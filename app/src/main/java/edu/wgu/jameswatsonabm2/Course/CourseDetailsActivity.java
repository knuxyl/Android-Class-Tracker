package edu.wgu.jameswatsonabm2.Course;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import edu.wgu.jameswatsonabm2.Assessment.Assessment;
import edu.wgu.jameswatsonabm2.Assessment.AssessmentAdapter;
import edu.wgu.jameswatsonabm2.Assessment.AssessmentDetailsActivity;
import edu.wgu.jameswatsonabm2.Database.Repository;
import edu.wgu.jameswatsonabm2.Model.Data;
import edu.wgu.jameswatsonabm2.NotificationReceiver;
import edu.wgu.jameswatsonabm2.R;
import edu.wgu.jameswatsonabm2.Term.Term;

public class CourseDetailsActivity extends AppCompatActivity {
    Repository repo;
    TextView textTitleHeader;
    Spinner spinnerTerms;
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    Spinner spinnerStatus;
    EditText editInsName;
    EditText editInsPhone;
    EditText editInsEmail;
    TextView textCountAssessments;
    Term currentTerm;
    Course currentCourse;
    long termId;
    long courseId;
    String notes;
    RecyclerView recyclerViewAssessments;
    Calendar calendarStartDate = Calendar.getInstance();
    Calendar calendarEndDate = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dialogStartDate;
    DatePickerDialog.OnDateSetListener dialogEndDate;
    SimpleDateFormat formatDate;
    Switch switchStartDate;
    Switch switchEndDate;
    AlarmManager alarmManager;
    int startId;
    int endId;
    PendingIntent intentNotifyStart;
    PendingIntent intentNotifyEnd;
    Intent intentStart;
    Intent intentEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        repo = new Repository(getApplication());
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        formatDate = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        textTitleHeader = findViewById(R.id.textHeaderCourse);
        spinnerTerms = findViewById(R.id.spinnerTerms);
        editTitle = findViewById(R.id.editTitleCourse);
        editStartDate = findViewById(R.id.editStartDateCourse);
        editEndDate = findViewById(R.id.editEndDateCourse);
        textCountAssessments = findViewById(R.id.textCountAssessments);
        editInsName = findViewById(R.id.editInsName);
        editInsPhone = findViewById(R.id.editInsPhone);
        editInsEmail = findViewById(R.id.editInsEmail);
        spinnerStatus = findViewById(R.id.spinnerStatusCourse);
        switchStartDate = findViewById(R.id.switchStartDateCourse);
        switchEndDate = findViewById(R.id.switchEndDateCourse);
        List<String> listStatus = Arrays.asList("", "In-Progress", "Plan to take", "Completed", "Dropped");
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(this, R.layout.view_spinner, listStatus);
        spinnerStatus.setAdapter(adapterStatus);
        currentTerm = Data.getCurrentTerm();
        currentCourse = Data.getCurrentCourse();
        intentStart = new Intent(this, NotificationReceiver.class);
        intentStart.putExtra("title", "Course started");
        intentEnd = new Intent(this, NotificationReceiver.class);
        intentEnd.putExtra("title", "Course ended");
        List<String> listTerms = new ArrayList<>();
        if (currentCourse == null) {
            courseId = repo.getNewCourseId();
            setTitle("Create a new course");
            Data.setNotes("");
        } else {
            intentStart.putExtra("message", currentCourse.getTitle());
            intentEnd.putExtra("message", currentCourse.getTitle());

            courseId = currentCourse.getId();
            setTitle("Course details");
            textTitleHeader.setText(currentCourse.getTitle());
            editTitle.setText(currentCourse.getTitle());
            editInsName.setText(currentCourse.getInsName());
            editInsPhone.setText(currentCourse.getInsPhone());
            editInsEmail.setText(currentCourse.getInsEmail());
            spinnerStatus.setSelection(currentCourse.getStatus());
            Data.setNotes(currentCourse.getNotes());
            if (currentCourse.getStartDate() != null) {
                editStartDate.setText(formatDate.format(currentCourse.getStartDate()));
                calendarStartDate.setTime(currentCourse.getStartDate());
                intentStart.putExtra("date", "(" + formatDate.format(currentCourse.getStartDate()) + ")");
            }
            if (currentCourse.getEndDate() != null) {
                editEndDate.setText(formatDate.format(currentCourse.getEndDate()));
                calendarEndDate.setTime(currentCourse.getEndDate());
                intentEnd.putExtra("date", "(" + formatDate.format(currentCourse.getEndDate()) + ")");
            }
        }
        if (currentTerm == null) {
            if (Data.getIsTermAssigned()) {
                termId = repo.getNewTermId();
                listTerms.add("New term");
                spinnerTerms.setEnabled(false);
                spinnerTerms.setBackground(null);
            } else {
                termId = Data.getTerms().get(0).getId();
                for (Term term : Data.getTerms()) {
                    listTerms.add(term.getTitle());
                }
            }
        } else {
            termId = currentTerm.getId();
            listTerms.add(currentTerm.getTitle());
            spinnerTerms.setEnabled(false);
            spinnerTerms.setBackground(null);
        }
        dialogStartDate = (view, year, monthOfYear, dayOfMonth) -> {
            calendarStartDate.set(Calendar.YEAR, year);
            calendarStartDate.set(Calendar.MONTH, monthOfYear);
            calendarStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateStartDate();
        };
        dialogEndDate = (view, year, monthOfYear, dayOfMonth) -> {
            calendarEndDate.set(Calendar.YEAR, year);
            calendarEndDate.set(Calendar.MONTH, monthOfYear);
            calendarEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateEndDate();
        };
        setupData();
        ArrayAdapter<String> adapterTerms = new ArrayAdapter<>(this, R.layout.view_spinner, listTerms);
        spinnerTerms.setAdapter(adapterTerms);
        String alertId = "1" + (termId + 100) + (courseId + 100);
        startId = Integer.parseInt(alertId + "1");
        endId = Integer.parseInt(alertId + "2");
        intentNotifyStart = PendingIntent.getBroadcast(this,
                startId,
                intentStart,
                PendingIntent.FLAG_NO_CREATE);
        intentNotifyEnd = PendingIntent.getBroadcast(this,
                endId,
                intentEnd,
                PendingIntent.FLAG_NO_CREATE);
        if (intentNotifyStart != null) {
            switchStartDate.setChecked(true);
        }
        if (intentNotifyEnd != null) {
            switchEndDate.setChecked(true);
        }
        spinnerTerms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (currentTerm == null && !Data.getIsTermAssigned()) {
                    termId = Data.getTerms().get(position).getId();
                    String alertId = Long.toString(termId + 100) + (courseId + 100);
                    startId = Integer.parseInt(alertId + "1");
                    endId = Integer.parseInt(alertId + "2");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    public void setupData() {
        notes = Data.getNotes();
        recyclerViewAssessments = findViewById(R.id.recyclerViewAssessments);
        AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        if (currentCourse == null) {
            assessmentAdapter.setData(Data.getCourseAssessments(repo.getNewCourseId()));
            textCountAssessments.setText(Integer.toString(Data.getCourseAssessments(repo.getNewCourseId()).size()));
        } else {
            assessmentAdapter.setData(Data.getCourseAssessments(currentCourse));
            textCountAssessments.setText(Integer.toString(Data.getCourseAssessments(currentCourse).size()));
        }
        recyclerViewAssessments.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAssessments.setAdapter(assessmentAdapter);
        recyclerViewAssessments.setNestedScrollingEnabled(false);
    }

    @Override
    public void onResume() {
        setupData();
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuDelete) {
            deleteCourse();
        } else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (currentCourse != null) {
            getMenuInflater().inflate(R.menu.menu_delete, menu);
            menu.findItem(R.id.menuDelete).setTitle("Delete course");
        }
        return true;
    }

