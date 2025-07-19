package com.spring.event.spring_event_driven.service;

import com.spring.event.spring_event_driven.model.Student;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {

    Student createStudent(Student student);

}
