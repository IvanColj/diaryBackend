package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.spring.diaryBackend.dto.StudentMarksDTO;
import org.spring.diaryBackend.model.Student;
import org.spring.diaryBackend.repository.StudentRepository;
import org.spring.diaryBackend.service.StudentService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class SimpleStudentService implements StudentService {
    private final StudentRepository repository;
    private final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    public List<Student> findByAllStudent(int offset, int limit) {
        return repository.findByAllStudent(offset, limit);
    }

    @Override
    public List<Student> findAllStudent() {
        return repository.findAll();
    }

    @Override
    public List<Student> findByNumberGroup(Long group) {
        return repository.findByNumberGroup(group);
    }

    @Override
    public List<Student> fetchStudents(Long groupNumber) throws IOException {
        String url = "https://portal.novsu.ru/search/groups/r.2500.p.search.g.1991/i.2500/?page=search&grpname=" + groupNumber;
        Document doc = Jsoup.connect(url).get();

        Element table = doc.selectFirst("table.viewtable");
        List<Student> students = new ArrayList<>();

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
        } else {
            System.out.println("Таблица студентов не найдена на странице.");
        }

        return students;
    }

    private static String[] splitFio(String fio) {
        String[] parts = fio.split("\\s+");
        String surname = parts.length > 0 ? parts[0] : "";
        String name = parts.length > 1 ? parts[1] : "";
        String lastName = parts.length > 2 ? parts[2] : "";
        return new String[]{surname, name, lastName};
    }

    @Override
    public StudentMarksDTO getStudentMarks(Long id) {
        StudentMarksDTO baseInfo = repository.findBaseInfo(id);
        List<Object[]> rawMarks = repository.findMarksStudentBySubject(id);

        Map<Long, List<Double>> id_st = rawMarks.stream()
                .collect(Collectors.groupingBy(
                        row -> (Long) row[0],
                        Collectors.mapping(
                                row -> (Double) row[1],
                                Collectors.toList()
                        )
                ));
        baseInfo.setMarksBySt_id(id_st);
        return baseInfo;
    }

    @Override
    public Student saveStudent(Student student) {
        student.setPassword(encoder.encode(student.getPassword()));
        return repository.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        student.setPassword(encoder.encode(student.getPassword()));
        return repository.save(student);
    }

    @Override
    public Student findById(Long id) {
        Optional<Student> optionalEntity = repository.findById(id);
        return optionalEntity.orElse(null);
    }

    @Override
    public Student findByLoginOrPassword(String login, String password) {
        Student student = repository.findByLoginOrPassword(login, password);
        if (encoder.matches(password, student.getPassword())) {
            return student;
        }
        else {
            return new Student();
        }
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        repository.deleteById(id);
    }
}
