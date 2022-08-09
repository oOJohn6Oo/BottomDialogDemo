package io.john6.android.bottomdialogdemo

import android.graphics.Color
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DialogTestViewModel : ViewModel() {

    // 是否消耗掉insets
    var consumeWindowInset = MutableLiveData(false)
    // 是否对DecorView拦截WindowInsets，false则只对button添加
    var interceptDecorViewInset = MutableLiveData(false)
    var enforceStatusContract = MutableLiveData(false)
    var enforceNavigationContract = MutableLiveData(false)
    var windowIsFloating = MutableLiveData(false)
    var lightStatusBar = MutableLiveData(false)
    var lightNavigationBar = MutableLiveData(false)
    var dimBackground = MutableLiveData(false)
    var removeBackground = MutableLiveData(false)
    var hideNavigation = MutableLiveData(false)
    var respondToNav = MutableLiveData(false)
    var statusBarColor: MutableLiveData<Int> = MutableLiveData(Color.BLACK)
    var navigationBarColor = MutableLiveData(Color.BLACK)
    var windowWidth = MutableLiveData(MATCH_PARENT)
    var windowHeight = MutableLiveData(WRAP_CONTENT)

    init {
        Log.d("lq", "init ")
    }

    fun setStatusBarColor(status: Boolean, red: Int = -1, green: Int = -1, blue: Int = -1) {
        val currentColor = (if (status) statusBarColor else navigationBarColor).value ?: 0
        val desireRed = if (red == -1) currentColor.red else red
        val desireGreen = if (green == -1) currentColor.green else green
        val desireBlue = if (blue == -1) currentColor.blue else blue

        val desireColor = Color.rgb(desireRed, desireGreen, desireBlue)
        if (status) statusBarColor.value = desireColor
        else navigationBarColor.value = desireColor
    }
}