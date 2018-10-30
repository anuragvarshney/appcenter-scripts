package com.jombay.scripts

import org.json.JSONArray
import org.json.JSONObject

class AppCenter(private val org: String) {

    fun shellCmd(command: String) = Runtime.getRuntime().exec(arrayOf("/bin/sh", "-c", command)).inputStream.bufferedReader().readText()

    val API_KEY = System.getenv("APPCENTER_API_KEY")

    // returns available apps under organisation
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

    // branch already needs to be configured
    fun triggerBuild(app: String, branch: String, commitHash: String): String {
        val cmd = "curl -X POST \"https://api.appcenter.ms/v0.1/apps/$org/$app/branches/$branch/builds\" " +
                "-H \"accept: application/json\" " +
                "-H \"X-API-Token: $API_KEY\" " +
                "-H \"Content-Type: application/json\" " +
                "-d \"{ \\\"sourceVersion\\\": \\\"$commitHash\\\", \\\"debug\\\": false}\""
        return shellCmd(cmd)
    }
}