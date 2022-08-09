package io.john6.android.bottomdialogdemo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import androidx.core.view.WindowCompat
import io.john6.android.bottomdialogdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
//        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.O_MR1) {
//            window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
//        }
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        mBinding.test.setOnClickListener {
            startActivity(Intent(this, DialogTestAtt::class.java))
        }
        mBinding.floatingNavDialogDemo.setOnClickListener {
            DemoFloatingBottomWithNavDialog(this).show()
        }
        mBinding.noFloatingDialogDemo.setOnClickListener {
            DemoFloatingBottomWithNavDialog(this).show()
        }
        mBinding.noFloatingBottomSheetDialogDemo.setOnClickListener {
            DemoNoFloatingBottomSheetDialog(this).show()
        }
    }
}