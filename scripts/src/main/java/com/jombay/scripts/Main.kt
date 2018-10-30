package com.jombay.scripts

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val appCenter = AppCenter("Smartquest")
            println(appCenter.getAppNames())
        }
    }
}
