package com.melekdmr.retrofittkotlin.service

import io.reactivex.Observable
import com.melekdmr.retrofittkotlin.model.CryptoModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {

    @GET(" atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    //observabla:gözlemlenebilir obje, veriler geldiğinde alan ve bu verileri yayın yapan
    suspend fun getData(): Response<List<CryptoModel>>




}