package com.universityadmissions.entity;

import java.util.Objects;

public class Exam extends Entity {
    private int id;
    private ExamName examName;
    private int minGrade;
    private double coefficient;

    public Exam() {

    }

    public Exam(int id, ExamName examName, int minGrade, double coefficient) {
        this.id = id;
        this.examName = examName;
        this.minGrade = minGrade;
        this.coefficient = coefficient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ExamName getExamName() {
        return examName;
    }

    public void setExamName(ExamName examName) {
        this.examName = examName;
    }

    public int getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(int minGrade) {
        this.minGrade = minGrade;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam that = (Exam) o;
        return id == that.id && minGrade == that.minGrade && coefficient == that.coefficient && Objects.equals(examName, that.examName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, examName, minGrade, coefficient);
    }

    @Override
    public String toString() {
        return "DepartmentExamGrade{" +
                "id=" + id +
                ", examName=" + examName +
                ", minGrade=" + minGrade +
                ", coefficient=" + coefficient +
                '}';
    }
}
