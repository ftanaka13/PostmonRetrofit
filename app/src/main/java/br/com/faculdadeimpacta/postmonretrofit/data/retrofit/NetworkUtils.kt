package br.com.faculdadeimpacta.postmonretrofit.data.retrofit

import retrofit2.Converter
import retrofit2.Retrofit

class NetworkUtils {
    companion object {
        fun getInstance(baseUrl: String, converterFactory: Converter.Factory): Retrofit {
            return Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .build()
        }
    }
}