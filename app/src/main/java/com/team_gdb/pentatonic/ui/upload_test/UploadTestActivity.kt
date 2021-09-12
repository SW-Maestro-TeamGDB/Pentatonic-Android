package com.team_gdb.pentatonic.ui.upload_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.apollographql.apollo.api.FileUpload
import com.apollographql.apollo.rx3.rxMutate
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.UploadCoverFileMutation
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.type.UploadCoverFileInput
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class UploadTestActivity : AppCompatActivity() {
    private val filePath: String by lazy {
        "${externalCacheDir?.absolutePath}/mr.mp3"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_test)

        val button: Button = findViewById(R.id.uploadButton)
        button.setOnClickListener {
            uploadFile()
        }
    }

    fun uploadFile() {
        Timber.d("여기부터다! 버튼 눌렸음")
        apolloClient.rxMutate(
            UploadCoverFileMutation(
                UploadCoverFileInput(file = FileUpload("audio/mpeg3", filePath))
            )
        )
            .applySchedulers()
            .subscribeBy(
                onError = {
                    Timber.i(it)
                },
                onSuccess = {
                    Timber.d("=======업로드 완료======")
                    Timber.d(it.toString())
                    Timber.d(it.data.toString())
                    Timber.d(it.data?.uploadCoverFile.toString())
                    Timber.d(it.hasErrors().toString())
                    Timber.d("=======에러 ㅠㅠ======")
                    it.errors?.forEach {
                        Timber.i(it.message)
                    }
                },
            )
    }
}