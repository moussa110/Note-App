package mahmoud.moussa.noteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mahmoud.moussa.noteapp.database.Note

class AdapterNote(var data:MutableList<Note>?):RecyclerView.Adapter<AdapterNote.ViewHolder>() {
    var onItemClick:SetOnItemClickListner?=null
    var onEditClick:SetOnItemClickListner?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNote.ViewHolder {
        var v=LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data?.size?:0
    }



    override fun onBindViewHolder(holder: AdapterNote.ViewHolder, position: Int) {
        val item=data?.get(position)

        holder.title.text = item?.title
        holder.time.text = item?.time

        if (onEditClick!=null) {
            holder.edit.setOnClickListener {
                onEditClick?.onClick(item)
            }
        }


        if (onItemClick!=null){
            holder.itemView.setOnClickListener {
                onItemClick?.onClick(item)
            }
        }


    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var title:TextView=itemView.findViewById(R.id.item_title_tv)
        var time:TextView=itemView.findViewById(R.id.item_time_tv)
        var edit:Button=itemView.findViewById(R.id.item_edit)


    }

    interface SetOnItemClickListner{
        fun onClick(note: Note?)
    }

    fun changeData(note:MutableList<Note>?){
        data=note
        notifyDataSetChanged()
    }

    fun removeNote(position: Int): Note? {
        return data?.removeAt(position)
        notifyItemRemoved(position)
    }
}