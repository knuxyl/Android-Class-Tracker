package edu.wgu.jameswatsonabm2.Assessment;

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

public class AssessmentMainActivity extends AppCompatActivity {
    TextView textCountAssessments;
    RecyclerView recyclerViewAssessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Assessments");
        Repository repo = new Repository(getApplication());
        textCountAssessments = findViewById(R.id.textCountAssessments);
        recyclerViewAssessments = findViewById(R.id.recyclerViewAssessments);
        setupRecycler();
    }

    public void setupRecycler() {
        MainActivity.cleanup();
        MainActivity.setupData();
        AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentAdapter.setData(Data.getAssessments());
        recyclerViewAssessments.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAssessments.setAdapter(assessmentAdapter);
        recyclerViewAssessments.setNestedScrollingEnabled(false);
        textCountAssessments.setText(Long.toString(Data.getAssessments().size()));
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

    public void buttonCreateAssessment(View view) {
        if (Data.getTerms().isEmpty()) {
            Toast.makeText(this, "You must create a term and a course first", Toast.LENGTH_SHORT).show();
        } else if (Data.getCourses().isEmpty()) {
            Toast.makeText(this, "You must create a course first", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this, AssessmentDetailsActivity.class));
        }
    }
}