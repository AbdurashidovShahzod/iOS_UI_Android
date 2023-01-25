package uz.shahzod.unzosoft.iosui_android.ui.extension

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.IntegerRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


/**
 * Created by Abdurashidov Shahzod on 25/01/23 16:31.
 * company QQBank
 * shahzod9933@gmail.com
 */


fun Activity.transparentStatusBar() {
    setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true, window)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    if (Build.VERSION.SDK_INT >= 21) {
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false, window)
        window.statusBarColor = Color.TRANSPARENT
    }
}

fun Window.transparentStatusBar() {
    setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true, this)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    if (Build.VERSION.SDK_INT >= 21) {
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false, this)
        this.statusBarColor = Color.TRANSPARENT
    }
}

private fun setWindowFlag(bits: Int, on: Boolean, window: Window) {
    val winParams = window.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    window.attributes = winParams
}

fun View.showSnackBar(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun View.showSnackBar(message: String) {
    val snack = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snack.show()
}

fun Snackbar.showSnackBar(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}
