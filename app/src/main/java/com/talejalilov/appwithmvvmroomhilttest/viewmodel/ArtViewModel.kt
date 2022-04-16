package com.talejalilov.appwithmvvmroomhilttest.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talejalilov.appwithmvvmroomhilttest.Util.Resource
import com.talejalilov.appwithmvvmroomhilttest.model.Art
import com.talejalilov.appwithmvvmroomhilttest.model.ImageResponse
import com.talejalilov.appwithmvvmroomhilttest.repo.ArtRepositoryInterface
import kotlinx.coroutines.launch
import java.lang.Exception

class ArtViewModel @ViewModelInject constructor(
    private val repository : ArtRepositoryInterface
) : ViewModel() {

    //ART fragment

    val artList = repository.getArt()


    //ImageAPI fragment
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imagelist : LiveData<Resource<ImageResponse>>
    get() = images

    //Kukllanincin secdiyi gorsel
    private val selectedImage  = MutableLiveData<String>()
    val selectedImageUrl :LiveData<String>
    get() = selectedImage

    //ArtDetails Fragment
    private var insertMsg  = MutableLiveData<Resource<Art>>()
    val insertArtMessage : LiveData<Resource<Art>>
    get() = insertMsg


    fun resetInsertArtMessage(){

        insertMsg = MutableLiveData<Resource<Art>>()

    }



    fun setSelectedImage(url :String){
        selectedImage. postValue(url)
    }

    fun deleteArt(art: Art) = viewModelScope.launch{
        repository.deleteArt(art)
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(name : String, artistName :String, year :String){
        if(name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
            insertMsg.postValue(Resource.error("Enter name, artist, year", null))
            return
        }
        val yearInt = try {

            year.toInt()
        }catch (e:Exception){
            insertMsg.postValue(Resource.error("Error", null ))
            return
        }

        val art = Art(name, artistName, yearInt, selectedImage.value?: "")

        insertArt(art)
        setSelectedImage("")
        insertMsg.postValue(Resource.success(art))
    }


    fun searchForImage(searchString : String){

        if(searchString.isEmpty()){
            return
        }

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }

    }

}