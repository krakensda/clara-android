package tech.fulbek_dev.clara_app.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import tech.fulbek_dev.clara_app.data.paging.AssetsPagingSource
import tech.fulbek_dev.clara_app.data.remote.APIService

class AssetsRepository(
        override val api: APIService
): BaseRepository(api) {

    fun getAssets(search: String?) =
        Pager(
                config = PagingConfig(
                        pageSize = 8,
                        maxSize = 40,
                        enablePlaceholders = false
                ),
                pagingSourceFactory = { AssetsPagingSource(api, search) }
        ).liveData

}