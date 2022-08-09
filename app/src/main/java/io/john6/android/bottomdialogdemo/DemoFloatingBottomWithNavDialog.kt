package io.john6.android.bottomdialogdemo

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * 某些三方库，Floating 为 true
 * 这种情况改变不了导航栏颜色，改变不了亮色导航栏
 */
class DemoFloatingBottomWithNavDialog(context: Context):Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_bottom_test)


        window.baseConfig()

        window?.also {
            it.decorView.systemUiVisibility =
                it.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            ViewCompat.setOnApplyWindowInsetsListener(it.decorView){_,insets->
                val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//                it.getMyContentView()?.setPaddingRelative(0, 0, 0, inset.bottom)
                insets
            }
        }

    }
}