/*
package com.summit.android.addfast.ui.camera

import android.content.Intent
import android.media.MediaScannerConnection
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.summit.android.addfast.R
import com.summit.android.addfast.utils.lifeData.Status
import kotlinx.android.synthetic.main.fragment_select_image.*
import java.io.File


class SelectImage internal constructor() : Fragment() {

    private val args: SelectImageArgs by navArgs()
    lateinit var viewodel : CameraViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewodel= requireActivity().run {
            ViewModelProvider(this).get(CameraViewModel::class.java)
        }

        viewodel.cargoImagen.postValue(null)
        Glide.with(requireContext()).load(File(args.image)).into(photo_view_pager)

        view.findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            findNavController().navigateUp()
        }

        // Handle share button press
        view.findViewById<ImageButton>(R.id.share_button).setOnClickListener {

            File(args.image).let { mediaFile ->

                // Create a sharing intent
                val intent = Intent().apply {
                    // Infer media type from file extension
                    val mediaType = MimeTypeMap.getSingleton()
                            .getMimeTypeFromExtension(mediaFile.extension)
                    // Get URI from our FileProvider implementation
                    val uri = FileProvider.getUriForFile(
                            view.context, "com.summit.porpunoapp" + ".provider", mediaFile)
                    // Set the appropriate intent extra, type, action and flags
                    putExtra(Intent.EXTRA_STREAM, uri)
                    type = mediaType
                    action = Intent.ACTION_SEND
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }

                // Launch the intent letting the user choose which app to share with
                startActivity(Intent.createChooser(intent, getString(R.string.share_hint)))
            }
        }

        // Handle delete button press
        view.findViewById<ImageButton>(R.id.delete_button).setOnClickListener {

            File(args.image).let { mediaFile ->
                viewodel.imageSelect.postValue("")
                AlertDialog.Builder(view.context, android.R.style.Theme_Material_Dialog)
                        .setTitle(getString(R.string.delete_title))
                        .setMessage(getString(R.string.delete_dialog))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes) { _, _ ->

                            // Delete current photo
                            mediaFile.delete()

                            // Send relevant broadcast to notify other apps of deletion
                            MediaScannerConnection.scanFile(
                                    view.context, arrayOf(mediaFile.absolutePath), null, null)

                            // Notify our view pager
                            findNavController().navigateUp()

                        }

                        .setNegativeButton(android.R.string.no, null)
                        .create().showImmersive()
            }
        }
        send_image.setOnClickListener {
            viewodel.getImageFile(requireContext(),args.image).observe(viewLifecycleOwner, Observer {
                when(it.status){
                    Status.SUCCESS ->{

                        viewodel.imageSelect.postValue(it.data)
                        if (args.type=="register"){
                            findNavController().popBackStack(R.id.registerInfoFragment,false)
                        }



                    }
                    Status.LOADING->{

                    }
                    Status.ERROR->{
                        Log.e("error",it.exception!!.message!!)

                    }
                }
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_image, container, false)
    }


}
 */