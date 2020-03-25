package com.example.weathermap.common

import java.lang.reflect.Array.get
import java.net.URL
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object common{
    var city : String = "gliwice"
    val api : String = "1bdf1a2776aef7bcfd9a2fc288e302de"


    fun unixTimeStampToDateTime(unixTimestamp: Double):String{
        val dateFormat = SimpleDateFormat("HH:mm")
        val date = Date()
        date.time = unixTimestamp.toLong()*1000
        return dateFormat.format(date)
    }

    fun getIcon(icon:String):String{
        return "http://openweathermap.org/img/${icon}.png"
    }

    val dateNow:String
        get(){
            val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
            val date = Date()
            return dateFormat.format(date)
        }

}