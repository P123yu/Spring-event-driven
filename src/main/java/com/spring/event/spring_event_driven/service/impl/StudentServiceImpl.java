package com.spring.event.spring_event_driven.service.impl;

import com.spring.event.spring_event_driven.event.StudentEvent;
import com.spring.event.spring_event_driven.model.Student;
import com.spring.event.spring_event_driven.repository.StudentRepository;
import com.spring.event.spring_event_driven.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ApplicationEventPublisher applicationEventPublisher;



    @Override
    @Transactional
    public Student createStudent(Student student) {
        Student studentObj=studentRepository.save(student);
        applicationEventPublisher.publishEvent(new StudentEvent(student));
        return studentObj;
    }


//    @Override
//    @Transactional
//    public Student createStudent(Student student) {
//        Student studentObj=studentRepository.save(student);
//        applicationEventPublisher.publishEvent(new StudentEvent(student.getName(),student.getCity()));
//        return studentObj;
//    }


//
//// testing for rollback
//
//    @Override
//    @Transactional
//    public Student createStudent(Student student) {
//        Student studentObj=studentRepository.save(student);
//        applicationEventPublisher.publishEvent(new StudentEvent(student));
//        // Simulate failure after save
//        if (true) throw new RuntimeException("Rollback test");
//        return studentObj;
//    }
}
