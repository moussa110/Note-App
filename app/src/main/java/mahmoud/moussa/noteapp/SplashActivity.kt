package mahmoud.moussa.noteapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splash_note_tv.translationX=-1000f
        splash_note_tv.animate().translationXBy(1000f).duration = 2000

        splash_makeNote_tv.translationX=-1000f
        splash_makeNote_tv.animate().translationXBy(1000f).duration = 2000

        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
        },3000)


    }
}
