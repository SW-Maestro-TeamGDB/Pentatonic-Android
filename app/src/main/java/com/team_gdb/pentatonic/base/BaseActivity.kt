package com.newidea.mcpestore.libs.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.team_gdb.pentatonic.util.SystemUtil
import com.team_gdb.pentatonic.util.ViewUtil


abstract class BaseActivity<T : ViewDataBinding, R : BaseViewModel> : AppCompatActivity() {
    var transparentPoint = 0

    lateinit var binding: T

    var progressView: View? = null

    abstract val layoutResourceId: Int

    abstract val viewModel: R

    /**
     * 레이아웃을 띄운 직후 호출.
     * 뷰나 액티비티의 속성 등을 초기화.
     * ex) 리사이클러뷰, 툴바, 드로어뷰.
     */
    abstract fun initStartView()

    /**
     * 두번째로 호출.
     * 데이터 바인딩 및 rxjava 설정.
     * ex) rxjava observe, databinding observe..
     */
    abstract fun initDataBinding()

    /**
     * 바인딩 이후에 할 일을 여기에 구현.
     * 그 외에 설정할 것이 있으면 이곳에서 설정.
     * 클릭 리스너도 이곳에서 설정.
     */
    abstract fun initAfterBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResourceId)

        initStartView()
        initDataBinding()
        initAfterBinding()

        initStatusBar()
    }

    fun setProgressVisible(visible: Boolean) {
        ViewUtil.disableEnableControls(!visible, binding.root as ViewGroup)
        progressView?.let { it.visibility = if (visible) View.VISIBLE else View.INVISIBLE }
    }

    private fun initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.statusBarColor = Color.TRANSPARENT
            val decorView = window.decorView
            val wic = decorView.windowInsetsController
            wic!!.setSystemBarsAppearance(
                APPEARANCE_LIGHT_STATUS_BARS,
                APPEARANCE_LIGHT_STATUS_BARS
            )
        } else if (Build.VERSION.SDK_INT >= 23) {
            window.statusBarColor = Color.TRANSPARENT
            val decorView = window.decorView
            @Suppress("DEPRECATION")
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    fun setStatusBar(isTransparent: Boolean) {
        // 요청 여부에 따라 포인트를 조절한다. (각 fragment pause/resume 딜레이를 방지하기 위함)
        transparentPoint += if (isTransparent) 1 else -1

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (transparentPoint > 0) {
                window.setDecorFitsSystemWindows(false)
                window.findViewById<View>(android.R.id.content)
                    .updatePadding(bottom = SystemUtil.getBottomNavigationBarHeight(this))
            } else {
                window.setDecorFitsSystemWindows(true)
                window.findViewById<View>(android.R.id.content).updatePadding(bottom = 0)
            }
            return
        }

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= 23) {
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            if (transparentPoint > 0) {
                decorView.systemUiVisibility =
                    decorView.systemUiVisibility or (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            }
            return
        }
    }
}