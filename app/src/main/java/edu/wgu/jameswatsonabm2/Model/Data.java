package edu.wgu.jameswatsonabm2.Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.wgu.jameswatsonabm2.Assessment.Assessment;
import edu.wgu.jameswatsonabm2.Course.Course;
import edu.wgu.jameswatsonabm2.Term.Term;

public class Data {
    private static ArrayList<Term> terms = new ArrayList<>();
    private static ArrayList<Course> courses = new ArrayList<>();
    private static ArrayList<Assessment> assessments = new ArrayList<>();
    private static Term currentTerm;
    private static Course currentCourse;
    private static Assessment currentAssessment;
    private static String notes;
    private static boolean isTermAssigned;
    private static boolean isCourseAssigned;

    public static ArrayList<Term> getTerms() {
        return terms;
    }

    public static void setTerms(List<Term> listTerms) {
        terms = new ArrayList<>(listTerms);
    }

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public static void setCourses(List<Course> listCourses) {
        courses = new ArrayList<>(listCourses);
    }

    public static ArrayList<Assessment> getAssessments() {
        return assessments;
    }

    public static void setAssessments(List<Assessment> listAssessments) {
        assessments = new ArrayList<>(listAssessments);
    }

    public static void add(Term term) {
        terms.add(term);
    }

    public static void add(Course course) {
        courses.add(course);
    }

    public static void add(Assessment assessment) {
        assessments.add(assessment);
    }

    public static void update(Term term) {
        for (Term originalTerm : terms) {
            if (originalTerm.getId() == term.getId()) {
                terms.set(terms.indexOf(originalTerm), term);
                return;
            }
        }
    }

    public static void update(Course course) {
        for (Course originalCourse : courses) {
            if (originalCourse.getId() == course.getId()) {
                courses.set(courses.indexOf(originalCourse), course);
                return;
            }
        }
    }

    public static void update(Assessment assessment) {
        for (Assessment originalAssessment : assessments) {
            if (originalAssessment.getId() == assessment.getId()) {
                assessments.set(assessments.indexOf(originalAssessment), assessment);
                return;
            }
        }
    }

    public static void delete(Term term) {
        terms.remove(term);
    }

    public static void delete(Course course) {
        courses.remove(course);
    }

    public static void delete(Assessment assessment) {
        assessments.remove(assessment);
    }

    public static Term getCurrentTerm() {
        return currentTerm;
    }

    public static void setCurrentTerm(Term term) {
        currentTerm = term;
    }

    public static Course getCurrentCourse() {
        return currentCourse;
    }

    public static void setCurrentCourse(Course course) {
        currentCourse = course;
    }

    public static Assessment getCurrentAssessment() {
        return currentAssessment;
    }

    public static void setCurrentAssessment(Assessment assessment) {
        currentAssessment = assessment;
    }

    public static void clearCurrentTerm() {
        currentTerm = null;
    }

    public static void clearCurrentCourse() {
        currentCourse = null;
    }

    public static void clearCurrentAssessment() {
        currentAssessment = null;
    }

    public static ArrayList<Course> getTermCourses(Term term) {
        ArrayList<Course> termCourses = new ArrayList<>();
        if (term != null) {
            for (Course course : courses) {
                if (course.getTermId() == term.getId()) {
                    termCourses.add(course);
                }
            }
        }
        Comparator<Course> sortCourses = Comparator
                .comparing(Course::getStatus)
                .thenComparing(Course::getId);
        termCourses.stream().sorted(sortCourses).collect(Collectors.toList());
        return termCourses;
    }

    public static ArrayList<Course> getTermCourses(long id) {
        ArrayList<Course> termCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getTermId() == id) {
                termCourses.add(course);
            }
        }
        Comparator<Course> sortCourses = Comparator
                .comparing(Course::getStatus)
                .thenComparing(Course::getId);
        termCourses.stream().sorted(sortCourses).collect(Collectors.toList());
        return termCourses;
    }

    public static ArrayList<Assessment> getCourseAssessments(Course course) {
        ArrayList<Assessment> courseAssessments = new ArrayList<>();
        if (course != null) {
            for (Assessment assessment : assessments) {
                if (assessment.getCourseId() == course.getId()) {
                    courseAssessments.add(assessment);
                }
            }
        }

        return courseAssessments;
    }

    public static ArrayList<Assessment> getCourseAssessments(long id) {
        ArrayList<Assessment> courseAssessments = new ArrayList<>();
        for (Assessment assessment : assessments) {
            if (assessment.getCourseId() == id) {
                courseAssessments.add(assessment);
            }
        }

        return courseAssessments;
    }

    public static String getNotes() {
        return notes;
    }

    public static void setNotes(String newNotes) {
        notes = newNotes;
    }

    public static boolean getIsTermAssigned() {
        return isTermAssigned;
    }

    public static void setIsTermAssigned(boolean bool) {
        isTermAssigned = bool;
    }

    public static boolean getIsCourseAssigned() {
        return isCourseAssigned;
    }

    public static void setIsCourseAssigned(boolean bool) {
        isCourseAssigned = bool;
    }

    public static Term getTerm(long id) {
        for (Term term : terms) {
            if (term.getId() == id) {
                return term;
            }
        }
        return null;
    }

    public static Course getCourse(long id) {
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }
        return null;
    }
}