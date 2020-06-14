package mahmoud.moussa.noteapp

import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_note.*
import mahmoud.moussa.noteapp.database.Note
import mahmoud.moussa.noteapp.database.NoteDatabase
import java.util.*

class AddNoteActivity : BaseActivity() {

    var minute:Int?= null
    var hour:Int?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        setSupportActionBar(add_toolbar)

        add_time_tv.setOnClickListener {
            showTimePicker()
        }
        var type=intent.getIntExtra(Constants.TRANSACTION_TYPE,-1)


            if (type == 1) {
                supportActionBar?.title="add new note"
                add_modify_btn.text = "Add"
                add_modify_btn.setOnClickListener {
                    addData()
                }
            }
            if (type == 2) {
                supportActionBar?.title="modify note"
                add_modify_btn.text = "Modify"

                var note = intent.getSerializableExtra(Constants.EDIT_NOTE_EXTRA) as Note?
                showDataToUpdate(note)

                add_modify_btn.setOnClickListener{
                        updateData(note)
                }
            }


    }

    private fun updateData(note: Note?) {
        if (validateData()) {
            NoteDatabase.getInstance(applicationContext)
                ?.noteDao()
                ?.updateNote(
                    Note(
                        note?.id, add_title_ed.editText?.text.toString()
                        , add_desc_ed.editText?.text.toString()
                        , add_time_tv.text.toString()
                    )
                )

            showMessage("Done","note updated ☺","ok",DialogInterface.OnClickListener{
                dialogInterface, i ->
                    this.finish()
            })
        }
    }


    private fun showDataToUpdate(note:Note?) {
        add_title_ed.editText?.setText(note?.title.toString())
        add_desc_ed.editText?.setText(note?.description.toString())
        add_time_tv.text = note?.time.toString()
    }

    private fun addData() {
        if (validateData()){
            var note=Note(title=add_title_ed.editText?.text.toString(),
                description = add_desc_ed.editText?.text.toString(),
                time = ""+hour+":"+minute)


            NoteDatabase.getInstance(applicationContext)?.noteDao()?.insertNote(note)
            showMessage("Done","Note added ☺ ","ok",DialogInterface.OnClickListener{
                dialogInterface, i ->
                dialogInterface.dismiss()
                this.finish()
            },isCancellable = false)
        }
    }

    private fun validateData():Boolean {
        var isValidate=true
        if(add_title_ed.editText?.text.isNullOrBlank()){
            isValidate=false
            add_title_ed.error="TITLE"
        }else {
            add_title_ed.error = null
        }

        if(add_desc_ed.editText?.text.isNullOrBlank()){
            isValidate=false
            add_desc_ed.error="DESCRIPTION"
        }else {
            add_desc_ed.error = null
        }

        if (add_time_tv.text.equals("time")){
            isValidate=false
            showMessage("error","please select time !!","ok",DialogInterface.OnClickListener{
                dialogInterface, i ->
                dialogInterface.dismiss()


            },isCancellable = false)
        }
        return isValidate
    }

    private fun showTimePicker() {

        val cal=Calendar.getInstance()
        val timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener {
                timePicker, hrs, mins ->
            hour=hrs
            minute=mins
            add_time_tv.text = ""+hrs+":"+mins
        },cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
    }

}
