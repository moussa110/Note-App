package mahmoud.moussa.noteapp.database

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun insertNote(note: Note)

    @Delete
    fun deleteNote(note: Note?)

    @Update
    fun updateNote(note: Note)

    @Query("select * from Note")
    fun getAllNote():MutableList<Note>
}