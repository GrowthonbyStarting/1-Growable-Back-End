package com.growable.starting.repository;

import com.growable.starting.model.Mentor;
import com.growable.starting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MentorRepository extends JpaRepository<Mentor, Long> {

    @Query("select avg(r.starScore) FROM Review r WHERE r.mentor.mentorId = :mentorId")
    double avgStarScore(@Param("mentorId") Long mentorId);

    Mentor findByUser(User authenticatedUser);

    @Query("SELECT DISTINCT m FROM Mentor m LEFT JOIN FETCH m.companyInfos WHERE m.mentorId = :mentorId")
    Optional<Mentor> findMentorWithCompanyInfos(@Param("mentorId") Long mentorId);

    @Query("SELECT DISTINCT m FROM Mentor m LEFT JOIN FETCH m.lectureExperiences WHERE m.mentorId = :mentorId")
    Optional<Mentor> findMentorWithLectureExperiences(@Param("mentorId") Long mentorId);


//    // Get all mentors without related entities
//    List<Mentor> findAll();
//
//    // Get a mentor with the specified ID along with their lectures
//    @EntityGraph(attributePaths = {"lectures"})
//    Optional<Mentor> findByIdWithLectures(Long id);
//
//    // Get a mentor with the specified ID along with their enrollments
//    @EntityGraph(attributePaths = {"enrollments"})
//    Optional<Mentor> findByIdWithEnrollments(Long id);
//
//    // Get a mentor with the specified ID along with their lectureExperiences
//    @EntityGraph(attributePaths = {"lectureExperiences"})
//    Optional<Mentor> findByIdWithLectureExperiences(Long id);
//
//    // Get a mentor with the specified ID along with their companyInfos
//    @EntityGraph(attributePaths = {"companyInfos"})
//    Optional<Mentor> findByIdWithCompanyInfos(Long id);
}

