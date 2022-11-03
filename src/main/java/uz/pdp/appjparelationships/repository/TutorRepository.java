package uz.pdp.appjparelationships.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjparelationships.entity.Student;
import uz.pdp.appjparelationships.entity.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, Integer> {
    Page<Student> findAllByGroup_Faculty_UniversityId(Integer group_faculty_university_id, Pageable pageable);
}
