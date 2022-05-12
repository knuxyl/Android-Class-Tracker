package edu.wgu.jameswatsonabm2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import edu.wgu.jameswatsonabm2.Assessment.Assessment;
import edu.wgu.jameswatsonabm2.Course.Course;
import edu.wgu.jameswatsonabm2.Database.Repository;
import edu.wgu.jameswatsonabm2.Term.Term;

public class TestingActivity extends AppCompatActivity {
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        repo = new Repository(getApplication());
    }

    public void buttonClearDatabase(View view) {
        repo.deleteAssessments();
        repo.deleteCourses();
        repo.deleteTerms();
    }

    public void buttonInsertCurrentTerm(View view) throws ParseException {
        long termId = repo.getNewTermId();
        long courseId = repo.getNewCourseId();
        long assessmentId = repo.getNewAssesssmentId();
        Date courseStartDate = new Date();
        Date courseEndDate = new Date();
        Course course;
        Assessment assessment;
        SimpleDateFormat formatDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        Date termStartDate = formatDate.parse("Nov 1, 2021");
        Date termEndDate = formatDate.parse("Apr 30, 2022");
        Term term = new Term(termId, "Current Term",
                termStartDate,
                termEndDate);
        repo.insert(term);


        courseStartDate = formatDate.parse("Feb 7, 2022");
        courseEndDate = formatDate.parse("Feb 22, 2022");
        course = new Course(courseId,
                termId,
                "Mobile Application Development – C196",
                courseStartDate,
                courseEndDate,
                "",
                1,
                "Rodger Roberts",
                "(877) 435-7948",
                "someone@wgu.edu");
        repo.insert(course);
        assessment = new Assessment(assessmentId,
                courseId,
                "Task 1 - Mobile Application Development - ABM2",
                courseStartDate,
                courseEndDate,
                2);
        repo.insert(assessment);
        assessmentId++;
        courseId++;


        courseStartDate = null;
        courseEndDate = null;
        course = new Course(courseId,
                termId,
                "Organizational Behavior and Leadership – C484",
                courseStartDate,
                courseEndDate,
                "",
                2,
                "Not Assigned",
                "(877) 435-7948",
                "");
        repo.insert(course);
        assessment = new Assessment(assessmentId,
                courseId,
                "Organizational Behavior and Leadership",
                courseStartDate,
                courseEndDate,
                1);
        repo.insert(assessment);
        assessmentId++;
        courseId++;


        courseStartDate = null;
        courseEndDate = null;
        course = new Course(courseId,
                termId,
                "Software Development Capstone – C868",
                courseStartDate,
                courseEndDate,
                "",
                2,
                "Not Assigned",
                "(877) 435-7948",
                "");
        repo.insert(course);
        assessment = new Assessment(assessmentId,
                courseId,
                "Task 1 - Software Development Capstone - RYM2",
                courseStartDate,
                courseEndDate,
                2);
        repo.insert(assessment);
        assessmentId++;
        assessment = new Assessment(assessmentId,
                courseId,
                "Task 2 - Software Development Capstone - RYM2",
                courseStartDate,
                courseEndDate,
                2);
        repo.insert(assessment);
        assessmentId++;
        courseId++;


        courseStartDate = null;
        courseEndDate = formatDate.parse("Feb 06, 2022");
        course = new Course(courseId,
                termId,
                "Software II - Advanced Java Concepts – C195",
                courseStartDate,
                courseEndDate,
                "",
                3,
                "No longer assigned",
                "(877) 435-7948",
                "");
        repo.insert(course);
        assessment = new Assessment(assessmentId,
                courseId,
                "Task 1 - Software II - Advanced Java Concepts - QAM2",
                courseStartDate,
                courseEndDate,
                2);
        repo.insert(assessment);
        assessmentId++;
        courseId++;


        courseStartDate = null;
        courseEndDate = formatDate.parse("Feb 01, 2022");
        course = new Course(courseId,
                termId,
                "Software I – C482",
                courseStartDate,
                courseEndDate,
                "",
                3,
                "No longer assigned",
                "(877) 435-7948",
                "");
        repo.insert(course);
        assessment = new Assessment(assessmentId,
                courseId,
                "Task 1 - Software I - QKM2",
                courseStartDate,
                courseEndDate,
                2);
        repo.insert(assessment);
        assessmentId++;
        courseId++;


        courseStartDate = null;
        courseEndDate = formatDate.parse("Jan 11, 2022");
        course = new Course(courseId,
                termId,
                "Software Engineering – C188",
                courseStartDate,
                courseEndDate,
                "",
                3,
                "No longer assigned",
                "(877) 435-7948",
                "");
        repo.insert(course);
        assessment = new Assessment(assessmentId,
                courseId,
                "Task 1 - Software Engineering - NUP1",
                courseStartDate,
                courseEndDate,
                2);
        repo.insert(assessment);
        assessmentId++;
        courseId++;


        courseStartDate = null;
        courseEndDate = formatDate.parse("Jan 07, 2022");
        course = new Course(courseId,
                termId,
                "Software Quality Assurance – C857",
                courseStartDate,
                courseEndDate,
                "",
                3,
                "No longer assigned",
                "(877) 435-7948",
                "");
        repo.insert(course);
        assessment = new Assessment(assessmentId,
                courseId,
                "Software Quality Assurance",
                courseStartDate,
                courseEndDate,
                1);
        repo.insert(assessment);
        assessmentId++;
        courseId++;


        courseStartDate = null;
        courseEndDate = formatDate.parse("Jan 06, 2022");
        course = new Course(courseId,
                termId,
                "Advanced Data Management – D191",
                courseStartDate,
                courseEndDate,
                "",
                3,
                "No longer assigned",
                "(877) 435-7948",
                "");
        repo.insert(course);
        assessment = new Assessment(assessmentId,
                courseId,
                "Task 1 - Advanced Data Management - VDM1",
                courseStartDate,
                courseEndDate,
                2);
        repo.insert(assessment);
        assessmentId++;
        courseId++;


        courseStartDate = null;
        courseEndDate = formatDate.parse("Dec 31, 2021");
        course = new Course(courseId,
                termId,
                "Data Structures and Algorithms I – C949",
                courseStartDate,
                courseEndDate,
                "",
                3,
                "No longer assigned",
                "(877) 435-7948",
                "");
        repo.insert(course);
        assessment = new Assessment(assessmentId,
                courseId,
                "Data Structures and Algorithms I",
                courseStartDate,
                courseEndDate,
                1);
        repo.insert(assessment);
        assessmentId++;
        courseId++;


        courseStartDate = null;
        courseEndDate = formatDate.parse("Dec 22, 2021");
        course = new Course(courseId,
                termId,
                "Business of IT - Applications – C846",
                courseStartDate,
                courseEndDate,
                "",
                3,
                "No longer assigned",
                "(877) 435-7948",
                "");
        repo.insert(course);
        assessment = new Assessment(assessmentId,
                courseId,
                "Organizational Behavior and Leadership",
                courseStartDate,
                courseEndDate,
                1);
        repo.insert(assessment);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

}