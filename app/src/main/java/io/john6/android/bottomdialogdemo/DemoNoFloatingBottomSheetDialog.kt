package io.john6.android.bottomdialogdemo

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetDialog

class DemoNoFloatingBottomSheetDialog(context: Context) : BottomSheetDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_bottom_test)

        window.baseConfig()
        window?.also {
            it.setLayout(MATCH_PARENT, MATCH_PARENT)
            WindowCompat.setDecorFitsSystemWindows(it, false)
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O_MR1) {
            window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }

        window?.also {
            ViewCompat.setOnApplyWindowInsetsListener(it.decorView) { _, insets ->
                val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                it.getMyContentView()?.setPaddingRelative(0, 0, 0, inset.bottom)
                WindowInsetsCompat.CONSUMED
            }
        }

    }
}