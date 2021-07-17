package com.team_gdb.pentatonic.ui.login

import android.graphics.Color
import android.view.View
import android.view.WindowManager
import com.newidea.mcpestore.libs.base.BaseActivity
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.databinding.ActivityLoginBinding
import com.team_gdb.pentatonic.util.makeStatusBarTransparent
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_login
    override val viewModel: LoginViewModel by viewModel()

    override fun initStartView() {

    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
    }