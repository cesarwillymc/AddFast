package com.summit.android.addfast.ui.main.service.ubication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.graphics.Color
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.PolyUtil
import com.huawei.hms.location.FusedLocationProviderClient
import com.huawei.hms.location.LocationRequest
import com.huawei.hms.location.LocationServices
import com.huawei.hms.location.LocationSettingsRequest
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.repo.model.Anuncios
import com.summit.android.addfast.repo.model.Usuario
import com.summit.android.addfast.repo.model.departamento.UbiModel
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.ui.main.MainViewModelFactory
import com.summit.android.addfast.utils.bitmapDescriptorFromVector
import com.summit.android.addfast.utils.getLocationMode
import com.summit.android.addfast.utils.setOnSingleClickListener
import com.summit.android.addfast.utils.verifyPermission
import kotlinx.android.synthetic.main.fragment_ubication.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*

/**
 * A simple [Fragment] subclass.
 */


class Ubication : BaseFragment(), KodeinAware{

    lateinit var geo: Geocoder
  //  lateinit var googleMap: GoogleMap
   // lateinit var mapFragment: SupportMapFragment
    private var origen: Marker?=null

    private lateinit var viewModel: MainViewModel
    override val kodein by kodein()
    private val factory: MainViewModelFactory by instance()
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
            activeMapsIndle()
        }catch (e:Exception){
        }
    }


    private fun createMarker(fromLngLat: LatLng): Marker? {
        origen?.let {
            it.remove()
        }
        return googleMap?.let {
            it.addMarker(
                MarkerOptions().icon(requireContext().bitmapDescriptorFromVector(R.drawable.marker_daloo))
                    .title("Position Rider ")
                    .position(fromLngLat))
        }
    }
    var latLongactual:LatLng?=null
    private fun activeMapsIndle() {
        googleMap!!.setOnCameraIdleListener {
            try{
                latLongactual = googleMap!!.cameraPosition.target
            }catch (e:Exception){
                Log.e("error", e.message!!)
            }
        }
    }

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



    override fun getLayout(): Int =R.layout.fragment_ubication
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= requireActivity().run {
            ViewModelProvider(this,factory).get(MainViewModel::class.java)
        }

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
        feid_edtxt_search.setOnSingleClickListener {
            try {
                val center = googleMap!!.cameraPosition.target

                direcciones = geo.getFromLocation(center.latitude,center.longitude,1) as List<Address>
                val direccion2= (direcciones!![0].getAddressLine(0)).split(",")

                feid_edtxt_actual.setText(direccion2[0])
            }catch (e:Exception){
                toast("Intentalo nuevamente")
            }
        }



        //gpsEnable()
        feid_btn_update.setOnSingleClickListener  {

                val dato=feid_edtxt_actual.text.toString().trim()
                if (dato.isEmpty()){
                    feid_edtxt_actual.error="Agregue su dirección y mueva el marcador a su destino"
                    feid_edtxt_actual.requestFocus()
                }

                else{
                    viewModel.anuncioCreate.postValue(Anuncios(ubicacion = UbiModel(latLongactual!!.latitude,latLongactual!!.longitude)!!,phone = dato))
                    feid_btn_update.hide()
                    findNavController().navigateUp()
                }

        }
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
                val mapFragment = childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment?
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
