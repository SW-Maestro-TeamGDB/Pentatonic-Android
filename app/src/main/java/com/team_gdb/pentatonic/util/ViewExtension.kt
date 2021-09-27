package com.team_gdb.pentatonic.util

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit


fun Activity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        statusBarColor = Color.TRANSPARENT
    }
}

fun View.setMarginTop(marginTop: Int) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop, 0, 0)
    this.layoutParams = menuLayoutParams
}

// Edit Text 에 쿼리 디바운싱 적용
fun EditText.setQueryDebounce(queryFunction: (String) -> Unit, clearButton: View): Disposable {
    val editTextChangeObservable = this.textChanges()

    // EditText 에 포커스가 갔을 때 쿼리 삭제 버튼 활성화
    this.setOnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            clearButton.visibility = View.VISIBLE
        }
//        else {
//            clearButton.visibility = View.GONE
//        }
    }
    val searchEditTextSubscription: Disposable =
        // 생성한 Observable 에 Operator 추가
        editTextChangeObservable
            // 마지막 글자 입력 0.8초 후에 onNext 이벤트로 데이터 발행
            .debounce(800, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            // 구독을 통해 이벤트 응답 처리
            .subscribeBy(
                onNext = {
                    Timber.d("onNext : $it")
                    queryFunction(it.toString())
                },
                onComplete = {
                    Timber.d("onComplete")
                },
                onError = {
                    Timber.i("onError : $it")
                }
            )
    return searchEditTextSubscription
}