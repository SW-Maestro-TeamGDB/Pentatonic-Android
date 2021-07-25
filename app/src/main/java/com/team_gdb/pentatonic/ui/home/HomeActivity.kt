package com.team_gdb.pentatonic.ui.home

import android.Manifest
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.newidea.mcpestore.libs.base.BaseActivity
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.databinding.ActivityHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_home
    override val viewModel: HomeViewModel by viewModel()

    var permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            /* no-op */
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            Toast.makeText(
                this@HomeActivity,
                "허용이 필요한 권한이 있습니다\n$deniedPermissions",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun initStartView() {
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        navView.setupWithNavController(navController)

        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("권한을 허용하지 않으면 펜타토닉 서비스를 사용하기 어렵습니다.\n\n[설정] > [권한] 에서 권한을 허용해주세요.")
            .setPermissions(Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .check()
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}