package io.john6.android.bottomdialogdemo

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import io.john6.android.bottomdialogdemo.databinding.ActivityDialogTestBinding
import io.john6.android.bottomdialogdemo.databinding.ItemSwitchBinding
import kotlin.math.roundToInt

class DialogTestAtt : AppCompatActivity() {

    private lateinit var mBinding: ActivityDialogTestBinding
    lateinit var mViewModel: DialogTestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        mBinding = ActivityDialogTestBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DialogTestViewModel::class.java)

        initView()
        initInsets()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBinding.scrollViewAttTest.setOnScrollChangeListener { v, _, _, _, _ ->
                val canScrollDown = v.canScrollVertically(1)
                mBinding.bottomViewAttTest.elevation = if (canScrollDown) 6.dp else 0f
            }
        }
    }

    private fun initInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.root) { _, insets ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            mBinding.scrollViewAttTest.setPaddingRelative(0, inset.top, 0, 0)
            mBinding.bottomViewAttTest.setPaddingRelative(0, 0, 0, inset.bottom)

            insets
        }
    }

    private fun initView() {
        mBinding.statusColor.titleItemCc.text = "StatusBar Color"
        mBinding.navColor.titleItemCc.text = "NavigationBar Color"

        mBinding.removeBgd.titleItemS.text = "Remove Background"
        mBinding.enforceNavigation.titleItemS.text = "EnforceNavigationContract"
        mBinding.enforceStatus.titleItemS.text = "EnforceStatusContract"
        mBinding.consumeWindowInsets.titleItemS.text = "ConsumeWindowInsets"
        mBinding.decorIntercept.titleItemS.text = "InterceptDecorView"
        mBinding.floating.titleItemS.text = "WindowIsFloating"
        mBinding.lightStatus.titleItemS.text = "Light StatusBar"
        mBinding.lightNav.titleItemS.text = "Light NavigationBar"
        mBinding.dimBgd.titleItemS.text = "DimBackground"
        mBinding.hideNav.titleItemS.text = "FLAG_LAYOUT_HIDE_NAVIGATION"
        mBinding.respondNav.titleItemS.text = "Responds To NavigationBar"

        setupSwitchItem(mBinding.decorIntercept, mViewModel.interceptDecorViewInset)
        setupSwitchItem(mBinding.consumeWindowInsets, mViewModel.consumeWindowInset)
        setupSwitchItem(mBinding.dimBgd, mViewModel.dimBackground)
        setupSwitchItem(mBinding.lightStatus, mViewModel.lightStatusBar)
        setupSwitchItem(mBinding.lightNav, mViewModel.lightNavigationBar)
        setupSwitchItem(mBinding.enforceStatus, mViewModel.enforceStatusContract)
        setupSwitchItem(mBinding.enforceNavigation, mViewModel.enforceNavigationContract)
        setupSwitchItem(mBinding.floating, mViewModel.windowIsFloating)
        setupSwitchItem(mBinding.removeBgd, mViewModel.removeBackground)
        setupSwitchItem(mBinding.hideNav, mViewModel.hideNavigation)
        setupSwitchItem(mBinding.respondNav, mViewModel.respondToNav)

        // 状态栏颜色
        mBinding.statusColor.slideRItemCc.addOnChangeListener { _, value, _ ->
            mViewModel.setStatusBarColor(true, red = value.roundToInt())
        }
        mBinding.statusColor.slideGItemCc.addOnChangeListener { _, value, _ ->
            mViewModel.setStatusBarColor(true, green = value.roundToInt())
        }
        mBinding.statusColor.slideBItemCc.addOnChangeListener { _, value, _ ->
            mViewModel.setStatusBarColor(true, blue = value.roundToInt())
        }

        setupSlider()

        // 导航栏颜色
        mBinding.navColor.slideRItemCc.addOnChangeListener { _, value, _ ->
            mViewModel.setStatusBarColor(false, red = value.roundToInt())
        }
        mBinding.navColor.slideGItemCc.addOnChangeListener { _, value, _ ->
            mViewModel.setStatusBarColor(false, green = value.roundToInt())
        }
        mBinding.navColor.slideBItemCc.addOnChangeListener { _, value, _ ->
            mViewModel.setStatusBarColor(false, blue = value.roundToInt())
        }

        mBinding.widthGroupDgTest.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                mViewModel.windowWidth.value = when (checkedId) {
                    R.id.wrap_content_width_dg_test -> WRAP_CONTENT
                    else -> MATCH_PARENT
                }
            }
        }
        mBinding.heightGroupDgTest.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                mViewModel.windowHeight.value = when (checkedId) {
                    R.id.wrap_content_height_dg_test -> WRAP_CONTENT
                    else -> MATCH_PARENT
                }
            }
        }

        // 按钮
        mBinding.btnConfirmDgTest.setOnClickListener { showDialog() }

        mViewModel.consumeWindowInset.observe(this) {
            mBinding.consumeWindowInsets.switchItemS.isChecked = it
        }
        mViewModel.interceptDecorViewInset.observe(this) {
            mBinding.decorIntercept.switchItemS.isChecked = it
        }
        mViewModel.enforceStatusContract.observe(this) {
            mBinding.enforceStatus.switchItemS.isChecked = it
        }
        mViewModel.enforceNavigationContract.observe(this) {
            mBinding.enforceNavigation.switchItemS.isChecked = it
        }
        mViewModel.lightStatusBar.observe(this) { mBinding.lightStatus.switchItemS.isChecked = it }
        mViewModel.lightNavigationBar.observe(this) { mBinding.lightNav.switchItemS.isChecked = it }
        mViewModel.dimBackground.observe(this) { mBinding.dimBgd.switchItemS.isChecked = it }
        mViewModel.removeBackground.observe(this) { mBinding.removeBgd.switchItemS.isChecked = it }
        mViewModel.hideNavigation.observe(this) { mBinding.hideNav.switchItemS.isChecked = it }
        mViewModel.respondToNav.observe(this) { mBinding.respondNav.switchItemS.isChecked = it }
        mViewModel.windowIsFloating.observe(this) { mBinding.floating.switchItemS.isChecked = it }

        mViewModel.statusBarColor.observe(this) {
            mBinding.statusColor.colorItemCc.setBackgroundColor(it)
        }
        mViewModel.navigationBarColor.observe(this) {
            mBinding.navColor.colorItemCc.setBackgroundColor(it)
        }
        mViewModel.windowWidth.observe(this) {
            mBinding.widthGroupDgTest.check(
                when (it) {
                    WRAP_CONTENT -> R.id.wrap_content_width_dg_test
                    else -> R.id.match_parent_width_dg_test
                }
            )
        }
        mViewModel.windowHeight.observe(this) {
            mBinding.heightGroupDgTest.check(
                when (it) {
                    WRAP_CONTENT -> R.id.wrap_content_height_dg_test
                    else -> R.id.match_parent_height_dg_test
                }
            )
        }

    }

    private fun setupSlider() {
        mBinding.statusColor.slideRItemCc.setLabelFormatter { "${it.roundToInt()}" }
        mBinding.statusColor.slideGItemCc.setLabelFormatter { "${it.roundToInt()}" }
        mBinding.statusColor.slideBItemCc.setLabelFormatter { "${it.roundToInt()}" }
        mBinding.navColor.slideRItemCc.setLabelFormatter { "${it.roundToInt()}" }
        mBinding.navColor.slideGItemCc.setLabelFormatter { "${it.roundToInt()}" }
        mBinding.navColor.slideBItemCc.setLabelFormatter { "${it.roundToInt()}" }
    }

    private fun showDialog() {
        if (mViewModel.windowIsFloating.value == true)
            TestBottomDialog(this).show()
        else
            TestBottomDialog(this, R.style.MyDialog).show()
    }


    private fun setupSwitchItem(
        mItemBinding: ItemSwitchBinding,
        desireLiveData: MutableLiveData<Boolean>
    ) {
        mItemBinding.root.setOnClickListener {
            desireLiveData.value = desireLiveData.value == false
        }
    }
}