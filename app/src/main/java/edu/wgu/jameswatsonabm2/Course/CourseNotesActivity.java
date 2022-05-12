package edu.wgu.jameswatsonabm2.Course;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import edu.wgu.jameswatsonabm2.Model.Data;
import edu.wgu.jameswatsonabm2.R;

public class CourseNotesActivity extends AppCompatActivity {
    EditText editNotes;
    TextView textNotesHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notes);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Notes");
        textNotesHeader = findViewById(R.id.textNotesHeader);
        editNotes = findViewById(R.id.editNotes);
        if (Data.getCurrentCourse() == null) {
            textNotesHeader.setText("New course");
        } else {
            textNotesHeader.setText(Data.getCurrentCourse().getTitle());
        }
        editNotes.setText(Data.getNotes());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.buttonShareNotes) {
            Intent notesInfo = new Intent();
            notesInfo.setAction(Intent.ACTION_SEND);
            notesInfo.putExtra(Intent.EXTRA_TEXT, editNotes.getText().toString());
            notesInfo.putExtra(Intent.EXTRA_TITLE, textNotesHeader.getText().toString() + " Notes");
            notesInfo.setType("text/plain");
            Intent notesShare = Intent.createChooser(notesInfo, null);
            startActivity(notesShare);
        } else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    public void buttonCancelNotes(View view) {
        finish();
    }

    public void buttonSaveNotes(View view) {
        Data.setNotes(editNotes.getText().toString());
        finish();
    }
}