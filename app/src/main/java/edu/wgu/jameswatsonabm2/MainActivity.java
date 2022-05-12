package edu.wgu.jameswatsonabm2;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import edu.wgu.jameswatsonabm2.Assessment.Assessment;
import edu.wgu.jameswatsonabm2.Assessment.AssessmentMainActivity;
import edu.wgu.jameswatsonabm2.Course.Course;
import edu.wgu.jameswatsonabm2.Course.CourseMainActivity;
import edu.wgu.jameswatsonabm2.Database.Repository;
import edu.wgu.jameswatsonabm2.Model.Data;
import edu.wgu.jameswatsonabm2.Term.TermMainActivity;

public class MainActivity extends AppCompatActivity {
    public static Application application;

    public static Application getNewApplication() {
        return application;
    }

    public void setNewApplication(Application newApplication) {
        application = newApplication;
    }

    public static void setupData() {
        cleanup();
        Repository repo = new Repository(getNewApplication());
        Data.setTerms(repo.getAllTerms());
        Data.setCourses(repo.getAllCourses());
        Data.setAssessments(repo.getAllAssessments());
    }

    public static void cleanup() {
        Repository repo = new Repository(getNewApplication());
        for (Course course : repo.getAllCourses()) {
            if (repo.getTerm(course.getTermId()) == null) {
                Data.delete(course);
                repo.delete(course);
            }
        }
        for (Assessment assessment : repo.getAllAssessments()) {
            if (repo.getCourse(assessment.getCourseId()) == null) {
                Data.delete(assessment);
                repo.delete(assessment);
            }
        }
        Data.clearCurrentTerm();
        Data.clearCurrentCourse();
        Data.clearCurrentAssessment();
        Data.setIsTermAssigned(false);
        Data.setIsCourseAssigned(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNewApplication(getApplication());
        setupData();
    }

    @Override
    public void onResume() {
        setupData();
        super.onResume();
    }

    public void buttonTerms(View view) {
        startActivity(new Intent(this, TermMainActivity.class));
    }

    public void buttonCourses(View view) {
        startActivity(new Intent(this, CourseMainActivity.class));
    }

    public void buttonAssessments(View view) {
        startActivity(new Intent(this, AssessmentMainActivity.class));
    }

    public void buttonTesting(View view) {
        startActivity(new Intent(this, TestingActivity.class));
    }
}