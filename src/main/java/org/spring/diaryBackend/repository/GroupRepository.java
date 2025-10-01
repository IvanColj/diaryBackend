package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.GroupMarksDTO;
import org.spring.diaryBackend.dto.STNameSubjectDTO;
import org.spring.diaryBackend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select new org.spring.diaryBackend.dto.GroupMarksDTO(s.id, s.lastName, s.name, s.surname, null) from Student s where s.numberGroup = :numberGroup")
    List<GroupMarksDTO> findBaseInfo(@Param("numberGroup") Long numberGroup);

    @Query("select m.id.idStudent, rm " +
            "from SemesterMarks m " +
            "JOIN m.regularMarks rm " +
            "JOIN Student s ON m.id.idStudent = s.id " +
            "where m.id.idSt = :id_st " +
            "and s.numberGroup = :numberGroup order by rm.number, rm.typeMark.weight")
    List<Object[]> findAllMarksGroup(@Param("numberGroup") Long numberGroup,
                                     @Param("id_st") Long id_st);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "delete from groups where number_group = :numberGroup"
    )
    void deleteNumberGroup(@Param("numberGroup") Long numberGroup);

    Group findGroupByNumberGroup(Long numberGroup);

    @Query(
            value = """
                    select DISTINCT new org.spring.diaryBackend.dto.STNameSubjectDTO(st.id, s.subjectName)
                    FROM Subject s, SubjectTeacher st JOIN st.groups g
                    WHERE s.id = st.idSubject and g = :group""")
    List<STNameSubjectDTO> findBySubject(@Param("group") Long group);
}