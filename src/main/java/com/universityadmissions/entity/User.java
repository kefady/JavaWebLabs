package com.universityadmissions.entity;

import java.sql.Date;
import java.util.Objects;

public class User extends Entity {
    private int id;
    private Role role;
    private String username;
    private String password;
    private String surname;
    private String name;
    private String patronymic;
    private String email;
    private String city;
    private String region;
    private String eduName;
    private Date birthday;
    private boolean blocked;

    public User() {

    }

    public User(int id, Role role, String username, String password, String surname, String name, String patronymic, String email, String city, String region, String eduName, Date birthday, boolean blocked) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.password = password;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.email = email;
        this.city = city;
        this.region = region;
        this.eduName = eduName;
        this.birthday = birthday;
        this.blocked = blocked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEduName() {
        return eduName;
    }

    public void setEduName(String eduName) {
        this.eduName = eduName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return id == that.id && blocked == that.blocked && Objects.equals(role, that.role) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(surname, that.surname) && Objects.equals(name, that.name) && Objects.equals(patronymic, that.patronymic) && Objects.equals(email, that.email) && Objects.equals(city, that.city) && Objects.equals(region, that.region) && Objects.equals(eduName, that.eduName) && Objects.equals(birthday, that.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, username, password, surname, name, patronymic, email, city, region, eduName, birthday, blocked);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", edu_name='" + eduName + '\'' +
                ", birthday=" + birthday +
                ", blocked=" + blocked +
                '}';
    }
}