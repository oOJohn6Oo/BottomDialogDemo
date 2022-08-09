package io.john6.android.bottomdialogdemo

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DemoNoFloatingBottomDialog(context: Context):Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_bottom_test)

        window.baseConfig()
        window?.navigationBarColor = Color.RED
        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.O_MR1) {
            window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }

        window?.also {
            ViewCompat.setOnApplyWindowInsetsListener(it.decorView){_,insets->
                val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                it.getMyContentView()?.setPaddingRelative(0, 0, 0, inset.bottom)
                insets
            }
        }

    }
}