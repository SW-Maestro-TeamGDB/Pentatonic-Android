package com.team_gdb.pentatonic.di

import androidx.navigation.fragment.FragmentNavigatorExtras
import com.team_gdb.pentatonic.repository.login.LoginRepository
import com.team_gdb.pentatonic.repository.login.LoginRepositoryImpl
import com.team_gdb.pentatonic.repository.record.RecordRepository
import com.team_gdb.pentatonic.repository.record.RecordRepositoryImpl
import com.team_gdb.pentatonic.repository.record_processing.RecordProcessingRepository
import com.team_gdb.pentatonic.repository.record_processing.RecordProcessingRepositoryImpl
import com.team_gdb.pentatonic.repository.register.RegisterRepository
import com.team_gdb.pentatonic.repository.register.RegisterRepositoryImpl
import com.team_gdb.pentatonic.repository.user_verify.UserVerifyRepository
import com.team_gdb.pentatonic.repository.user_verify.UserVerifyRepositoryImpl
import com.team_gdb.pentatonic.repository.weekly_challenge.WeeklyChallengeRepository
import com.team_gdb.pentatonic.repository.weekly_challenge.WeeklyChallengeRepositoryImpl
import com.team_gdb.pentatonic.ui.artist.ArtistViewModel
import com.team_gdb.pentatonic.ui.band_cover.BandCoverViewModel
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverViewModel
import com.team_gdb.pentatonic.ui.login.LoginViewModel
import com.team_gdb.pentatonic.ui.lounge.LoungeViewModel
import com.team_gdb.pentatonic.ui.my_page.MyPageViewModel
import com.team_gdb.pentatonic.ui.profile.ProfileViewModel
import com.team_gdb.pentatonic.ui.record.RecordViewModel
import com.team_gdb.pentatonic.ui.record_processing.RecordProcessingViewModel
import com.team_gdb.pentatonic.ui.register.RegisterViewModel
import com.team_gdb.pentatonic.ui.rising_band.RisingBandViewModel
import com.team_gdb.pentatonic.ui.rising_solo.RisingSoloViewModel
import com.team_gdb.pentatonic.ui.select_song.SelectSongActivity
import com.team_gdb.pentatonic.ui.select_song.SelectSongViewModel
import com.team_gdb.pentatonic.ui.song_detail.SongDetailViewModel
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

    viewModel {
        RisingBandViewModel()
    }

    viewModel {
        RisingSoloViewModel()
    }

    viewModel {
        RecordViewModel()
    }

    viewModel {
        RecordProcessingViewModel(get())
    }

    viewModel {
        BandCoverViewModel()
    }

    viewModel {
        ProfileViewModel()
    }

    viewModel {
        CreateCoverViewModel()
    }

    viewModel {
        SelectSongViewModel()
    }

    viewModel {
        SongDetailViewModel()
    }
}

val repositoryModule = module {
    single<RegisterRepository> {
        RegisterRepositoryImpl()
    }
    single<UserVerifyRepository> {
        UserVerifyRepositoryImpl()
    }
    single<LoginRepository> {
        LoginRepositoryImpl()
    }
    single<WeeklyChallengeRepository> {
        WeeklyChallengeRepositoryImpl()
    }
    single<RecordRepository> {
        RecordRepositoryImpl()
    }
    single<RecordProcessingRepository> {
        RecordProcessingRepositoryImpl()
    }
}

val moduleList = listOf(viewModelModule, repositoryModule)