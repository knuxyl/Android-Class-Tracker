package edu.wgu.jameswatsonabm2.Course;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long termId;
    public String title;
    public Date startDate;
    public Date endDate;
    public String notes;
    public int status;
    public String insName;
    public String insPhone;
    public String insEmail;

    public Course(long id, long termId, String title, Date startDate, Date endDate, String notes, int status, String insName, String insPhone, String insEmail) {
        this.id = id;
        this.termId = termId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
        this.status = status;
        this.insName = insName;
        this.insPhone = insPhone;
        this.insEmail = insEmail;
    }

    public long getId() {
        return id;
    }

    public long getTermId() {
        return termId;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getNotes() {
        return notes;
    }

    public int getStatus() {
        return status;
    }

    public String getInsName() {
        return insName;
    }

    public String getInsPhone() {
        return insPhone;
    }

    public String getInsEmail() {
        return insEmail;
    }
}