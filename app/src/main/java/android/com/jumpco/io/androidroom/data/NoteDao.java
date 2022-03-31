package android.com.jumpco.io.androidroom.data;

import android.com.jumpco.io.androidroom.model.Note;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);
    @Update
    void update(Note note);
    @Delete
    void delete(Note note);
    @Query("DELETE FROM Note")
    void deleteAllNotes();
    @Query("SELECT * FROM Note ORDER BY priority DESC")
     LiveData<List<Note>> getAllNotes();
}
