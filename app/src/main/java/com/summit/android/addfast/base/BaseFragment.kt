package com.summit.android.addfast.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }
    fun View.show(){
        this.visibility=View.VISIBLE
    }
    fun View.hide(){
        this.visibility=View.GONE
    }
    fun hideKeyboard(){
        try{
            val view = requireActivity().currentFocus
            view!!.clearFocus()
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }catch (e :Exception){

        }
    }
    fun EditText.isEmptyLbl():Boolean{
        val texto= text.toString().trim()
        if (texto.isEmpty()){
            this.error="No puede estar vacio"
             this.requestFocus()
             return true
        }
        return false

    }
    fun EditText.showKeyboard(){
        try{
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }catch (e :Exception){

        }
    }
    fun navigateFragment(nav: NavDirections){
        findNavController().navigate(nav)
    }
    fun snakBar(mensaje: String){
        Snackbar.make(requireView(),mensaje, Snackbar.LENGTH_LONG).also { snackbar ->
            snackbar.setAction("Ok"){
                snackbar.dismiss()
            }.show()
        }
    }
    fun snakBar(mensaje: String,duracion:Int){
        Snackbar.make(requireView(),mensaje, duracion).also { snackbar ->
            snackbar.setAction("Ok"){
                snackbar.dismiss()
            }.show()
        }
    }
    fun snakBarDefinitivo(mensaje: String){
        Snackbar.make(requireView(),mensaje, Snackbar.LENGTH_INDEFINITE).also { snackbar ->
            snackbar.setAction("Ok"){
                snackbar.dismiss()
            }.show()
        }
    }
    fun toast(mensaje:String){
        Toast.makeText(requireContext(),mensaje, Toast.LENGTH_LONG).show()
    }
    fun navigateToActivity(intent: Intent) {
        intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
    }
    @LayoutRes
    protected abstract fun getLayout():Int
}