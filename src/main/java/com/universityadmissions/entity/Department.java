package com.universityadmissions.entity;

import java.util.Objects;

public class Department extends Entity {
    private int id;
    private String name;
    private int budgetPlace;
    private int allPlace;
    private Exam firstExam;
    private Exam secondExam;
    private Exam thirdExam;

    public Department() {

    }

    public Department(int id, String name, int budgetPlace, int allPlace, Exam firstExam, Exam secondExam, Exam thirdExam) {
        this.id = id;
        this.name = name;
        this.budgetPlace = budgetPlace;
        this.allPlace = allPlace;
        this.firstExam = firstExam;
        this.secondExam = secondExam;
        this.thirdExam = thirdExam;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBudgetPlace() {
        return budgetPlace;
    }

    public void setBudgetPlace(int budgetPlace) {
        this.budgetPlace = budgetPlace;
    }

    public int getAllPlace() {
        return allPlace;
    }

    public void setAllPlace(int allPlace) {
        this.allPlace = allPlace;
    }

    public Exam getFirstExam() {
        return firstExam;
    }

    public void setFirstExam(Exam firstExam) {
        this.firstExam = firstExam;
    }

    public Exam getSecondExam() {
        return secondExam;
    }

    public void setSecondExam(Exam secondExam) {
        this.secondExam = secondExam;
    }

    public Exam getThirdExam() {
        return thirdExam;
    }

    public void setThirdExam(Exam thirdExam) {
        this.thirdExam = thirdExam;
    }

    @Override
    public void close() throws Exception {
        // Log
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id && budgetPlace == that.budgetPlace && allPlace == that.allPlace && Objects.equals(name, that.name) && Objects.equals(firstExam, that.firstExam) && Objects.equals(secondExam, that.secondExam) && Objects.equals(thirdExam, that.thirdExam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, budgetPlace, allPlace, firstExam, secondExam, thirdExam);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", budget_place=" + budgetPlace +
                ", all_place=" + allPlace +
                ", firstExam=" + firstExam +
                ", secondExam=" + secondExam +
                ", thirdExam=" + thirdExam +
                '}';
    }
}
