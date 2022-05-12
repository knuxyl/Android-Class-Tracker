package edu.wgu.jameswatsonabm2.Assessment;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessments ORDER BY id ASC")
    List<Assessment> getAll();

    @Query("DELETE FROM assessments")
    void deleteAll();

    @Query("SELECT MAX(id) FROM assessments")
    long getNewId();
}