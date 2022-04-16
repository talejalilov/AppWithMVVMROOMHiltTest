package com.talejalilov.appwithmvvmroomhilttest.repo

import androidx.lifecycle.LiveData
import com.talejalilov.appwithmvvmroomhilttest.Util.Resource
import com.talejalilov.appwithmvvmroomhilttest.model.Art
import com.talejalilov.appwithmvvmroomhilttest.model.ImageResponse

interface ArtRepositoryInterface {

    suspend fun insertArt(art : Art)

    suspend fun deleteArt(art : Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString: String) : Resource<ImageResponse>



}