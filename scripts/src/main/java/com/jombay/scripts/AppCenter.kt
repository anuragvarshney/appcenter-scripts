package com.jombay.scripts

import org.json.JSONArray
import org.json.JSONObject

class AppCenter(private val org: String) {

    fun shellCmd(command: String) = Runtime.getRuntime().exec(arrayOf("/bin/sh", "-c", command)).inputStream.bufferedReader().readText()

    val API_KEY = System.getenv("APPCENTER_API_KEY")

    fun getAppNames(): List<String> {
        val json = shellCmd("curl -X GET \"https://api.appcenter.ms/v0.1/orgs/$org/apps\" " +
                "-H \"accept: application/json\" " +
                "-H \"X-API-Token: $API_KEY\"")
        val names = mutableListOf<String>()
        try {
            JSONArray(json).forEach { obj ->
                if (obj is JSONObject) {
                    names.add(obj.getString("name"))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return names
    }
}