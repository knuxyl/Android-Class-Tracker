package edu.wgu.jameswatsonabm2.Course;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses ORDER BY termId, status, id ASC")
    List<Course> getAll();

    @Query("DELETE FROM courses")
    void deleteAll();

    @Query("SELECT MAX(id) FROM courses")
    long getNewId();

    @Query("SELECT * FROM courses WHERE id =:id")
    Course get(long id);
}