package uz.pdp.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationships.entity.Address;
import uz.pdp.appjparelationships.entity.Group;
import uz.pdp.appjparelationships.entity.Student;
import uz.pdp.appjparelationships.entity.Subject;
import uz.pdp.appjparelationships.payload.StudentDto;
import uz.pdp.appjparelationships.repository.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    DeanRepository deanRepository;
    @Autowired
    TutorRepository tutorRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    SubjectRepository subjectRepository;

    //1. VAZIRLIK
    @GetMapping("/forMinistry")
    public Page<Student> getStudentListForMinistry(@RequestParam int page) {
        //1-1=0     2-1=1    3-1=2    4-1=3
        //select * from student limit 10 offset (0*10)
        //select * from student limit 10 offset (1*10)
        //select * from student limit 10 offset (2*10)
        //select * from student limit 10 offset (3*10)
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage;
    }

    //2. UNIVERSITY
    @GetMapping("/forUniversity/{universityId}")
    public Page<Student> getStudentListForUniversity(@PathVariable Integer universityId,
                                                     @RequestParam int page) {
        //1-1=0     2-1=1    3-1=2    4-1=3
        //select * from student limit 10 offset (0*10)
        //select * from student limit 10 offset (1*10)
        //select * from student limit 10 offset (2*10)
        //select * from student limit 10 offset (3*10)
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroup_Faculty_UniversityId(universityId, pageable);
        return studentPage;
    }

    //3. FACULTY DEKANAT
    @GetMapping("/forDean/{UniversitetId}")
    public Page<Student> getForFacultyDean(@PathVariable Integer UniversitetId, @RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> students = deanRepository.findAllByFaculty_UniversityId(UniversitetId, pageable);
        return students;
    }
    //4. GROUP OWNER
    @GetMapping("/forTutor/{universitetId}")
    public Page<Student> getForTutorStudents(@PathVariable Integer universitetId,@RequestParam int page){
        Pageable pageable=PageRequest.of(page,10);
        Page<Student> studentsForTutor = tutorRepository.findAllByGroup_Faculty_UniversityId(universitetId, pageable);
        return studentsForTutor;
    }
    @PostMapping("/addStudent")
    public String add(@RequestBody StudentDto studentDto){
        Student student=new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        Optional<Address> optionalAddress = addressRepository.findById(studentDto.getAddressId());
        if (!optionalAddress.isPresent())
            return "not found";
        student.setAddress(optionalAddress.get());
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (!optionalGroup.isPresent())
            return "not found";
        student.setGroup(optionalGroup.get());
        List<Subject> subjectList = subjectRepository.findAllById(studentDto.getSubjectsId());
        student.setSubjects(subjectList);
        studentRepository.save(student);
        return "Student successful added";
    }
    @PutMapping("/editStudent/{id}")
    public String edit(@PathVariable Integer id,@RequestBody StudentDto studentDto){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent())
            return "Student not found";
        Student student = optionalStudent.get();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        Optional<Address> optionalAddress = addressRepository.findById(studentDto.getAddressId());
        student.setAddress(optionalAddress.get());
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        student.setGroup(optionalGroup.get());
        List<Subject> subjectList = subjectRepository.findAllById(studentDto.getSubjectsId());
        student.setSubjects(subjectList);
        studentRepository.save(student);
        return "Student edited";
    }
    @DeleteMapping("/deletestudent/{id}")
    public String delete(@PathVariable Integer id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        Student student = optionalStudent.get();
        studentRepository.deleteById(id);
        return student.getFirstName()+" "+student.getLastName()+" deleted";
    }


}
