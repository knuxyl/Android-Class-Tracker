package edu.wgu.jameswatsonabm2.Term;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import edu.wgu.jameswatsonabm2.Database.Repository;
import edu.wgu.jameswatsonabm2.MainActivity;
import edu.wgu.jameswatsonabm2.Model.Data;
import edu.wgu.jameswatsonabm2.R;

public class TermMainActivity extends AppCompatActivity {
    TextView textCountTerms;
    RecyclerView recyclerViewTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Terms");
        Repository repo = new Repository(getApplication());
        textCountTerms = findViewById(R.id.textCountTerms);
        recyclerViewTerms = findViewById(R.id.recyclerViewTerms);
        setupRecycler();
    }

    public void setupRecycler() {
        MainActivity.cleanup();
        MainActivity.setupData();
        TermAdapter termAdapter = new TermAdapter(this);
        termAdapter.setData(Data.getTerms());
        recyclerViewTerms.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTerms.setAdapter(termAdapter);
        recyclerViewTerms.setNestedScrollingEnabled(false);
        textCountTerms.setText(Long.toString(Data.getTerms().size()));
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

    public void buttonCreateTerm(View view) {
        startActivity(new Intent(this, TermDetailsActivity.class));
    }
}