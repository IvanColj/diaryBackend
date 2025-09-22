package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.spring.diaryBackend.dto.GroupMarksDTO;
import org.spring.diaryBackend.model.Group;
import org.spring.diaryBackend.model.RegularMarks;
import org.spring.diaryBackend.model.Student;
import org.spring.diaryBackend.repository.GroupRepository;
import org.spring.diaryBackend.service.GroupService;
import org.spring.diaryBackend.service.StudentService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class SimpleGroupService implements GroupService {
    private final StudentService serviceStudent;
    private final GroupRepository repository;

    @Override
    public List<Group> findByAllGroup(int offset, int limit) {
        return repository.findByAllGroup(offset, limit);
    }

    @Override
    public List<Group> findAll() {
        return repository.findAll();
    }

    @Override
    public Group findGroupByNumberGroup(Long numberGroup) {
        return repository.findGroupByNumberGroup(numberGroup);
    }

    @Override
    public List<Long> findBySubject(Long group) {
        return repository.findBySubject(group);
    }

    public List<GroupMarksDTO> getGroupMarksBySubject(Long numberGroup, Long subject) {
        List<GroupMarksDTO> marksGroupBySubject = repository.findBaseInfo(numberGroup);
        List<Object[]> rawMarks = repository.findAllMarksGroup(numberGroup, subject);

        Map<Long, List<RegularMarks>> marksByStudentId = rawMarks.stream()
                .collect(Collectors.groupingBy(
                        row -> (Long) row[0],
                        Collectors.mapping(
                                row -> (RegularMarks) row[1],
                                Collectors.toList()
                        )
                ));

        for (GroupMarksDTO groupMarksDTO : marksGroupBySubject) {
            Map<Long, List<RegularMarks>> studentMarksMap = new HashMap<>();
            studentMarksMap.put(groupMarksDTO.getIdStudent(), marksByStudentId.getOrDefault(groupMarksDTO.getIdStudent(), new ArrayList<>()));
            groupMarksDTO.setMarks(studentMarksMap.get(groupMarksDTO.getIdStudent()));
        }

        return marksGroupBySubject;
    }

    @Override
    public List<Student> fetchStudentsGroup(Long groupNumber) throws IOException {
        String url = "https://portal.novsu.ru/search/groups/r.2500.p.search.g.1991/i.2500/?page=search&grpname=" + groupNumber;
        Document doc = Jsoup.connect(url).get();

        Element table = doc.selectFirst("table.viewtable");
        Element ul = doc.selectFirst("#npe_instance_2500_npe_content > ul");

        List<Student> students = new ArrayList<>();
        Group group = extractGroupInfo(ul);

        if (table != null) {
            Elements rows = table.select("tr");
            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements cells = row.select("td");
                if (cells.size() >= 3) {
                    String fio = cells.get(1).text().trim();
                    String[] fioParts = splitFio(fio);

                    Student student = new Student();
                    student.setSurname(fioParts[0]);
                    student.setName(fioParts[1]);
                    student.setLastName(fioParts[2]);
                    student.setNumberGroup(groupNumber);
                    students.add(student);
                }
            }
        }

        if (findGroupByNumberGroup(group.getNumberGroup()) == null) {
            saveGroup(group);
            for (Student addStudent : students) {
                serviceStudent.saveStudent(addStudent);
            }
        } else {
            newStudentOldGroup(students, group);
        }

        return students;
    }

    private Group extractGroupInfo(Element ul) {
        Group group = new Group();

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

    private void newStudentOldGroup(List<Student> newAndOldStudents, Group group) {
        List<Student> oldStudents = serviceStudent.findByNumberGroup(group.getNumberGroup());
        List<Student> newStudents = new ArrayList<>();
        boolean exist;
        for (Student newStudent : newAndOldStudents) {
            exist = false;
            for (Student oldStudent : oldStudents) {
                if (Objects.equals(newStudent.getName(), oldStudent.getName()) &&
                        Objects.equals(newStudent.getLastName(), oldStudent.getLastName()) &&
                        Objects.equals(newStudent.getSurname(), oldStudent.getSurname())) {
                    exist = true;
                    break;
                }
            }

            if (!exist) {
                newStudents.add(newStudent);
            }
        }

        for (Student addStudent : newStudents) {
            serviceStudent.saveStudent(addStudent);

        }
    }


    @Override
    public Group saveGroup(Group group) {
        return repository.save(group);
    }

    @Override
    public Group updateGroup(Group group) {
        return repository.save(group);
    }

    @Override
    @Transactional
    public void deleteNumberGroup(Long numberGroup) {
        repository.deleteNumberGroup(numberGroup);
    }
}
