package com.talejalilov.appwithmvvmroomhilttest.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.talejalilov.appwithmvvmroomhilttest.model.Art

@Dao
interface ArtDao {

    // suspend  fun - corotunlerle beraber senkron calisan ve gerekdiginde durdurulub tekrar cagrila bilen fonksuyondur
    // bununla calisma sebebi ise veritabani ile calisiyoruz mainThreadi yormamak lazim cikarmalar ve eklentiler cok oluyor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //ID -ler bir birine girerse(ayni olursa) ne olacagini yaziyoruz replace -et yani yerine yaz
    suspend fun insertArt(art: Art)

    @Delete
    suspend fun deleteArt(art : Art)

    //buna suspend yazmadik - cunki LiveData kendisi asekron calisiyor ve bu fonksyon bize verileri gozlemlemek icin lazim
    @Query("SELECT * FROM arts")
    fun observeArts() : LiveData<List<Art>>
}