package com.example.elite_classroom.Retrofit


import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ServiceBuilder {


//    private const val  URL ="http://192.168.1.25:8000/api/v1/"
     const val  URL ="https://elite-classroom-server.herokuapp.com/api/"


    //Creating OTTHP Client
     val okHttp = OkHttpClient
        .Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)


    //Create Retrofit  Builder
     val builder = Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
    //Creating an instance of  Retrofit instance
     var retrofit= builder.build()



    fun  <T> buildService(serviceType: Class<T>) : T{
        return retrofit.create(serviceType)
    }
}




