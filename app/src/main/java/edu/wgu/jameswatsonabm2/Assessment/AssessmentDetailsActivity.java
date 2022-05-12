package edu.wgu.jameswatsonabm2.Assessment;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import edu.wgu.jameswatsonabm2.Course.Course;
import edu.wgu.jameswatsonabm2.Database.Repository;
import edu.wgu.jameswatsonabm2.Model.Data;
import edu.wgu.jameswatsonabm2.NotificationReceiver;
import edu.wgu.jameswatsonabm2.R;
import edu.wgu.jameswatsonabm2.Term.Term;

public class AssessmentDetailsActivity extends AppCompatActivity {
    Repository repo;
    TextView textTitleHeader;
    Spinner spinnerCourses;
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    TextView textCountCourses;
    Spinner spinnerType;
    Term currentTerm;
    Course currentCourse;
    Assessment currentAssessment;
    long assessmentId;
    long courseId;
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
        setContentView(R.layout.activity_assessment_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        repo = new Repository(getApplication());
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        formatDate = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        textTitleHeader = findViewById(R.id.textHeaderAssessment);
        spinnerCourses = findViewById(R.id.spinnerCourses);
        editTitle = findViewById(R.id.editTitleAssessment);
        editStartDate = findViewById(R.id.editStartDateAssessment);
        editEndDate = findViewById(R.id.editEndDateAssessment);
        textCountCourses = findViewById(R.id.textTypeAssessment);
        spinnerType = findViewById(R.id.spinnerTypeAssessment);
        switchStartDate = findViewById(R.id.switchStartDateAssessment);
        switchEndDate = findViewById(R.id.switchEndDateAssessment);
        List<String> listType = Arrays.asList("", "Objective", "Performance");
        ArrayAdapter<String> adapterType = new ArrayAdapter<>(this, R.layout.view_spinner, listType);
        spinnerType.setAdapter(adapterType);
        currentTerm = Data.getCurrentTerm();
        currentCourse = Data.getCurrentCourse();
        currentAssessment = Data.getCurrentAssessment();
        intentStart = new Intent(this, NotificationReceiver.class);
        intentStart.putExtra("title", "Assessment started");
        intentEnd = new Intent(this, NotificationReceiver.class);
        intentEnd.putExtra("title", "Assessment ended");
        List<String> listCourses = new ArrayList<>();
        if (currentAssessment == null) {
            assessmentId = repo.getNewAssesssmentId();
            setTitle("Create a new assessment");
        } else {
            intentStart.putExtra("message", currentAssessment.getTitle());
            intentEnd.putExtra("message", currentAssessment.getTitle());
            assessmentId = currentAssessment.getId();
            setTitle("Assessment details");
            textTitleHeader.setText(currentAssessment.getTitle());
            editTitle.setText(currentAssessment.getTitle());
            if (currentAssessment.getStartDate() != null) {
                editStartDate.setText(formatDate.format(currentAssessment.getStartDate()));
                calendarStartDate.setTime(currentAssessment.getStartDate());
                intentStart.putExtra("date", "(" + formatDate.format(currentAssessment.getStartDate()) + ")");
            }
            if (currentAssessment.getEndDate() != null) {
                editEndDate.setText(formatDate.format(currentAssessment.getEndDate()));
                calendarEndDate.setTime(currentAssessment.getEndDate());
                intentEnd.putExtra("date", "(" + formatDate.format(currentAssessment.getEndDate()) + ")");
            }
            spinnerType.setSelection(currentAssessment.getType());
        }
        if (currentCourse == null) {
            if (Data.getIsCourseAssigned()) {
                courseId = repo.getNewCourseId();
                listCourses.add("New course");
                spinnerCourses.setEnabled(false);
                spinnerCourses.setBackground(null);
            } else {
                courseId = Data.getCourses().get(0).getId();
                for (Course course : Data.getCourses()) {
                    listCourses.add(course.getTitle());
                }
            }
        } else {
            courseId = currentCourse.getId();
            listCourses.add(currentCourse.getTitle());
            spinnerCourses.setEnabled(false);
            spinnerCourses.setBackground(null);
        }
        ArrayAdapter<String> adapterCourses = new ArrayAdapter<>(this, R.layout.view_spinner, listCourses);
        spinnerCourses.setAdapter(adapterCourses);
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
        String alertId = "2" + (courseId + 100) + (assessmentId + 100);
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
        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (currentCourse == null && !Data.getIsCourseAssigned()) {
                    courseId = Data.getCourses().get(position).getId();
                    String alertId = "2" + (courseId + 100) + (assessmentId + 100);
                    startId = Integer.parseInt(alertId + "1");
                    endId = Integer.parseInt(alertId + "2");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuDelete) {
            deleteAssessment();
        } else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (currentAssessment != null) {
            getMenuInflater().inflate(R.menu.menu_delete, menu);
            menu.findItem(R.id.menuDelete).setTitle("Delete assessment");
        }
        return true;
    }

    public void deleteAssessment() {
        Toast.makeText(this, "Assessment \"" + currentAssessment.getTitle() + "\" deleted", Toast.LENGTH_SHORT).show();
        Data.delete(currentAssessment);
        repo.delete(currentAssessment);
        finish();
    }

    public void buttonSaveAssessment(View view) throws ParseException {
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

            Assessment assessment = new Assessment(
                    assessmentId,
                    courseId,
                    editTitle.getText().toString(),
                    startDate,
                    endDate,
                    spinnerType.getSelectedItemPosition());

            if (currentAssessment == null) {
                Data.add(assessment);
                repo.insert(assessment);
            } else {
                Data.update(assessment);
                repo.update(assessment);
            }
            Toast.makeText(this, "Assessment \"" + editTitle.getText() + "\" saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public boolean inputValidated() {
        return true;
    }

    public void buttonCancelAssessment(View view) {
        cancel();
    }

    public void cancel() {
        finish();
    }

    public void updateStartDate() {
        editStartDate.setText(formatDate.format(calendarStartDate.getTime()));
    }

    public void updateEndDate() {
        editEndDate.setText(formatDate.format(calendarEndDate.getTime()));
    }

    public void buttonStartDateAssessment(View view) {
        new DatePickerDialog(this, dialogStartDate,
                calendarStartDate.get(Calendar.YEAR),
                calendarStartDate.get(Calendar.MONTH),
                calendarStartDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void buttonEndDateAssessment(View view) {
        new DatePickerDialog(this, dialogEndDate,
                calendarEndDate.get(Calendar.YEAR),
                calendarEndDate.get(Calendar.MONTH),
                calendarEndDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void buttonDeleteStartDateAssessment(View view) {
        editStartDate.setText("");
    }

    public void buttonDeleteEndDateAssessment(View view) {
        editEndDate.setText("");
    }

    public void buttonDeleteTypeAssessment(View view) {
        spinnerType.setSelection(0);
    }
}