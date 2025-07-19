//package com.spring.event.spring_event_driven.event;
//
//import com.spring.event.spring_event_driven.model.Student;
//import lombok.Data;
//
//@Data
//public class StudentEvent {
//    private final Student student;
//}


package com.spring.event.spring_event_driven.event;

import com.spring.event.spring_event_driven.model.Student;
public record StudentEvent(Student student) { }



//
//
//package com.spring.event.spring_event_driven.event;
//public record StudentEvent(String name,String city) { }
