package com.spring.event.spring_event_driven.event;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class StudentListener {

    // this will start reacting in the same thread as service starts
    // this will not require @Transactional annotation in the service layer

//    @EventListener
//    public void handleStudentEvent(StudentEvent studentEvent){
//        System.out.println(studentEvent+"hello");
//    }


    // this will run anyway commit or rollback no matter this will always run

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleStudentEvent3(StudentEvent studentEvent){
        System.out.println(studentEvent+"runs after db completion");
        System.out.println(studentEvent.student()+"runs after db completion");

    }

    // this will require @Transactional annotation in the service layer
    @Order(1)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleStudentEvent7(StudentEvent studentEvent){
        System.out.println(studentEvent+"runs after db commit 1");
        System.out.println(studentEvent.student()+"runs after db commit");

    }

    @Order(2)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleStudentEvent8(StudentEvent studentEvent){
        System.out.println(studentEvent+"runs after db commit 2");
        System.out.println(studentEvent.student()+"runs after db commit");

    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleStudentEvent2(StudentEvent studentEvent){
        System.out.println(studentEvent+"runs after db rollback");
        System.out.println(studentEvent.student()+"runs after db rollback");

    }





//    // this will require @Transactional annotation in the service layer
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    public void handleStudentEvent(StudentEvent studentEvent){
//        System.out.println(studentEvent.name()+" "+studentEvent.city()+"runs after db completion");
//    }

}
