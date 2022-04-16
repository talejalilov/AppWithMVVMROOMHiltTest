package com.talejalilov.appwithmvvmroomhilttest.dependencyInjection

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.talejalilov.appwithmvvmroomhilttest.R
import com.talejalilov.appwithmvvmroomhilttest.Util.Util.BASE_URL
import com.talejalilov.appwithmvvmroomhilttest.api.RetrofitAPI
import com.talejalilov.appwithmvvmroomhilttest.repo.ArtRepository
import com.talejalilov.appwithmvvmroomhilttest.repo.ArtRepositoryInterface
import com.talejalilov.appwithmvvmroomhilttest.roomdb.ArtDao
import com.talejalilov.appwithmvvmroomhilttest.roomdb.ArtsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, ArtsDatabase::class.java, "ArtBookDB"
    ).build()

    @Singleton
    @Provides
    fun injectDao(database: ArtsDatabase) = database.artDao()


    @Singleton
    @Provides
    fun injectRetrofitAPI() :RetrofitAPI{

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
                //hangi clasi kullanicagiz deyir
            .create(RetrofitAPI::class.java)

    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao: ArtDao, api: RetrofitAPI) = ArtRepository(dao,api) as ArtRepositoryInterface


    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
        )

}