package edu.wgu.jameswatsonabm2.Term;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import edu.wgu.jameswatsonabm2.Course.CourseAdapter;
import edu.wgu.jameswatsonabm2.Course.CourseDetailsActivity;
import edu.wgu.jameswatsonabm2.Database.Repository;
import edu.wgu.jameswatsonabm2.Model.Data;
import edu.wgu.jameswatsonabm2.R;

public class TermDetailsActivity extends AppCompatActivity {
    Repository repo;
    TextView textTitleHeader;
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    TextView textCountCourses;
    Term currentTerm;
    RecyclerView recyclerViewCourses;
    long termId;
    Calendar calendarStartDate = Calendar.getInstance();
    Calendar calendarEndDate = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dialogStartDate;
    DatePickerDialog.OnDateSetListener dialogEndDate;
    SimpleDateFormat formatDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        repo = new Repository(getApplication());
        formatDate = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        currentTerm = Data.getCurrentTerm();
        textTitleHeader = findViewById(R.id.textHeaderTerm);
        editTitle = findViewById(R.id.editTitleTerm);
        editStartDate = findViewById(R.id.editStartDateTerm);
        editEndDate = findViewById(R.id.editEndDateTerm);
        textCountCourses = findViewById(R.id.textLabelVar);
        setupData();
        if (currentTerm == null) {
            termId = repo.getNewTermId();
            setTitle("Create a new term");
        } else {
            termId = currentTerm.getId();
            setTitle("Term details");
            textTitleHeader.setText(currentTerm.getTitle());
            editTitle.setText(currentTerm.getTitle());
            if (currentTerm.getStartDate() != null) {
                editStartDate.setText(formatDate.format(currentTerm.getStartDate()));
                calendarStartDate.setTime(currentTerm.getStartDate());
            }
            if (currentTerm.getEndDate() != null) {
                editEndDate.setText(formatDate.format(currentTerm.getEndDate()));
                calendarEndDate.setTime(currentTerm.getEndDate());
            }
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
    }

    @Override
    public void onResume() {
        setupData();
        super.onResume();
    }

    public void setupData() {
        recyclerViewCourses = findViewById(R.id.recyclerViewCourses);
        CourseAdapter courseAdapter = new CourseAdapter(this);
        if (currentTerm == null) {
            courseAdapter.setData(Data.getTermCourses(repo.getNewTermId()));
            textCountCourses.setText(Integer.toString(Data.getTermCourses(repo.getNewTermId()).size()));
        } else {
            courseAdapter.setData(Data.getTermCourses(currentTerm));
            textCountCourses.setText(Integer.toString(Data.getTermCourses(currentTerm).size()));
        }
        recyclerViewCourses.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCourses.setAdapter(courseAdapter);
        recyclerViewCourses.setNestedScrollingEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuDelete) {
            deleteTerm();
        } else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (currentTerm != null) {
            getMenuInflater().inflate(R.menu.menu_delete, menu);
            menu.findItem(R.id.menuDelete).setTitle("Delete term");
        }
        return true;
    }

    public void buttonCreateCourse(View view) {
        Data.clearCurrentCourse();
        Data.setIsTermAssigned(true);
        startActivity(new Intent(this, CourseDetailsActivity.class));
    }

    public void deleteTerm() {
        if (textCountCourses.getText().toString().equals("0")) {
            Toast.makeText(this, "Term \"" + currentTerm.getTitle() + "\" deleted", Toast.LENGTH_SHORT).show();
            Data.delete(currentTerm);
            repo.delete(currentTerm);
            finish();
        } else {
            Toast.makeText(this, "Cannot delete a term with courses", Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonSave(View view) throws ParseException {
        if (inputValidated()) {
            Date startDate = null;
            Date endDate = null;
            if (!editStartDate.getText().toString().equals("")) {
                startDate = formatDate.parse(editStartDate.getText().toString());
            }
            if (!editEndDate.getText().toString().equals("")) {
                endDate = formatDate.parse(editEndDate.getText().toString());
            }
            Term term = new Term(
                    termId,
                    editTitle.getText().toString(),
                    startDate,
                    endDate);

            if (currentTerm == null) {
                Data.add(term);
                repo.insert(term);
            } else {
                Data.update(term);
                repo.update(term);
            }
            Toast.makeText(this, "Term \"" + editTitle.getText() + "\" saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public boolean inputValidated() {
        return true;
    }

    public void buttonCancelTerm(View view) {
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

    public void buttonStartDateTerm(View view) {
        new DatePickerDialog(this, dialogStartDate,
                calendarStartDate.get(Calendar.YEAR),
                calendarStartDate.get(Calendar.MONTH),
                calendarStartDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void buttonEndDateTerm(View view) {
        new DatePickerDialog(this, dialogEndDate,
                calendarEndDate.get(Calendar.YEAR),
                calendarEndDate.get(Calendar.MONTH),
                calendarEndDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void buttonDeleteStartDateTerm(View view) {
        editStartDate.setText("");
    }

    public void buttonDeleteEndDateTerm(View view) {
        editEndDate.setText("");
    }
}