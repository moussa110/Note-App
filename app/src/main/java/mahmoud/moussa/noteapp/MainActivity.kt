package mahmoud.moussa.noteapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.dialog_note_details.*
import mahmoud.moussa.noteapp.database.Note
import mahmoud.moussa.noteapp.database.NoteDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var noteAdapter:AdapterNote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val data:MutableList<Note>?=NoteDatabase.getInstance(applicationContext)?.noteDao()?.getAllNote()
        noteAdapter=AdapterNote(data)
        note_rv.adapter=noteAdapter

        noteAdapter.onEditClick=object :AdapterNote.SetOnItemClickListner{
            override fun onClick(note: Note?) {
                var intent=Intent(this@MainActivity,AddNoteActivity::class.java)
                intent.putExtra(Constants.TRANSACTION_TYPE,2)
                intent.putExtra(Constants.EDIT_NOTE_EXTRA,note)

                startActivity(intent)
            }
        }

        noteAdapter.onItemClick=object :AdapterNote.SetOnItemClickListner{
            override fun onClick(note: Note?) {
                showNoteInDialog(note)
            }
        }



        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

                val position = viewHolder.adapterPosition
                val noteToRemove=noteAdapter.removeNote(position)
                noteAdapter.notifyDataSetChanged()
                showSnackBarTodelete(noteToRemove)
            }

        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(note_rv)


        fab.setOnClickListener { view ->
            var intent=Intent(this,AddNoteActivity::class.java)
            intent.putExtra(Constants.TRANSACTION_TYPE,1)
            startActivity(intent)
        }
    }

    private fun showSnackBarTodelete(noteToRemove: Note?) {
        var undoClicked=false
        val snackbar=Snackbar.make(note_rv,"note deleted",3000)
        snackbar.setAction("undo") {
                refreshData()
            undoClicked=true
        }

        snackbar.addCallback(object :BaseTransientBottomBar.BaseCallback<Snackbar>(){
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                if (!undoClicked) {
                    deleteNoteFromDb(noteToRemove)
                }
                undoClicked=false
            }
        })
        snackbar.show()
    }

    private fun deleteNoteFromDb(noteToRemove: Note?) {
            NoteDatabase.getInstance(applicationContext)
                ?.noteDao()
                ?.deleteNote(noteToRemove)
    }

    private fun showNoteInDialog(note: Note?) {

        val dialog=Dialog(this@MainActivity)
        dialog.setContentView(R.layout.dialog_note_details)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        dialog.dialog_title_tv.text = note?.title
        dialog.dialog_desc_tv.text = note?.description
        dialog.dialog_time_tv.text = note?.time

        dialog.dialog_btn.setOnClickListener {
            dialog.cancel()
        }
    }

    private fun refreshData(){
        var notes:MutableList<Note>?= NoteDatabase.getInstance(applicationContext)?.noteDao()?.getAllNote()
        noteAdapter.changeData(notes)
    }

    override fun onStart() {
        super.onStart()
        refreshData()
    }
}