    public void buttonCourseNotes(View view) {
        startActivity(new Intent(this, CourseNotesActivity.class));
    }

    public void buttonCreateAssessment(View view) {
        Data.setIsCourseAssigned(true);
        Data.clearCurrentAssessment();
        startActivity(new Intent(this, AssessmentDetailsActivity.class));
    }

    public void deleteCourse() {
        Toast.makeText(this, "Course \"" + Data.getCurrentCourse().getTitle() + "\" deleted", Toast.LENGTH_SHORT).show();
        Data.delete(currentCourse);
        repo.delete(currentCourse);
        for (Assessment assessment : Data.getCourseAssessments(currentCourse)) {
            Data.delete(assessment);
            repo.delete(assessment);
        }
        finish();
    }

    public void updateStartDate() {
        editStartDate.setText(formatDate.format(calendarStartDate.getTime()));
    }

    public void updateEndDate() {
        editEndDate.setText(formatDate.format(calendarEndDate.getTime()));
    }

    public void buttonSaveCourse(View view) throws ParseException {
        if (inputValidated()) {
            Date startDate = null;
            Date endDate = null;
            intentStart.putExtra("message", editTitle.getText().toString());
            intentEnd.putExtra("message", editTitle.getText().toString());
            if (!editStartDate.getText().toString().equals("")) {
                startDate = formatDate.parse(editStartDate.getText().toString());
                intentStart.putExtra("date", "(" + formatDate.format(startDate) + ")");
                if (switchStartDate.isChecked()) {
                    if (intentNotifyStart != null) {
                        alarmManager.cancel(intentNotifyStart);
                        intentNotifyStart.cancel();
                    }
                    intentNotifyStart = PendingIntent.getBroadcast(this,
                            startId,
                            intentStart,
                            0);
                    alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, startDate.getTime(), intentNotifyStart);
                } else {
                    if (intentNotifyStart != null) {
                        alarmManager.cancel(intentNotifyStart);
                        intentNotifyStart.cancel();
                    }
                }
            }
            if (!editEndDate.getText().toString().equals("")) {
                endDate = formatDate.parse(editEndDate.getText().toString());
                intentEnd.putExtra("date", "(" + formatDate.format(endDate) + ")");
                if (switchEndDate.isChecked()) {
                    if (intentNotifyEnd != null) {
                        alarmManager.cancel(intentNotifyEnd);
                        intentNotifyEnd.cancel();
                    }
                    intentNotifyEnd = PendingIntent.getBroadcast(this,
                            endId,
                            intentEnd,
                            0);
                    alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, endDate.getTime(), intentNotifyEnd);
                } else {
                    if (intentNotifyEnd != null) {
                        alarmManager.cancel(intentNotifyEnd);
                        intentNotifyEnd.cancel();
                    }
                }
            }


