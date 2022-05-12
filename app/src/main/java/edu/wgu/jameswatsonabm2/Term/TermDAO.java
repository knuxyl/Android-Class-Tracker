package edu.wgu.jameswatsonabm2.Term;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM terms ORDER BY id ASC")
    List<Term> getAll();

    @Query("DELETE FROM terms")
    void deleteAll();

    @Query("SELECT MAX(id) FROM terms")
    long getNewId();

    @Query("SELECT * FROM terms WHERE id =:id")
    Term get(long id);
}