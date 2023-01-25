package uz.shahzod.unzosoft.iosui_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.shahzod.unzosoft.iosui_android.databinding.ActivityMainBinding
import uz.shahzod.unzosoft.iosui_android.ui.extension.showSnackBar
import uz.shahzod.unzosoft.iosui_android.ui.extension.transparentStatusBar
import uz.shahzod.unzosoft.iosui_android.ui.iosDialog.IOSDialog
import uz.shahzod.unzosoft.iosui_android.ui.iosDialog.IOSDialogButton
import uz.shahzod.unzosoft.iosui_android.ui.iosSwitch.IosSwitch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        transparentStatusBar()
        //dialogs()
        switch()
    }

    private fun switch() {
        binding.iosSwitch.setOnCheckedChangeListener(object : IosSwitch.OnCheckedChangeListener {
            override fun onCheckedChanged(switchView: IosSwitch?, isChecked: Boolean) {
                switchView?.showSnackBar(isChecked.toString())
            }
        })
    }

//    private fun dialogs() {
//        binding.iosDialog.setOnClickListener {
//            IOSDialog.Builder(this)
//                .message("Ios dialog...")
//                .enableAnimation(true)
//                .build()
//                .show()
//        }
//
//        binding.iosDialogTitle.setOnClickListener {
//            IOSDialog.Builder(this)
//                .message("App would like to send your notifications")
//                .enableAnimation(true)
//                .positiveButtonText("Allow")
//                .negativeButtonText("Don't allow")
//                .build()
//                .show()
//        }
//
//        binding.iosDialogOptions.setOnClickListener {
//            val list = arrayListOf<IOSDialogButton>().apply {
//                add(IOSDialogButton(1, "User registration", true, IOSDialogButton.TYPE_POSITIVE))
//                add(IOSDialogButton(2, "User replace", true, IOSDialogButton.TYPE_POSITIVE))
//                add(IOSDialogButton(3, "User delete", true, IOSDialogButton.TYPE_POSITIVE))
//                add(IOSDialogButton(4, "Log out", true, IOSDialogButton.TYPE_NEGATIVE))
//            }
//            IOSDialog.Builder(this)
//                .title("Welcome User")
//                .message("Are you ready to change your data?")
//                .enableAnimation(true)
//                .multiOptions(true)
//                .multiOptionsListeners { iosDialog, iosDialogButton ->
//                    iosDialog.dismiss()
//                    when (iosDialogButton.id) {
//                        1 -> it.showSnackBar("User registration")
//                        2 -> it.showSnackBar("User replace")
//                        3 -> it.showSnackBar("User delete")
//                        4 -> it.showSnackBar("Log out")
//                    }
//                }
//                .iosDialogButtonList(list)
//                .build()
//                .show()
//        }
//    }
}