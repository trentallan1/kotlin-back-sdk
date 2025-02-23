package dev.tmsoft.lib.query.paging

import io.ktor.http.Parameters
import io.ktor.server.request.ApplicationRequest

fun ApplicationRequest.pagingParameters(maxPageSize: Int = 100): PagingParameters {
    return queryParameters.paging(maxPageSize)
}

fun Parameters.paging(maxPageSize: Int = 100, default: Int = 30): PagingParameters {
    val pageSize = this["pageSize"]?.toIntOrNull() ?: default
    val page = this["page"]?.toIntOrNull() ?: 1
    val searchText= this["searchText"]?:""

    return PagingParameters(
        if (pageSize > maxPageSize) maxPageSize else pageSize,
        page,
        searchText
    )
}

data class PagingParameters(val pageSize: Int, val currentPage: Int, val searchText : String) {
    val offset = offset(pageSize, currentPage)
}

fun offset(pageSize: Int, currentPage: Int): Long {
    return (pageSize * (currentPage - 1)).toLong()
}
