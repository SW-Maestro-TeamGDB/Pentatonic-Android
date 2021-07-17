package com.team_gdb.pentatonic.ui.home

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.newidea.mcpestore.libs.base.BaseActivity
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.databinding.ActivityHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_home
    override val viewModel: HomeViewModel by viewModel()
    override fun initStartView() {
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_lounge,
                R.id.navigation_studio,
                R.id.navigation_artist,
                R.id.navigation_my_page
            )
        )
        navView.setupWithNavController(navController)
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}