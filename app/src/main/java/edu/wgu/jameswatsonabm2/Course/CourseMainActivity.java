package edu.wgu.jameswatsonabm2.Course;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import edu.wgu.jameswatsonabm2.Database.Repository;
import edu.wgu.jameswatsonabm2.MainActivity;
import edu.wgu.jameswatsonabm2.Model.Data;
import edu.wgu.jameswatsonabm2.R;

public class CourseMainActivity extends AppCompatActivity {
    TextView textCountCourses;
    RecyclerView recyclerViewCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Courses");
        Repository repo = new Repository(getApplication());
        textCountCourses = findViewById(R.id.textCountCourses);
        recyclerViewCourses = findViewById(R.id.recyclerViewCourses);
        setupRecycler();
    }

    public void setupRecycler() {
        MainActivity.cleanup();
        MainActivity.setupData();
        CourseAdapter courseAdapter = new CourseAdapter(this);
        courseAdapter.setData(Data.getCourses());
        recyclerViewCourses.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCourses.setAdapter(courseAdapter);
        recyclerViewCourses.setNestedScrollingEnabled(false);
        textCountCourses.setText(Long.toString(Data.getCourses().size()));
    }

    @Override
    public void onResume() {
        setupRecycler();
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void buttonCreateCourse(View view) {
        if (Data.getTerms().isEmpty()) {
            Toast.makeText(this, "You must create a term first", Toast.LENGTH_SHORT).show();
        } else {
            Data.setNotes("");
            startActivity(new Intent(this, CourseDetailsActivity.class));
        }
    }
}