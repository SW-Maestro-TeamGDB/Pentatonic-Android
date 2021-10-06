package com.team_gdb.pentatonic.base

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.inputmethod.InputMethodManager
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

    /**
     * EditText 가 아닌 곳을 터치하면 키보드 내림
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val focusView: View? = currentFocus
        if (focusView != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()
            if (!rect.contains(x, y)) {
                val imm: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun setProgressVisible(visible: Boolean) {
        ViewUtil.disableEnableControls(!visible, binding.root as ViewGroup)
        progressView?.let { it.visibility = if (visible) View.VISIBLE else View.INVISIBLE }
    }



}