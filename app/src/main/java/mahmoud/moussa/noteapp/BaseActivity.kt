package mahmoud.moussa.noteapp

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity:AppCompatActivity() {
    fun showMessage(title:String?=null,message:String?=null,positveString:String?=null
                    ,positveAction:DialogInterface.OnClickListener?=null
                    ,negativeString:String?=null,negativaAction:DialogInterface.OnClickListener?=null
                    ,neutralString:String?=null,neutralAction:DialogInterface.OnClickListener?=null
                    ,isCancellable:Boolean=true){

        val builder= AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positveString,positveAction)
            .setNegativeButton(negativeString,negativaAction)
            .setNeutralButton(neutralString,neutralAction)
            .setCancelable(isCancellable)
        builder.show()
    }

    fun showMessage(title:Int?=null,message:Int?=null,positveString:Int?=null
                    ,positveAction:DialogInterface.OnClickListener?=null
                    ,negativeString:Int?=null,negativaAction:DialogInterface.OnClickListener?=null
                    ,neutralString:Int?,neutralAction:DialogInterface.OnClickListener?=null
                    ,isCancellable:Boolean=true){

        val builder= AlertDialog.Builder(this)
        if (title!=null)
            builder.setTitle(title)
        if (message!=null)
            builder.setMessage(message)
        if (positveString!=null)
            builder.setPositiveButton(positveString,positveAction)
        if (negativeString!=null)
            builder.setNegativeButton(negativeString,negativaAction)
        if (neutralString!=null)
            builder.setNeutralButton(neutralString,neutralAction)
            builder.setCancelable(isCancellable)
        builder.show()
    }

}