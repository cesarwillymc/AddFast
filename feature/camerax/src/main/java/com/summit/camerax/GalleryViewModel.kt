package com.summit.camerax


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.summit.camerax.adapter.GalleryOptionsAdapter
import com.summit.commons.ui.extension.ConvertImage
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import kotlin.Comparator

class GalleryViewModel : ViewModel() {
    private val extensionListWhite = arrayOf("JPG", "PNG", "JPEG")

    var listOptionsMenu: List<GalleryOptionsAdapter.GalleryDocs> = listOf(
        GalleryOptionsAdapter.GalleryDocs("Todos", ""),
        GalleryOptionsAdapter.GalleryDocs(
            "Descargas",
            Environment.getExternalStorageDirectory().path + "/" + Environment.DIRECTORY_DOWNLOADS
        ),
        GalleryOptionsAdapter.GalleryDocs(
            "ScreenShot",
            Environment.getExternalStorageDirectory().path + "/DCIM/Screenshots"
        ),
        GalleryOptionsAdapter.GalleryDocs(
            "WhatsApp",
            Environment.getExternalStorageDirectory().path + "/WhatsApp/Media/WhatsApp Images"
        ),
        GalleryOptionsAdapter.GalleryDocs(
            "Facebook",
            Environment.getExternalStorageDirectory().path + "/DCIM/Facebook"
        ),
        GalleryOptionsAdapter.GalleryDocs("Fotos", Environment.getExternalStorageDirectory().path + "/DCIM/Camera")
    )


    private lateinit var _urlDirectionImage: String
    val urlDirectionImage: String get() = _urlDirectionImage

    private val _state = MutableLiveData<GalleryViewState>()
    val state:LiveData<GalleryViewState> get() = _state

    fun getImageFile(context: Context, path: String){
        viewModelScope.launch {
            _state.postValue (GalleryViewState.Loading)
            try {
                ConvertImage(
                    context
                ).compressImage(path).apply {
                    _urlDirectionImage = this
                    _state.postValue(GalleryViewState.Complete)
                }
            } catch (e: Exception) {
                _state.postValue(GalleryViewState.Error)
            }
        }
    }


    fun listImages(fileName: String) = liveData {

        val imagesDir = File(fileName)
        val dato = imagesDir.listFiles { file ->
            extensionListWhite.contains(file.extension.toUpperCase(Locale.ROOT))
        }?.toMutableList() ?: mutableListOf()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dato.sortWith(Comparator.comparing(File::lastModified).reversed())
        } else {
            val constantLastModifiedTimes: MutableMap<File, Long> = HashMap()
            for (f in dato) {
                constantLastModifiedTimes[f] = f.lastModified()
            }
            dato.sortWith { f1, f2 -> constantLastModifiedTimes[f2]!!.compareTo(constantLastModifiedTimes[f1]!!) }
        }
        emit(dato)

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("Recycle")
    fun getImagesPath(activity: Activity): List<File> {
        val listOfAllImages = mutableListOf<File>()
        val cursor: Cursor?
        var columnIndexData: Int
        var pathOfImage: String?
        val orderBy = MediaStore.Images.Media.DATE_MODIFIED;
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.MediaColumns.DATA, MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )
        cursor = activity.contentResolver.query(
            uri, projection, null,
            null, "$orderBy DESC"
        )


        while (cursor!!.moveToNext()) {
            columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            pathOfImage = cursor.getString(columnIndexData)
            pathOfImage?.let {
                listOfAllImages.add(File(it))
            }

        }
        return listOfAllImages
    }
}
