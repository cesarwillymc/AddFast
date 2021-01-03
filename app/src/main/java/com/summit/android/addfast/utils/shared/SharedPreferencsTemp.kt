package com.summit.android.addfast.utils.system

import android.content.Context
import android.content.SharedPreferences
import com.summit.android.addfast.app.MyApp
import com.summit.android.addfast.utils.Constants.APP_SETTINGS_TEMP


class SharedPreferencsTemp {
    companion object{
        private fun getSharedPreferencs(): SharedPreferences = MyApp.getContextApp().getSharedPreferences(APP_SETTINGS_TEMP,
            Context.MODE_PRIVATE)

        fun setTempStringValue(key:String,value:String){
            val editor = getSharedPreferencs().edit()
            editor.putString(key,value)
            editor.apply()
        }
        fun setTempBooleanValue(key:String,value:Boolean){
            val editor = getSharedPreferencs().edit()
            editor.putBoolean(key,value)
            editor.apply()
        }
        fun setTempIntValue(key:String,value:Int){
            val editor = getSharedPreferencs().edit()
            editor.putInt(key,value)
            editor.apply()
        }
        fun getTempStringValue(key:String): String? =getSharedPreferencs().getString(key,"")
        fun getTempBooleanValue(key:String): Boolean? =getSharedPreferencs().getBoolean(key,false)
        fun getTempIntValue(key:String): Int? =getSharedPreferencs().getInt(key,0)
        fun clearAllTempShared(){
            getSharedPreferencs().edit().clear().apply()
        }
        fun clearItemTempShared(key:String){
            getSharedPreferencs().edit().remove(key).apply()
        }
    }


}