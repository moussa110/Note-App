package mahmoud.moussa.noteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Note::class),version = 1)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao():NoteDao

    companion object{

        val DB_NAME="DB-NOTE"
        private var noteDatabase:NoteDatabase?=null

        fun getInstance(context: Context): NoteDatabase? {
            if (noteDatabase== null){
                noteDatabase = Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java, DB_NAME
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return noteDatabase
        }
    }

}