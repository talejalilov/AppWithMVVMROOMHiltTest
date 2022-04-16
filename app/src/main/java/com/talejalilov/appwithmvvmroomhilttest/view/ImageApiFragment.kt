package com.talejalilov.appwithmvvmroomhilttest.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.talejalilov.appwithmvvmroomhilttest.R
import com.talejalilov.appwithmvvmroomhilttest.Util.Status
import com.talejalilov.appwithmvvmroomhilttest.adapter.ImageRecyclerAdapter
import com.talejalilov.appwithmvvmroomhilttest.databinding.FragmentImageApiBinding
import com.talejalilov.appwithmvvmroomhilttest.viewmodel.ArtViewModel
import javax.inject.Inject

class ImageApiFragment @Inject constructor(

    private val imageRecyclerAdapter: ImageRecyclerAdapter
) :Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel: ArtViewModel
    private var fragmentBinding: FragmentImageApiBinding?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

        subscribeToObserver()
        binding.imageRecyclerView.adapter = imageRecyclerAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            //tiklanan oge-nin linkini gondermis olduq
            viewModel.setSelectedImage(it)
        }
    }

    fun subscribeToObserver(){
        viewModel.imagelist.observe(viewLifecycleOwner, Observer {
            when(it.status){

                Status.SUCCESS ->{
                    val urls = it.data?.hits?.map { imageResult ->

                        imageResult.previewURL
                    }
                    imageRecyclerAdapter.images = urls?: listOf()
                    fragmentBinding?.progressBar?.visibility  = View.GONE
                }

                Status.ERROR ->{
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG).show()
                    fragmentBinding?.progressBar?.visibility  = View.GONE

                }

                Status.LOADING ->{
                    fragmentBinding?.progressBar?.visibility  = View.VISIBLE
                }

            }
        })
    }
}