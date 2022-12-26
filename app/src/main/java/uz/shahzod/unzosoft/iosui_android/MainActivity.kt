package uz.shahzod.unzosoft.iosui_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.shahzod.unzosoft.iosui_android.ui.iosDialog.IOSDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        IOSDialog.Builder(this)
            .message("IOS custom dialog")
            .build()
            .show()

    }
}