            Course course = new Course(
                    courseId,
                    termId,
                    editTitle.getText().toString(),
                    startDate,
                    endDate,
                    notes,
                    spinnerStatus.getSelectedItemPosition(),
                    editInsName.getText().toString(),
                    editInsPhone.getText().toString(),
                    editInsEmail.getText().toString());
            if (currentCourse == null) {
                Data.add(course);
                repo.insert(course);
            } else {
                Data.update(course);
                repo.update(course);
            }
            Toast.makeText(this, "Course \"" + editTitle.getText() + "\" saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public boolean inputValidated() {
        return true;
    }

    public void buttonCancelCourse(View view) {
        cancel();
    }

    public void cancel() {

        if (currentCourse == null) {
            for (Assessment assessment : Data.getAssessments()) {
                if (assessment.getCourseId() == courseId) {
                    Data.delete(assessment);
                    repo.delete(assessment);
                }
            }
        }
        finish();
    }

    public void buttonStartDateCourse(View view) {
        new DatePickerDialog(this, dialogStartDate,
                calendarStartDate.get(Calendar.YEAR),
                calendarStartDate.get(Calendar.MONTH),
                calendarStartDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void buttonEndDateCourse(View view) {
        new DatePickerDialog(this, dialogEndDate,
                calendarEndDate.get(Calendar.YEAR),
                calendarEndDate.get(Calendar.MONTH),
                calendarEndDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void buttonDeleteStartDateCourse(View view) {
        editStartDate.setText("");
    }

    public void buttonDeleteEndDateCourse(View view) {
        editEndDate.setText("");
    }

    public void buttonDeleteStatusCourse(View view) {
        spinnerStatus.setSelection(0);
    }
}