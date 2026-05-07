package org.spring.diaryBackend.service.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.text.Transliterator;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.spring.diaryBackend.dto.entity.StudentDTO;
import org.spring.diaryBackend.dto.entity.StudentGroupDTO;
import org.spring.diaryBackend.dto.other.*;
import org.spring.diaryBackend.logic.GenerateSecurePassword;
import org.spring.diaryBackend.mapper.entity.StudentGroupDTOMapper;
import org.spring.diaryBackend.mapper.other.MarksStudentDTOMapper;
import org.spring.diaryBackend.mapper.other.NameSubjectTeachersDTOMapper;
import org.spring.diaryBackend.model.Student;
import org.spring.diaryBackend.model.StudentGroup;
import org.spring.diaryBackend.repository.MarkRepository;
import org.spring.diaryBackend.repository.StaffRepository;
import org.spring.diaryBackend.repository.StudentGroupRepository;
import org.spring.diaryBackend.repository.SubgroupRepository;
import org.spring.diaryBackend.service.StudentGroupService;
import org.spring.diaryBackend.service.StudentService;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class SimpleStudentGroupService implements StudentGroupService {
    private final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    private final StudentService studentService;
    private final StudentGroupRepository studentGroupRepository;
    private final StaffRepository staffRepository;
    private final SubgroupRepository subgroupRepository;
    private final MarkRepository markRepository;
    private final StudentGroupDTOMapper studentGroupDTOMapper;
    private final NameSubjectTeachersDTOMapper nameSubjectTeachersDTOMapper;
    private final MarksStudentDTOMapper marksStudentDTOMapper;

    private final JdbcTemplate jdbcTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public List<StudentGroupDTO> findAll() {
        return studentGroupRepository.findAll()
                .stream()
                .map(studentGroupDTOMapper)
                .toList();
    }

    @Override
    public StudentGroupDTO findStudentGroupByNumberGroupAndAdmissionYear(Long numberGroup, Long admissionYear) {
        return studentGroupDTOMapper.apply(
                studentGroupRepository.findGroupByNumberAndAdmissionYear(numberGroup, admissionYear)
        );
    }

    @Override
    public List<StudentGroupDTO> findStudentGroupByNumberGroup(Long numberGroup) {
        return studentGroupRepository.findGroupByNumber(numberGroup)
                .stream()
                .map(studentGroupDTOMapper)
                .toList();
    }

    @Override
    public StudentGroupDTO findStudentGroupByIdGroup(Long idGroup) {
        return studentGroupDTOMapper.apply(
                studentGroupRepository.findGroupById(idGroup)
        );
    }

    @Override
    public List<STTeachersDTO> findBySubject(Long group) {
        List<STTeachersDTO> STTeachersDTOS = studentGroupRepository.findGroupSubjects(group)
                .stream()
                .map(nameSubjectTeachersDTOMapper)
                .toList();

        return STTeachersDTOS.stream()
                .collect(Collectors.toMap(
                        STTeachersDTO::getIdSt,
                        dto -> new STTeachersDTO(
                                dto.getIdSt(),
                                dto.getIdSubject(),
                                dto.getNameSubject(),
                                new ArrayList<>(dto.getTeachers())
                        ),
                        (existing, replacement) -> {
                            existing.getTeachers().addAll(replacement.getTeachers());
                            return existing;
                        }
                ))
                .values().stream()
                .toList();
    }

    @Override
    public List<GroupMarksDTO> getGroupMarksBySubject(Long idGroup, Long idSt, Long idTeacher) {
        List<GroupMarksDTO> marksGroupBySubject = studentGroupRepository.findStudentsFIO(idGroup);

        if (subgroupRepository.findTeacherSubgroupBySubject(idSt, idTeacher) != null) {
            List<Long> studentsId = subgroupRepository
                    .findTeacherSubgroupBySubject(idSt, idTeacher)
                    .getStudents()
                    .stream()
                    .map(Student::getId)
                    .toList();

            marksGroupBySubject = marksGroupBySubject.stream()
                    .filter(groupMarksDTO -> studentsId.contains(groupMarksDTO.getIdStudent()))
                    .toList();
        }

        for (GroupMarksDTO groupMarksDTO : marksGroupBySubject) {
            groupMarksDTO.setMarks(
                    markRepository.findStudentRegularsMarkBySubject(groupMarksDTO.getIdStudent(), idSt)
                            .stream()
                            .map(marksStudentDTOMapper)
                            .toList()
            );
        }
        return marksGroupBySubject;
    }

    @Override
    public List<StudentDTO> fetchStudentsGroup(Long groupNumber) throws IOException {
        String url = "https://portal.novsu.ru/search/groups/r.2500.p.search.g.1991/i.2500/?page=search&grpname=" + groupNumber;
        Document doc = Jsoup.connect(url).get();

        Element table = doc.selectFirst("table.viewtable");
        Element ul = doc.selectFirst("#npe_instance_2500_npe_content > ul");

        List<StudentDTO> studentsAdd = new ArrayList<>();
        List<StudentDTO> studentsReturn = new ArrayList<>();
        String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";
        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        String password;

        StudentGroup group = extractGroupInfo(ul);
        group.setNumberGroup(groupNumber);

        if (table != null) {
            Elements rows = table.select("tr");
            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements cells = row.select("td");
                if (cells.size() >= 3) {
                    String fio = cells.get(1).text().trim();
                    String[] fioParts = splitFio(fio);

                    StudentDTO student = new StudentDTO();
                    student.setLastName(fioParts[0]);
                    student.setName(fioParts[1]);
                    student.setPatronymic(fioParts[2]);
                    student.setIdGroup(group.getId());
                    student.setLogin(
                            toLatinTrans.transliterate(fioParts[0]) +
                                    toLatinTrans.transliterate(String.valueOf(fioParts[1].charAt(0))) +
                                    toLatinTrans.transliterate(String.valueOf(fioParts[2].charAt(0)))
                    );
                    password = GenerateSecurePassword.generatePassword(10);
                    student.setPassword(password);
                    studentsReturn.add(student);

                    StudentDTO studentCopy = new StudentDTO();
                    studentCopy.setLastName(student.getLastName());
                    studentCopy.setName(student.getName());
                    studentCopy.setPatronymic(student.getPatronymic());
                    studentCopy.setIdGroup(student.getIdGroup());
                    studentCopy.setLogin(student.getLogin());
                    studentCopy.setPassword(encoder.encode(password));
                    studentsAdd.add(studentCopy);
                }
            }
        }

        if (findStudentGroupByNumberGroupAndAdmissionYear(group.getNumberGroup(), group.getAdmissionYear()).getId() == null) {
            studentGroupRepository.save(group);
            group.setId(studentGroupRepository.findGroupByNumberAndAdmissionYear(
                    group.getNumberGroup(),
                    group.getAdmissionYear()).getId());

            for (StudentDTO addStudent : studentsAdd) {
                addStudent.setIdGroup(group.getId());
                studentService.saveStudent(addStudent);
            }
        } else {
            newStudentOldGroup(studentsAdd, group);
        }

        return studentsReturn;
    }

    private StudentGroup extractGroupInfo(Element ul) {
        StudentGroup group = new StudentGroup();

        if (ul != null) {
            Elements liElements = ul.select("li");
            for (Element li : liElements) {
                String text = li.text().trim();
                if (text.startsWith("Форма обучения:")) {
                    group.setFormEducation(text.replace("Форма обучения:", "").trim());
                } else if (text.startsWith("Курс:")) {
                    group.setCourse(Long.valueOf(text.replace("Курс:", "").trim()));
                } else if (text.startsWith("Направление (специальность):")) {
                    group.setSpecialty(text.replace("Направление (специальность):", "").trim());
                } else if (text.startsWith("Профиль:")) {
                    group.setProfile(text.replace("Профиль:", "").trim());
                } else if (text.startsWith("Год поступления:")) {
                    group.setAdmissionYear(Long.valueOf(text.replace("Год поступления:", "").trim()));
                }
            }
        }

        return group;
    }

    private static String[] splitFio(String fio) {
        String[] parts = fio.split("\\s+");
        String surname = parts.length > 0 ? parts[0] : "";
        String name = parts.length > 1 ? parts[1] : "";
        String lastName = parts.length > 2 ? parts[2] : "";
        return new String[]{surname, name, lastName};
    }

    private void newStudentOldGroup(List<StudentDTO> newAndOldStudents, StudentGroup group) {
        List<StudentDTO> oldStudents = studentService.findByIdGroup(group.getId());
        List<StudentDTO> newStudents = new ArrayList<>();
        boolean exist;

        for (StudentDTO newStudent : newAndOldStudents) {
            exist = false;
            for (StudentDTO oldStudent : oldStudents) {
                if (Objects.equals(newStudent.getName(), oldStudent.getName()) &&
                        Objects.equals(newStudent.getLastName(), oldStudent.getLastName()) &&
                        Objects.equals(newStudent.getPatronymic(), oldStudent.getPatronymic())) {
                    exist = true;
                    break;
                }
            }

            if (!exist) {
                newStudents.add(newStudent);
            }
        }

        for (StudentDTO addStudent : newStudents) {
            studentService.saveStudent(addStudent);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GroupReportDTO getFullGroupReport(Long groupId) {
        // 1. Получаем названия предметов в нужном порядке
        String subjectsSql = "SELECT s.subject_name FROM subject s " +
                "JOIN subject_teacher st ON s.id = st.id_subject " +
                "JOIN groups_st gs ON st.id = gs.id_st " +
                "WHERE gs.id_group = ? ORDER BY st.id ASC";

        List<String> subjectNames = jdbcTemplate.queryForList(subjectsSql, String.class, groupId);

        // 2. Вызываем функцию получения отчета по студентам
        String reportSql = "SELECT get_group_report(?)";

        // Используем jdbcTemplate.query вместо queryForList, чтобы избежать автоматического
        // приведения jsonb -> Map, которое вызывает ошибку TypeMismatchDataAccessException
        List<Map<String, Object>> rawData = jdbcTemplate.query(reportSql, (rs, rowNum) -> {
            Map<String, Object> row = new java.util.HashMap<>();
            // Получаем объект из первой колонки результата функции
            row.put("result", rs.getObject(1));
            return row;
        }, groupId);

        // 3. Превращаем PGobject (JSON-строку) в JsonNode для корректной отправки в API
        List<Object> extractedData = Collections.singletonList(rawData.stream()
                .map(row -> {
                    // Достаем объект из нашей временной карты
                    Object pgObject = row.get("result");

                    // PGobject при вызове toString() возвращает чистую JSON-строку
                    String jsonString = (pgObject != null) ? pgObject.toString() : null;

                    if (jsonString != null) {
                        try {
                            // Превращаем строку в JsonNode, чтобы Spring отправил её как JSON-объект, а не как строку
                            return objectMapper.readTree(jsonString);
                        } catch (Exception e) {
                            // В случае ошибки парсинга возвращаем null, который позже отфильтруем
                            return null;
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList());

        // 4. Собираем всё в итоговый DTO
        GroupReportDTO report = new GroupReportDTO();
        report.setSubjectNames(subjectNames);
        report.setStudentsData(extractedData);

        return report;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentCategoryGroupDTO> getStudentsByScholarshipCategory(Long groupId) {
        String sql = """
        WITH student_stats AS (
            SELECT
                s.id,
                s.last_name || ' ' || s.name || ' ' || s.patronymic AS fio,
                MIN(sm.initial_certification) as min_init,
                MAX(sm.initial_certification) as max_init
            FROM student s
            JOIN semester_mark sm ON s.id = sm.id_student
            WHERE s.id_group = ?
              AND sm.id_st IN (
                SELECT id_st
                FROM groups_st
                WHERE id_group = ?
            )
            GROUP BY s.id, s.last_name, s.name, s.patronymic
        )
        SELECT
            fio,
            CASE
                WHEN min_init = 5 AND max_init = 5 THEN '5'
                WHEN min_init = 4 AND max_init = 5 THEN '4-5'
                WHEN min_init = 4 AND max_init = 4 THEN '4'
                ELSE 'Другое'
                END as category
        FROM student_stats
        WHERE (min_init = 5 AND max_init = 5)
           OR (min_init = 4 AND max_init = 4)
           OR (min_init = 4 AND max_init = 5)
        ORDER BY category, fio;
        """;

        // 1. Получаем «плоский» список из базы
        List<StudentCategoryDTO> flatList = jdbcTemplate.query(sql, (rs, rowNum) -> {
            StudentCategoryDTO dto = new StudentCategoryDTO();
            dto.setFio(rs.getString("fio"));
            dto.setCategory(rs.getString("category"));
            return dto;
        }, groupId, groupId);

        // 2. Группируем студентов по категориям с помощью Java Stream API
        // groupingBy создаст Map<String, List<StudentCategoryDTO>>
        Map<String, List<StudentCategoryDTO>> grouped = flatList.stream()
                .collect(Collectors.groupingBy(
                        StudentCategoryDTO::getCategory,
                        LinkedHashMap::new, // Используем LinkedHashMap, чтобы сохранить порядок категорий
                        Collectors.toList()
                ));

        // 3. Преобразуем Map в список объектов StudentCategoryGroupDTO
        return grouped.entrySet().stream()
                .map(entry -> {
                    List<String> names = entry.getValue().stream()
                            .map(StudentCategoryDTO::getFio)
                            .toList();
                    return new StudentCategoryGroupDTO(entry.getKey(), names);
                })
                .toList();
    }

    @Override
    public StudentGroupDTO updateGroup(StudentGroupDTO studentGroupNew) {
        StudentGroup studentGroupUpdate = studentGroupRepository.findGroupById(studentGroupNew.getId());

        if (studentGroupNew.getNumberGroup() != null) {
            studentGroupUpdate.setNumberGroup(studentGroupNew.getNumberGroup());
        }
        if (studentGroupNew.getAdmissionYear() != null) {
            studentGroupUpdate.setAdmissionYear(studentGroupNew.getAdmissionYear());
        }
        if (studentGroupNew.getIdCurator() != null) {
            studentGroupUpdate.setIdCurator(
                    staffRepository.findById(studentGroupNew.getIdCurator()).orElse(null)
            );
            if (studentGroupUpdate.getIdCurator() == null) {
                return new StudentGroupDTO();
            }
        }
        if (studentGroupNew.getCourse() != null) {
            studentGroupUpdate.setCourse(studentGroupNew.getCourse());
        }
        if (studentGroupNew.getFormEducation() != null) {
            studentGroupUpdate.setFormEducation(studentGroupNew.getFormEducation());
        }
        if (studentGroupNew.getProfile() != null) {
            studentGroupUpdate.setProfile(studentGroupNew.getProfile());
        }
        if (studentGroupNew.getSpecialty() != null) {
            studentGroupUpdate.setSpecialty(studentGroupNew.getSpecialty());
        }
        return studentGroupDTOMapper.apply(studentGroupRepository.save(studentGroupUpdate));
    }

    @Override
    @Transactional
    public void deleteIdGroup(Long idGroup) {
        studentGroupRepository.deleteGroupById(idGroup);
    }
}