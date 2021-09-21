package com.team_gdb.pentatonic.di

import com.team_gdb.pentatonic.repository.artist.ArtistRepository
import com.team_gdb.pentatonic.repository.artist.ArtistRepositoryImpl
import com.team_gdb.pentatonic.repository.band_cover.BandCoverRepository
import com.team_gdb.pentatonic.repository.band_cover.BandCoverRepositoryImpl
import com.team_gdb.pentatonic.repository.login.LoginRepository
import com.team_gdb.pentatonic.repository.login.LoginRepositoryImpl
import com.team_gdb.pentatonic.repository.lounge.LoungeRepository
import com.team_gdb.pentatonic.repository.lounge.LoungeRepositoryImpl
import com.team_gdb.pentatonic.repository.my_page.MyPageRepository
import com.team_gdb.pentatonic.repository.my_page.MyPageRepositoryImpl
import com.team_gdb.pentatonic.repository.profile.ProfileRepository
import com.team_gdb.pentatonic.repository.profile.ProfileRepositoryImpl
import com.team_gdb.pentatonic.repository.record.RecordRepository
import com.team_gdb.pentatonic.repository.record.RecordRepositoryImpl
import com.team_gdb.pentatonic.repository.record_processing.RecordProcessingRepository
import com.team_gdb.pentatonic.repository.record_processing.RecordProcessingRepositoryImpl
import com.team_gdb.pentatonic.repository.register.RegisterRepository
import com.team_gdb.pentatonic.repository.register.RegisterRepositoryImpl
import com.team_gdb.pentatonic.repository.studio.StudioRepository
import com.team_gdb.pentatonic.repository.studio.StudioRepositoryImpl
import com.team_gdb.pentatonic.repository.user_verify.UserVerifyRepository
import com.team_gdb.pentatonic.repository.user_verify.UserVerifyRepositoryImpl
import com.team_gdb.pentatonic.repository.weekly_challenge.WeeklyChallengeRepository
import com.team_gdb.pentatonic.repository.weekly_challenge.WeeklyChallengeRepositoryImpl
import com.team_gdb.pentatonic.repository.whole_cover.WholeCoverRepository
import com.team_gdb.pentatonic.repository.whole_cover.WholeCoverRepositoryImpl
import com.team_gdb.pentatonic.ui.artist.ArtistViewModel
import com.team_gdb.pentatonic.ui.band_cover.BandCoverViewModel
import com.team_gdb.pentatonic.ui.cover_play.CoverPlayingViewModel
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverViewModel
import com.team_gdb.pentatonic.ui.login.LoginViewModel
import com.team_gdb.pentatonic.ui.lounge.LoungeViewModel
import com.team_gdb.pentatonic.ui.my_page.MyPageViewModel
import com.team_gdb.pentatonic.ui.profile.ProfileViewModel
import com.team_gdb.pentatonic.ui.record.RecordViewModel
import com.team_gdb.pentatonic.ui.record_processing.RecordProcessingViewModel
import com.team_gdb.pentatonic.ui.register.RegisterViewModel
import com.team_gdb.pentatonic.ui.select_song.SelectSongViewModel
import com.team_gdb.pentatonic.ui.solo_cover.SoloCoverActivity
import com.team_gdb.pentatonic.ui.solo_cover.SoloCoverViewModel
import com.team_gdb.pentatonic.ui.song_detail.SongDetailViewModel
import com.team_gdb.pentatonic.ui.studio.StudioViewModel
import com.team_gdb.pentatonic.ui.user_verify.UserVerifyViewModel
import com.team_gdb.pentatonic.ui.weekly_challenge.WeeklyChallengeViewModel
import com.team_gdb.pentatonic.ui.whole_cover.WholeCoverViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ArtistViewModel(get())
    }
    viewModel {
        LoungeViewModel(get())
    }
    viewModel {
        StudioViewModel(get())
    }
    viewModel {
        MyPageViewModel(get())
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
        SoloCoverViewModel()
    }

    viewModel {
        RecordViewModel()
    }

    viewModel {
        RecordProcessingViewModel(get())
    }

    viewModel {
        BandCoverViewModel(get())
    }

    viewModel {
        ProfileViewModel(get())
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

    viewModel {
        WholeCoverViewModel(get())
    }

    viewModel {
        CoverPlayingViewModel()
    }
}

val repositoryModule = module {
    single<LoungeRepository> {
        LoungeRepositoryImpl()
    }
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
    single<ArtistRepository> {
        ArtistRepositoryImpl()
    }
    single<StudioRepository> {
        StudioRepositoryImpl()
    }
    single<MyPageRepository> {
        MyPageRepositoryImpl()
    }
    single<WholeCoverRepository> {
        WholeCoverRepositoryImpl()
    }

    single<BandCoverRepository> {
        BandCoverRepositoryImpl()
    }

    single<ProfileRepository> {
        ProfileRepositoryImpl()
    }
}

val moduleList = listOf(viewModelModule, repositoryModule)