package com.example.weathermap

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.weathermap.Model.OpenWeatherMap
import com.example.weathermap.common.common
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL


class MainActivity : AppCompatActivity() {

    internal var openWeatherMap = OpenWeatherMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherButton.setOnClickListener{
            weatherBtnHandler()
        }
        cityNameText.setOnClickListener{
            cityNameText.text.clear();
        }
    }

    private inner class GetWeather: AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            var response : String ?
            try {
                response = URL( "https://api.openweathermap.org/data/2.5/weather?q=${common.city}&units=metric&appid=${common.api}").readText(Charsets.UTF_8)
            }
            catch (e : Exception ) {
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(result == null){
                return
            }
            val gson = Gson()
            val mType = object : TypeToken<OpenWeatherMap>() {}.type

            openWeatherMap = gson.fromJson<OpenWeatherMap>(result, mType)

            city_name.text = "${openWeatherMap.name}, ${openWeatherMap.sys!!.country}"
            description.text = "${openWeatherMap.weather!![0].description}"
            pressure.text = "Ciśnienie ${openWeatherMap.main!!.pressure} hPa"
            timeTxt.text = "${common.dateNow}"
            sunUp.text = "Wschód słońca: ${common.unixTimeStampToDateTime(openWeatherMap.sys!!.sunrise)}"
            sunDown.text = "Zachód słońca: ${common.unixTimeStampToDateTime(openWeatherMap.sys!!.sunset)}"
            temperature.text = "${openWeatherMap.main!!.temp} °C"
            Picasso.with(this@MainActivity)
                .load(common.getIcon(openWeatherMap.weather!![0].icon!!))
                .into(weatherIcon)

        }
    }

    fun weatherBtnHandler(){
        common.city = cityNameText.getText().toString().toLowerCase()
        showWeatherReport()
    }

    fun showWeatherReport(){
        GetWeather().execute(common.city, common.api)
    }


}
