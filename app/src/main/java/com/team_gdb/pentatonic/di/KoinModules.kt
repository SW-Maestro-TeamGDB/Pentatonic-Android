package com.team_gdb.pentatonic.di

import androidx.navigation.fragment.FragmentNavigatorExtras
import com.team_gdb.pentatonic.repository.login.LoginRepository
import com.team_gdb.pentatonic.repository.register.RegisterRepository
import com.team_gdb.pentatonic.repository.user_verify.UserVerifyRepository
import com.team_gdb.pentatonic.repository.weekly_challenge.WeeklyChallengeRepository
import com.team_gdb.pentatonic.ui.artist.ArtistViewModel
import com.team_gdb.pentatonic.ui.login.LoginViewModel
import com.team_gdb.pentatonic.ui.lounge.LoungeViewModel
import com.team_gdb.pentatonic.ui.my_page.MyPageViewModel
import com.team_gdb.pentatonic.ui.register.RegisterViewModel
import com.team_gdb.pentatonic.ui.studio.StudioViewModel
import com.team_gdb.pentatonic.ui.user_verify.UserVerifyViewModel
import com.team_gdb.pentatonic.ui.weekly_challenge.WeeklyChallengeFragment
import com.team_gdb.pentatonic.ui.weekly_challenge.WeeklyChallengeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ArtistViewModel()
    }
    viewModel {
        LoungeViewModel()
    }
    viewModel {
        StudioViewModel()
    }
    viewModel {
        MyPageViewModel()
    }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        RegisterViewModel(get())
    }
    viewModel {
        UserVerifyViewModel(get())
    }
    viewModel {
        WeeklyChallengeViewModel(get())
    }
}

val repositoryModule = module {
    single{
        RegisterRepository()
    }
    single{
        UserVerifyRepository()
    }
    single{
        LoginRepository()
    }
    single{
        WeeklyChallengeRepository()
    }
}

val moduleList = listOf(viewModelModule, repositoryModule)