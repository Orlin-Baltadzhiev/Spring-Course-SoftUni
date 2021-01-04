package entities.UniversitySystem;

import entities.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Students")
public class Student extends BaseEntity {
    private String firstName;
    private String lastName;
    private long phoneNumber;
    private double avarageGrade;
    private boolean attendance;
    private Set<Course> courses;

    public Student() {
    }
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Column(name = "phone_number")
    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Column(name = "avarage_grade")
    public double getAvarageGrade() {
        return avarageGrade;
    }

    public void setAvarageGrade(double avarageGrade) {
        this.avarageGrade = avarageGrade;
    }
    @Column (name = "attendance")
    public boolean isAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }
    @ManyToMany (mappedBy = "students",targetEntity = Course.class)
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
