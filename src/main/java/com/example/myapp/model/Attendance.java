package com.example.myapp.model;

import javax.persistence.*;

import org.springframework.lang.NonNull;


@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NonNull
    @ManyToOne
    private Student student;

    @NonNull
    @ManyToOne
    private Schedule schedule;

    @NonNull
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
}

