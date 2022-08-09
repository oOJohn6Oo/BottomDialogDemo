package io.john6.android.bottomdialogdemo

import android.content.res.Resources
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import kotlin.math.roundToInt

fun Window?.setContentHeightText() {
    this?.also { w ->
        w.decorView.doOnPreDraw {
            w.getMyTextView()?.text = "${250.dp.roundToInt()} -> ${w.getMyContentView()?.measuredHeight ?: 0}"
        }
    }
}

fun Window?.getMyContentView(): FrameLayout? = this?.decorView?.findViewById(R.id.content)
fun Window?.getMyTextView(): TextView? = this?.decorView?.findViewById(R.id.size_text_dg_bt)
fun Window?.baseConfig(){
    this?.also {
        it.setContentHeightText()
        it.setWindowAnimations(R.style.dialog_anim)
        it.setGravity(Gravity.BOTTOM)
        it.setBackgroundDrawable(null)
        it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}

val Int.dp
get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics)