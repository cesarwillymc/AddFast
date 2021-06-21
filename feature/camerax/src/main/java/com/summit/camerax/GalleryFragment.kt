/*
/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.summit.android.addfast.ui.camera

import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.summit.android.addfast.R
import com.summit.android.addfast.utils.lifeData.Status
import kotlinx.android.synthetic.main.fragment_gallery.*
import java.io.File
import java.util.*


val EXTENSION_WHITELIST = arrayOf("JPG","PNG","JPEG")

/** Fragment used to present the user with a gallery of photos taken */
class GalleryFragment internal constructor() : Fragment(), GalleryAdapter.clickListener,TipeSearchGalleryAdapter.TipeSearchGalleryListener {


    lateinit var adaptadorView:TipeSearchGalleryAdapter
    var listaimg= listOf<File>()

    lateinit var adaptador:GalleryAdapter
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_gallery, container, false)
    lateinit var viewodel : CameraViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewodel= requireActivity().run {
            ViewModelProvider(this).get(CameraViewModel::class.java)
        }
        listaimg = getImagesPath(requireActivity())!!



        // Populate the ViewPager and implement a cache of two media items
        enviarImagen.setOnClickListener {
            viewodel.getImageFile(requireContext(),file!!.path).observe(viewLifecycleOwner, androidx.lifecycle.Observer{
                when(it.status){
                     Status.SUCCESS->{
                        viewodel.imageSelect.postValue(it.data!!)
                         Log.e("getImageFile",it.data)
                        Handler().postDelayed({
                            findNavController().navigateUp()
                        },400L)
                    }
                    Status.LOADING->{

                    }
                    Status.ERROR->{
                        Log.e("error",it.exception!!.message!!)

                    }
                }
            })
        }



        // Handle back button press
        view.findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            findNavController().navigateUp()
        }
        //Checking media files list
        adaptador= GalleryAdapter(this)
        reciclerImg.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = adaptador
        }
        adaptador.updateData(listaimg)


        adaptadorView = TipeSearchGalleryAdapter(this)
        reclyclerOptions.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
            adapter = adaptadorView
        }
        adaptadorView.updateData(listPedido)

    }
    private fun listImages(): List<File>? {
        val imagesDir= File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString())
        return imagesDir.listFiles { file ->
            EXTENSION_WHITELIST.contains(file.extension.toUpperCase(Locale.ROOT))
        }?.sortedDescending()?.toMutableList() ?: mutableListOf()


    }

    var file:File?=null
    override fun click(path: File?, position: Int?) {
        if (path==null){
            file=null
            enviarImagen.visibility=View.GONE
            adaptador.selectData(null)
        }else{
            //File(requireActivity().getPath(Uri.fromFile(path)))
            file= path
            enviarImagen.visibility=View.VISIBLE
            adaptador.selectData(path.path)
        }

    }
    fun getImagesPath(activity: Activity): List<File>? {
        val uri: Uri
        val listOfAllImages = mutableListOf<File>()
        val cursor: Cursor?
        var column_index_data: Int
        var PathOfImage: String? = null
        val orderBy = MediaStore.Images.Media.DATE_MODIFIED;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaColumns.DATA,MediaStore.Images.Media._ID,MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        cursor = activity.contentResolver.query(uri, projection, null,
            null, "$orderBy DESC")


        while (cursor!!.moveToNext()) {
            column_index_data = cursor!!.getColumnIndexOrThrow(MediaColumns.DATA)
            PathOfImage = cursor.getString(column_index_data)
            listOfAllImages.add(File(PathOfImage))
        }
        return listOfAllImages
    }
    override fun onclickGallery(dato: String, position: Int) {
        Log.e("entro","onclickGallery $dato")
        if (dato==""){
            listaimg= getImagesPath(requireActivity())!!
            adaptador.updateData(listaimg)
            adaptadorView.setearPosition(position)
        }else{
            viewodel.listImages(dato).observe(this, androidx.lifecycle.Observer {
                if(it.isNotEmpty()){
                    listaimg = it
                    adaptador.updateData(listaimg)
                    adaptadorView.setearPosition(position)
                }
            })
        }

    }
}

 */