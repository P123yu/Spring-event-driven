package com.spring.event.spring_event_driven.repository;

import com.spring.event.spring_event_driven.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}
