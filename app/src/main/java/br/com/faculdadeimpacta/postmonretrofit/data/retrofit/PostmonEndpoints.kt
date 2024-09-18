package br.com.faculdadeimpacta.postmonretrofit.data.retrofit

import br.com.faculdadeimpacta.postmonretrofit.data.model.CEP
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PostmonEndpoints {

    @GET("/cep/{cep}")
    fun getInformacoesCEP(@Path("cep") cep: String): Call<CEP>

}