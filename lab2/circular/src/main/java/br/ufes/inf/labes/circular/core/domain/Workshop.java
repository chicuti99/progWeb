package br.ufes.inf.labes.circular.core.domain;

import java.time.LocalDate;
import br.ufes.inf.labes.jbutler.ejb.persistence.PersistentObjectSupport;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Workshop extends PersistentObjectSupport implements Comparable<Workshop> {
    @Size(max = 100)
    private String name;

    @Size(max = 10)
    private String acronym;

    @NotNull
    private int year;

    @NotNull
    private LocalDate submissionDeadline;

    @NotNull
    private LocalDate reviewDeadline;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public LocalDate getSubmissionDeadline() {
        return submissionDeadline;
    }

    public void setSubmissionDeadline(LocalDate submissionDeadline) {
        this.submissionDeadline = submissionDeadline;
    }

    public LocalDate getReviewDeadline() {
        return reviewDeadline;
    }

    public void setReviewDeadline(LocalDate reviewDeadline) {
        this.reviewDeadline = reviewDeadline;
    }

    @Override
    public int compareTo(Workshop o) {
        return year - o.year;
    }

    @Override
    public String toString() {
        return acronym;
    }
}