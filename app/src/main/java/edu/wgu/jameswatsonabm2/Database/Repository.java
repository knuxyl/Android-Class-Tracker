package edu.wgu.jameswatsonabm2.Database;

import android.app.Application;
import android.content.Context;

import java.util.List;

import edu.wgu.jameswatsonabm2.Assessment.Assessment;
import edu.wgu.jameswatsonabm2.Assessment.AssessmentDAO;
import edu.wgu.jameswatsonabm2.Course.Course;
import edu.wgu.jameswatsonabm2.Course.CourseDAO;
import edu.wgu.jameswatsonabm2.Term.Term;
import edu.wgu.jameswatsonabm2.Term.TermDAO;

public class Repository {
    private final TermDAO termDAO;
    private final CourseDAO courseDAO;
    private final AssessmentDAO assessmentDAO;
    private final Context context;
    private List<Term> allTermEntities;
    private List<Course> allCourses;
    private List<Assessment> allAssessmentEntities;

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        termDAO = db.termDAO();
        courseDAO = db.courseDAO();
        assessmentDAO = db.assessmentDAO();
        this.context = application.getApplicationContext();
    }

    public List<Term> getAllTerms() {
        allTermEntities = termDAO.getAll();
        return allTermEntities;
    }

    public List<Course> getAllCourses() {
        allCourses = courseDAO.getAll();
        return allCourses;
    }

    public List<Assessment> getAllAssessments() {
        allAssessmentEntities = assessmentDAO.getAll();
        return allAssessmentEntities;
    }

    public void insert(Term term) {
        termDAO.insert(term);
    }

    public void insert(Course course) {
        courseDAO.insert(course);
    }

    public void insert(Assessment assessment) {
        assessmentDAO.insert(assessment);
    }

    public void update(Term term) {
        termDAO.update(term);
    }

    public void update(Course course) {
        courseDAO.update(course);
    }

    public void update(Assessment assessment) {
        assessmentDAO.update(assessment);
    }

    public void delete(Term term) {
        termDAO.delete(term);
    }

    public void delete(Course course) {
        courseDAO.delete(course);
    }

    public void delete(Assessment assessment) {
        assessmentDAO.delete(assessment);
    }

    public void deleteTerms() {
        termDAO.deleteAll();
    }

    public void deleteCourses() {
        courseDAO.deleteAll();
    }

    public void deleteAssessments() {
        assessmentDAO.deleteAll();
    }

    public long getNewTermId() {
        return termDAO.getNewId() + 1;
    }

    public long getNewCourseId() {
        return courseDAO.getNewId() + 1;
    }

    public long getNewAssesssmentId() {
        return assessmentDAO.getNewId() + 1;
    }

    public Term getTerm(long id) {
        return termDAO.get(id);
    }

    public Course getCourse(long id) {
        return courseDAO.get(id);
    }
}
