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
import com.team_gdb.pentatonic.util.makeStatusBarTransparent


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

        makeStatusBarTransparent()
        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    fun setProgressVisible(visible: Boolean) {
        ViewUtil.disableEnableControls(!visible, binding.root as ViewGroup)
        progressView?.let { it.visibility = if (visible) View.VISIBLE else View.INVISIBLE }
    }

}