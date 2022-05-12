package edu.wgu.jameswatsonabm2.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.wgu.jameswatsonabm2.Assessment.Assessment;
import edu.wgu.jameswatsonabm2.Assessment.AssessmentDAO;
import edu.wgu.jameswatsonabm2.Course.Course;
import edu.wgu.jameswatsonabm2.Course.CourseDAO;
import edu.wgu.jameswatsonabm2.Term.Term;
import edu.wgu.jameswatsonabm2.Term.TermDAO;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class DatabaseBuilder extends RoomDatabase {
    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "userdb")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract TermDAO termDAO();

    public abstract CourseDAO courseDAO();

    public abstract AssessmentDAO assessmentDAO();
}
