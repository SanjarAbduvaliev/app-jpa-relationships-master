package uz.pdp.appjparelationships.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjparelationships.entity.Dean;
import uz.pdp.appjparelationships.entity.Student;

public interface DeanRepository extends JpaRepository<Dean,Integer> {
    Page<Student> findAllByFaculty_UniversityId(Integer faculty_university_id, Pageable pageable);
}
