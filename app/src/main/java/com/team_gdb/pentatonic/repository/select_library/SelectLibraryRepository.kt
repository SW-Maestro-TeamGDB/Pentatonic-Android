package com.team_gdb.pentatonic.repository.select_library

import com.apollographql.apollo.api.Response
import com.team_gdb.pentatonic.GetUserLibraryQuery
import io.reactivex.rxjava3.core.Observable

interface SelectLibraryRepository {
    fun getUserLibrary(userId: String): Observable<Response<GetUserLibraryQuery.Data>>
}