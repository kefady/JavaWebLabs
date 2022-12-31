package com.universityadmissions.entity;

import java.util.Objects;

public class CertificateGrade extends Entity {
    private int id;
    private User user;
    private ExamName examName;
    private int grade;

    public CertificateGrade() {

    }

    public CertificateGrade(int id, User user, ExamName examName, int grade) {
        this.id = id;
        this.user = user;
        this.examName = examName;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ExamName getExamName() {
        return examName;
    }

    public void setExamName(ExamName examName) {
        this.examName = examName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public void close() throws Exception {
        // Log
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateGrade that = (CertificateGrade) o;
        return id == that.id && grade == that.grade && Objects.equals(user, that.user) && Objects.equals(examName, that.examName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, examName, grade);
    }

    @Override
    public String toString() {
        return "CertificateGrade{" +
                "id=" + id +
                ", user=" + user +
                ", examName=" + examName +
                ", grade=" + grade +
                '}';
    }
}
