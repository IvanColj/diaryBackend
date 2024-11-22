package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.GroupMarksDTO;
import org.spring.diaryBackend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(
            nativeQuery = true,
            value = "select * from groups offset :offset limit :limit")
    List<Group> findByAllGroup(@Param("offset") int offset, @Param("limit") int limit);

    @Query("select new org.spring.diaryBackend.dto.GroupMarksDTO(s.name, s.lastName, s.id, null) from Student s, Group g where s.numberGroup = g.numberGroup and g.numberGroup = :numberGroup")
    List<GroupMarksDTO> findBaseInfo(@Param("numberGroup") Long numberGroup);

    @Query("select m.id.id_student, m.marks from Marks m where m.id.id_st = :id_st and m.id.id_student in (select s.id from Student s, Group g where s.numberGroup = g.numberGroup and g.numberGroup = :numberGroup)")
    List<Object[]> findAllMarksGroup(@Param("numberGroup") Long numberGroup, @Param("id_st") Long id_st);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "delete from groups where number_group = :numberGroup"
    )
    void deleteNumberGroup(@Param("numberGroup")Long numberGroup);

    Group findGroupByNumberGroup(Long numberGroup);
}