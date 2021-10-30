package com.team_gdb.pentatonic.repository.record_processing

import com.apollographql.apollo.api.FileUpload
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxMutate
import com.apollographql.apollo.api.Input
import com.team_gdb.pentatonic.*
import com.team_gdb.pentatonic.data.model.SessionSettingEntity
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.type.*
import io.reactivex.rxjava3.core.Single

class RecordProcessingRepositoryImpl : RecordProcessingRepository {

    // 전달해준 coverURL 과 MR 이 병합된 음원을 제공해주는 뮤테이션 (싱크 확인용)
    override fun getMergedResult(coverList: List<String>) =
        apolloClient.rxMutate(MergeCoverMutation(mergeAudiosInput = MergeAudiosInput(coverList)))

    override fun uploadCoverFile(filePath: String): Single<Response<UploadCoverFileMutation.Data>> =
        apolloClient.rxMutate(
            UploadCoverFileMutation(
                UploadCoverFileInput(file = FileUpload("audio/mpeg3", filePath))
            )
        )

    override fun registerFreeSong(
        coverUrl: String,
        songName: String,
        songArtist: String
    ): Single<Response<UploadFreeSongMutation.Data>> = apolloClient.rxMutate(
        UploadFreeSongMutation(
            UploadFreeSongInput(UploadFreeSongAllInput(songName, coverUrl, songArtist))
        )
    )

    override fun uploadCoverToLibrary(
        name: String,
        coverURI: String,
        songId: String,
        position: String,
        reverb: Double,
        volume: Double,
    ): Single<Response<UploadCoverMutation.Data>> = apolloClient.rxMutate(
        UploadCoverMutation(
            UploadCoverInput(
                cover = UploadCoverAllInput(
                    name, coverURI, songId, position = SESSION_TYPE.valueOf(position)
                ),
                filter = UploadCoverFilterInput(
                    reverb= reverb
                )
            )
        )
    )
}