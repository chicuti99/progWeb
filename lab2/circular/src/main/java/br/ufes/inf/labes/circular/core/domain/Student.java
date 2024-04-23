package br.ufes.inf.labes.circular.core.domain;

import br.ufes.inf.labes.jbutler.ejb.persistence.PersistentObjectSupport;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;

@Entity
public class Student extends PersistentObjectSupport implements Comparable<Student> {
    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String registration;

    @Size(max = 100)
    private String course;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public int compareTo(Student o) {
        return registration.compareTo(o.registration);
    }

    @Override
    public String toString() {
        return registration;
    }
}
