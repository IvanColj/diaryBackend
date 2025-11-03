package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StaffDTO;
import org.spring.diaryBackend.dto.entity.SubjectDTO;
import org.spring.diaryBackend.mapper.entity.StaffDTOMapper;
import org.spring.diaryBackend.mapper.entity.SubjectDTOMapper;
import org.spring.diaryBackend.model.Staff;
import org.spring.diaryBackend.repository.StaffRepository;
import org.spring.diaryBackend.service.StaffService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleStaffService implements StaffService {
    private final StaffRepository staffRepository;
    private final StaffDTOMapper staffDTOMapper;
    private final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    private final SubjectDTOMapper subjectDTOMapper;

    @Override
    public List<StaffDTO> findAllStaff() {
        return staffRepository.findAll().stream().map(staffDTOMapper).toList();
    }

    @Override
    public List<SubjectDTO> findByAllSubject(Long id) {
        return staffRepository.findByAllSubject(id).stream().map(subjectDTOMapper).toList();
    }

    @Override
    public StaffDTO findStaffById(Long id) {
        return staffRepository.findById(id).map(staffDTOMapper).orElse(null);
    }

    @Override
    public StaffDTO saveStaff(StaffDTO staffDTO) {
        Staff staff = new Staff();
        staff.setLastName(staffDTO.getLastName());
        staff.setName(staffDTO.getName());
        staff.setPatronymic(staffDTO.getPatronymic());
        staff.setLogin(staffDTO.getLogin());
        staff.setPassword(encoder.encode(staffDTO.getPassword()));
        staff.setEmail(staffDTO.getEmail());
        return staffDTOMapper.apply(staffRepository.save(staff));
    }

    @Override
    public void addStaffJob(Long idStaff, Long idJob) {
        staffRepository.addStaffJob(idStaff, idJob);
    }

    @Override
    public void deleteStaffJob(Long idStaff, Long idJob) {
        staffRepository.deleteStaffJob(idStaff, idJob);
    }

    @Override
    public StaffDTO updateStaff(StaffDTO staffNew) {
        Staff staffUpdate = staffRepository.findById(staffNew.getId()).orElse(null);
        if (staffUpdate == null) {
            return new StaffDTO();
        }
        if (staffNew.getPatronymic() != null) {
            staffUpdate.setPassword(staffNew.getPassword());
        }
        if (staffNew.getName() != null) {
            staffUpdate.setName(staffNew.getName());
        }
        if (staffNew.getLastName() != null) {
            staffUpdate.setLastName(staffNew.getLastName());
        }
        if (staffNew.getLogin() != null) {
            Staff staffLogin = staffRepository.findByLogin(staffNew.getLogin());
            if (staffLogin.getId() == null) {
                staffUpdate.setLogin(staffNew.getLogin());
            }
            else {
                return new StaffDTO(null, null, null, null, "Такой логин уже есть, придумайте другой", null, null, null);
            }
        }
        if (staffNew.getPassword() != null) {
            staffUpdate.setPassword(encoder.encode(staffNew.getPassword()));
        }
        if (staffNew.getEmail() != null) {
            staffUpdate.setEmail(staffNew.getEmail());
        }
        return staffDTOMapper.apply(staffRepository.save(staffUpdate));
    }

    @Override
    public StaffDTO findByLoginOrPassword(String login, String password) {
        Staff staff = staffRepository.findByLoginOrPassword(login, password);
        if (staff != null && encoder.matches(password, staff.getPassword())) {
            return staffDTOMapper.apply(staff);
        }
        else {
            return new StaffDTO();
        }
    }

    @Override
    @Transactional
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
}
