package edu.wgu.jameswatsonabm2.Assessment;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long courseId;
    public String title;
    public Date startDate;
    public Date endDate;
    public int type;

    public Assessment(long id, long courseId, String title, Date startDate, Date endDate, int type) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public long getCourseId() {
        return courseId;
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

    public int getType() {
        return type;
    }
}