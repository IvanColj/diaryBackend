package org.spring.diaryBackend.service.simple;

import com.ibm.icu.text.Transliterator;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.spring.diaryBackend.dto.entity.StudentDTO;
import org.spring.diaryBackend.dto.entity.StudentGroupDTO;
import org.spring.diaryBackend.dto.other.GroupMarksDTO;
import org.spring.diaryBackend.dto.other.NameSubjectTeachersDTO;
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
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class SimpleStudentGroupService implements StudentGroupService {
    private final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    private final StudentService studentService;
    private final StudentGroupRepository studentGroupRepository;
    private final StaffRepository staffRepository;
    private final StudentGroupDTOMapper studentGroupDTOMapper;

    private final NameSubjectTeachersDTOMapper nameSubjectTeachersDTOMapper;

    private final MarksStudentDTOMapper marksStudentDTOMapper;

    private final SubgroupRepository subgroupRepository;

    private final MarkRepository markRepository;

    @Override
    public List<StudentGroupDTO> findAll() {
        return studentGroupRepository.findAll().stream().map(studentGroupDTOMapper).toList();
    }

    @Override
    public StudentGroupDTO findStudentGroupByNumberGroupAndAdmissionYear(Long numberGroup, Long admissionYear) {
        return studentGroupDTOMapper.apply(studentGroupRepository.findStudentGroupByNumberGroupAndAdmissionYear(numberGroup, admissionYear));
    }

    @Override
    public List<StudentGroupDTO> findStudentGroupByNumberGroup(Long numberGroup) {
        return studentGroupRepository.findStudentGroupByNumberGroup(numberGroup).stream().map(studentGroupDTOMapper).toList();
    }

    @Override
    public StudentGroupDTO findStudentGroupByIdGroup(Long idGroup) {
        return studentGroupDTOMapper.apply(studentGroupRepository.findStudentGroupByIdGroup(idGroup));
    }

    @Override
    public List<NameSubjectTeachersDTO> findBySubject(Long group) {
        List<NameSubjectTeachersDTO> nameSubjectTeachersDTOS = studentGroupRepository.findBySubject(group).stream().map(nameSubjectTeachersDTOMapper).toList();
        return nameSubjectTeachersDTOS.stream()
                .collect(Collectors.toMap(
                        NameSubjectTeachersDTO::getIdSt,
                        dto -> new NameSubjectTeachersDTO(
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
        List<GroupMarksDTO> marksGroupBySubject = studentGroupRepository.findBaseInfo(idGroup);
        if (subgroupRepository.findByIdStAndIdTeacher(idSt, idTeacher) != null) {
            List<Long> studentsId = subgroupRepository.findByIdStAndIdTeacher(idSt, idTeacher).getStudents().stream().map(Student::getId).toList();
            marksGroupBySubject = marksGroupBySubject.stream()
                    .filter(groupMarksDTO -> studentsId.contains(groupMarksDTO.getIdStudent()))
                    .toList();
        }
        for (GroupMarksDTO groupMarksDTO : marksGroupBySubject) {
            groupMarksDTO.setMarks(markRepository.findByStudentSubject(groupMarksDTO.getIdStudent(), idSt).stream().map(marksStudentDTOMapper).toList());
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
                    student.setLogin(toLatinTrans.transliterate(fioParts[0]) +
                            toLatinTrans.transliterate(String.valueOf(fioParts[1].charAt(0))) +
                            toLatinTrans.transliterate(String.valueOf(fioParts[2].charAt(0))));
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
            group.setId(studentGroupRepository.findStudentGroupByNumberGroupAndAdmissionYear(
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
    public StudentGroupDTO updateGroup(StudentGroupDTO studentGroupNew) {
        StudentGroup studentGroupUpdate = studentGroupRepository.findStudentGroupByIdGroup(studentGroupNew.getId());
        if (studentGroupNew.getNumberGroup() != null) {
            studentGroupUpdate.setNumberGroup(studentGroupNew.getNumberGroup());
        }
        if (studentGroupNew.getAdmissionYear() != null) {
            studentGroupUpdate.setAdmissionYear(studentGroupNew.getAdmissionYear());
        }
        if (studentGroupNew.getIdCurator() != null) {
            studentGroupUpdate.setIdCurator(staffRepository.findById(studentGroupNew.getIdCurator()).orElse(null));
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
        studentGroupRepository.deleteIdGroup(idGroup);
    }
}
