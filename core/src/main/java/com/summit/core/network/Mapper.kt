package com.summit.core.network

import org.json.JSONArray
import org.json.JSONObject

class Mapper {
    fun mapToJSON(map: Map<String, Any>): JSONObject? {
        val obj = JSONObject()
        for ((key, value) in map) {
            when (value) {
                is Map<*, *> -> {
                    val subMap = value as Map<String, Any>
                    obj.put(key, mapToJSON(subMap))
                }
                is List<*> -> {
                    obj.put(key, listToJSONArray(value as List<Any>))
                }
                else -> {
                    obj.put(key, value)
                }
            }
        }
        return obj
    }

    private fun listToJSONArray(list: List<Any>): JSONArray? {
        val arr = JSONArray()
        for (obj in list) {
            when (obj) {
                is Map<*, *> -> {
                    arr.put(mapToJSON(obj as Map<String, Any>))
                }
                is List<*> -> {
                    arr.put(listToJSONArray(obj as List<Any>))
                }
                else -> {
                    arr.put(obj)
                }
            }
        }
        return arr
    }
}