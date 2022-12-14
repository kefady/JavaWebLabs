package com.universityadmissions.entity;

import java.util.Objects;

public class ExamName extends Entity {
    private int id;
    private String name;

    public ExamName() {

    }

    public ExamName(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public void close() throws Exception {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamName that = (ExamName) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "ExamName{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
