package com.universityadmissions.entity;

import java.sql.Date;
import java.util.Objects;

public class Application extends Entity {
    private int id;
    private User user;
    private Department department;
    private int priority;
    private int finalGrade;
    private boolean verified;
    private boolean accepted;
    private Date date;

    public Application() {

    }

    public Application(int id, User user, Department department, int priority, int finalGrade, boolean verified, boolean accepted, Date date) {
        this.id = id;
        this.user = user;
        this.department = department;
        this.priority = priority;
        this.finalGrade = finalGrade;
        this.verified = verified;
        this.accepted = accepted;
        this.date = date;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(int finalGrade) {
        this.finalGrade = finalGrade;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public void close() throws Exception {
        // Log
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return id == that.id && priority == that.priority && finalGrade == that.finalGrade && verified == that.verified && Objects.equals(user, that.user) && Objects.equals(department, that.department) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, department, priority, finalGrade, verified, date);
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", user=" + user +
                ", department=" + department +
                ", priority=" + priority +
                ", finalGrade=" + finalGrade +
                ", verified=" + verified +
                ", date=" + date +
                '}';
    }
}
