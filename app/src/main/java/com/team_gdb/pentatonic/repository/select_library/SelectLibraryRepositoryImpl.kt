package com.team_gdb.pentatonic.repository.select_library

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.team_gdb.pentatonic.GetUserLibraryQuery
import com.team_gdb.pentatonic.network.NetworkHelper
import io.reactivex.rxjava3.core.Observable

class SelectLibraryRepositoryImpl: SelectLibraryRepository {
    override fun getUserLibrary(userId: String): Observable<Response<GetUserLibraryQuery.Data>> =
        NetworkHelper.apolloClient.rxQuery(GetUserLibraryQuery(userId))}