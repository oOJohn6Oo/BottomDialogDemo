package io.john6.android.bottomdialogdemo

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.StyleRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

/**
 * 仅演示如何适配 Dialog
 * 含多个配置项，可组合查看具体效果
 */
class TestBottomDialog(context: Context, @StyleRes theme: Int = 0) : Dialog(context, theme) {

    // Just for demonstration
    private val viewModel = (context as DialogTestAtt).mViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_bottom_test)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        window?.also {
            it.setContentHeightText()
            it.setWindowAnimations(R.style.dialog_anim)

            WindowCompat.setDecorFitsSystemWindows(it, false)
            it.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            // setOnApplyWindowInsetsListener
            val view = if (viewModel.interceptDecorViewInset.value == true) it.decorView
            else it.decorView.findViewById(R.id.content)
            ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
                val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                if (viewModel.respondToNav.value == true)
                    it.getMyContentView()?.setPaddingRelative(0, 0, 0, inset.bottom)

                // 消费这次的INSETS
                if (viewModel.consumeWindowInset.value == true)
                    WindowInsetsCompat.CONSUMED
                else insets
            }

            // hideNavigation
            if (viewModel.hideNavigation.value == true) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Log.d("lq", "onCreate: R")
                    it.attributes.fitInsetsTypes = WindowInsets.Type.statusBars()
                } else {
                    Log.d("lq", "onCreate: <R")
                    it.decorView.systemUiVisibility = it.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                }
            }
            if (viewModel.removeBackground.value == true)
                it.setBackgroundDrawable(null)

            it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            it.setGravity(Gravity.BOTTOM)
            it.setLayout(
                viewModel.windowWidth.value ?: ViewGroup.LayoutParams.MATCH_PARENT,
                viewModel.windowHeight.value ?: ViewGroup.LayoutParams.WRAP_CONTENT
            )
            if (viewModel.dimBackground.value != true)
                it.setDimAmount(0f)

            WindowCompat.getInsetsController(it, it.decorView)?.also { controller ->
                controller.isAppearanceLightStatusBars = viewModel.lightStatusBar.value == true
                controller.isAppearanceLightNavigationBars =
                    viewModel.lightNavigationBar.value == true
            }

            it.statusBarColor = viewModel.statusBarColor.value ?: Color.TRANSPARENT
            it.navigationBarColor = viewModel.navigationBarColor.value ?: Color.TRANSPARENT

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                it.isNavigationBarContrastEnforced =
                    viewModel.enforceNavigationContract.value == true
                it.isStatusBarContrastEnforced = viewModel.enforceStatusContract.value == true
            }
        }
    }

}
