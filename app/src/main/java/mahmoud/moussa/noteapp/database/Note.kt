package mahmoud.moussa.noteapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note (
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,

    var title:String?=null,
    var description:String?=null,
    var time:String?=null
):Serializable