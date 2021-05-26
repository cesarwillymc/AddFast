package com.summit.android.addfast.ui.main.service.ubication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.huawei.hms.location.FusedLocationProviderClient
import com.huawei.hms.location.LocationRequest
import com.huawei.hms.location.LocationServices
import com.huawei.hms.location.LocationSettingsRequest
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.repo.model.Anuncios
import com.summit.android.addfast.repo.model.Usuario
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.utils.bitmapDescriptorFromVector
import com.summit.android.addfast.utils.getLocationMode
import com.summit.android.addfast.utils.verifyPermission
import kotlinx.android.synthetic.main.fragment_ubication_position.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * A simple [Fragment] subclass.
 */


class UbicationPosition : BaseFragment(){

    lateinit var geo: Geocoder
  //  lateinit var googleMap: GoogleMap
   // lateinit var mapFragment: SupportMapFragment
    private var origen: Marker?=null


    private var usuario: Usuario?=null

    private var googleMap: GoogleMap? = null

    //    lateinit var mapFragment: SupportMapFragment
    private var locationManager: LocationManager? = null

    var latitude:Double?=null
    var longitude:Double?=null
  //  var altitude:Double?=null

    private var direcciones:List<Address>?=null
      @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap=googleMap

        try{

            googleMap!!.isMyLocationEnabled=true
            googleMap!!.uiSettings.isMyLocationButtonEnabled=true
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
            createMarker(LatLng((args.modelo as Anuncios).ubicacion.latitude,(args.modelo as Anuncios).ubicacion.longitude))

        }catch (e:Exception){
        }
    }


    private fun createMarker(fromLngLat: LatLng): Marker? {
        Log.e("ubicacion","$fromLngLat")
        origen?.let {
            it.remove()
        }
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(fromLngLat, 15f))
        return googleMap?.let {
            it.addMarker(
                MarkerOptions().icon(requireContext().bitmapDescriptorFromVector(R.drawable.marker_daloo))
                    .title("Position Rider ")
                    .position(fromLngLat))
        }
    }
    var latLongactual:LatLng?=null


    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private fun gpsEnable() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder =
            LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(requireActivity())
        val task =
            settingsClient.checkLocationSettings(builder.build())
        task.addOnSuccessListener {  }
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(activity, 0)
                } catch (e1: SendIntentException) {
                    e1.printStackTrace()
                }
            }
        }
    }
    val viewModel: MainViewModel by viewModel()

    val args:UbicationPositionArgs by navArgs()
    override fun getLayout(): Int =R.layout.fragment_ubication_position
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        geo = Geocoder(context, Locale.getDefault())
        mapa_icon_gpsaa.isEnabled=false
        requireContext().verifyPermission()
        try{
            gpsEnable()
        }catch (e:Exception){}
        viewModel.getDataPermission.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it) {
                if (requireContext().getLocationMode() == 3) {
                    inicializarMapa()
                } else {
                    toast("Ponlo en alta precisión")
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            } else {
                requireContext().verifyPermission()
            }
        })



       /*  mapa_icon_gpsaa.setOnSingleClickListener  {
            val lastKnownLocation = mapboxMap!!.locationComponent.lastKnownLocation
            val log = lastKnownLocation!!.longitude
            val lat = lastKnownLocation!!.latitude
            moveCamera(LatLng(lat, log))
        }*/
    }
    private fun inicializarMapa(){

        gpsEnable()
        Handler().postDelayed({
            try{
                val mapFragment = childFragmentManager.findFragmentById(R.id.map_view_postion) as SupportMapFragment?
                mapFragment?.getMapAsync(callback)
            }catch (e:Exception){}
        },1000L)


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1->{
                if (resultCode == Activity.RESULT_OK) {
                    //getDeviceLocation()
                } else {
                    Toast.makeText(requireContext(), "Error de gps", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



}
