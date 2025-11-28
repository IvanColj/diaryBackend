package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StudentDTO;
import org.spring.diaryBackend.dto.other.MarksStudentDTO;
import org.spring.diaryBackend.dto.other.STTeachersDTO;
import org.spring.diaryBackend.dto.other.StudentAllMarksDTO;
import org.spring.diaryBackend.mapper.entity.StudentDTOMapper;
import org.spring.diaryBackend.mapper.other.MarksStudentDTOMapper;
import org.spring.diaryBackend.mapper.other.NameSubjectTeachersDTOMapper;
import org.spring.diaryBackend.model.RegularMark;
import org.spring.diaryBackend.model.Student;
import org.spring.diaryBackend.model.StudentGroup;
import org.spring.diaryBackend.repository.StudentGroupRepository;
import org.spring.diaryBackend.repository.StudentRepository;
import org.spring.diaryBackend.service.StudentService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class SimpleStudentService implements StudentService {
    private final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    private final StudentRepository studentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final StudentDTOMapper studentDTOMapper;
    private final MarksStudentDTOMapper marksStudentDTOMapper;
    private final NameSubjectTeachersDTOMapper nameSubjectTeachersDTOMapper;

    @Override
    public List<StudentDTO> findAllStudent() {
        return studentRepository.findAll()
                .stream()
                .map(studentDTOMapper)
                .toList();
    }

    @Override
    public List<StudentDTO> findByIdGroup(Long idGroup) {
        return studentRepository.findStudentsByGroup(idGroup)
                .stream()
                .map(studentDTOMapper)
                .toList();
    }

    @Override
    public List<StudentAllMarksDTO> getStudentMarks(Long id) {
        List<STTeachersDTO> STTeachersDTOS = studentRepository.findStudentMarksInSubgroup(id)
                .stream()
                .map(nameSubjectTeachersDTOMapper)
                .toList();

        List<STTeachersDTO> groupedSTTeachersDTOS = STTeachersDTOS.stream()
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

        List<StudentAllMarksDTO> studentAllMarksDTOS = new ArrayList<>();
        groupedSTTeachersDTOS.forEach(nameSubjectTeachersDTO ->
                studentAllMarksDTOS.add(new StudentAllMarksDTO(
                        nameSubjectTeachersDTO,
                        null,
                        null
                ))
        );

        List<Object[]> rawMarks = studentRepository.findStudentMarksInGroup(id);

        Map<Long, List<MarksStudentDTO>> stMarks = rawMarks.stream()
                .collect(Collectors.groupingBy(
                        row -> (Long) row[0],
                        Collectors.mapping(
                                row -> marksStudentDTOMapper.apply((RegularMark) row[1]),
                                Collectors.toList()
                        )
                ));

        Map<Long, Long> stCertification = rawMarks.stream()
                .collect(HashMap::new,
                        (map, row) -> map.putIfAbsent((Long) row[0], (Long) row[2]),
                        HashMap::putAll
                );

        studentAllMarksDTOS.forEach(
                studentAllMarksDTO -> {
                    studentAllMarksDTO.setMarksBySt(
                            stMarks.get(studentAllMarksDTO.getSTTeachersDTO().getIdSt())
                    );
                    studentAllMarksDTO.setCertification(
                            stCertification.get(studentAllMarksDTO.getSTTeachersDTO().getIdSt())
                    );
                }
        );

        return studentAllMarksDTOS;
    }

    @Override
    public void saveStudent(StudentDTO studentDTO) {
        Student student;
        if (studentDTO.getId() != null) {
            student = studentRepository.findById(studentDTO.getId())
                    .orElse(new Student());
        } else {
            student = new Student();
        }

        student.setLastName(studentDTO.getLastName());
        student.setName(studentDTO.getName());
        student.setPatronymic(studentDTO.getPatronymic());
        student.setLogin(studentDTO.getLogin());
        student.setPassword(studentDTO.getPassword());
        student.setTelephone(studentDTO.getTelephone());
        student.setBirthDate(studentDTO.getBirthDate());
        student.setAddress(studentDTO.getAddress());
        student.setEmail(studentDTO.getEmail());

        if (studentDTO.getIdGroup() != null) {
            StudentGroup group = studentGroupRepository.findGroupById(studentDTO.getIdGroup());
            student.setIdGroup(group);
        } else {
            student.setIdGroup(null);
        }

        studentDTOMapper.apply(studentRepository.save(student));
    }

    @Override
    public StudentDTO updateStudent(StudentDTO studentNew) {
        Student student = studentRepository.findById(studentNew.getId()).orElse(null);
        if (student == null) {
            return new StudentDTO();
        }

        if (studentNew.getLastName() != null) {
            student.setLastName(studentNew.getLastName());
        }
        if (studentNew.getName() != null) {
            student.setName(studentNew.getName());
        }
        if (studentNew.getPatronymic() != null) {
            student.setPatronymic(studentNew.getPatronymic());
        }
        if (studentNew.getLastNameGenitive() != null) {
            student.setLastNameGenitive(studentNew.getLastNameGenitive());
        }
        if (studentNew.getNameGenitive() != null) {
            student.setNameGenitive(studentNew.getNameGenitive());
        }
        if (studentNew.getPatronymicGenitive() != null) {
            student.setPatronymicGenitive(studentNew.getPatronymicGenitive());
        }
        if (studentNew.getIdGroup() != null) {
            student.setIdGroup(
                    studentGroupRepository.findGroupById(studentNew.getIdGroup())
            );
        }
        if (studentNew.getLogin() != null) {
            student.setLogin(studentNew.getLogin());
        }
        if (studentNew.getPassword() != null) {
            student.setPassword(encoder.encode(studentNew.getPassword()));
        }
        if (studentNew.getTelephone() != null) {
            student.setTelephone(studentNew.getTelephone());
        }
        if (studentNew.getBirthDate() != null) {
            student.setBirthDate(studentNew.getBirthDate());
        }
        if (studentNew.getAddress() != null) {
            student.setAddress(studentNew.getAddress());
        }
        if (studentNew.getEmail() != null) {
            student.setEmail(studentNew.getEmail());
        }
        return studentDTOMapper.apply(studentRepository.save(student));
    }

    @Override
    public StudentDTO findById(Long id) {
        return studentRepository.findById(id)
                .map(studentDTOMapper)
                .orElse(null);
    }

    @Override
    public StudentDTO findByLoginOrPassword(String login, String password) {
        Student student = studentRepository.findByLoginOrPassword(login, password);
        if (student != null && encoder.matches(password, student.getPassword())) {
            return studentDTOMapper.apply(student);
        } else {
            return new StudentDTO();
        }
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}