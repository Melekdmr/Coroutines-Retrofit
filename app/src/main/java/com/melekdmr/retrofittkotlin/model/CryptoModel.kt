package com.melekdmr.retrofittkotlin.model

import com.google.gson.annotations.SerializedName


// data sınıfı olduğu için veri çekmeli ve işlemeli
  data class CryptoModel(
    @SerializedName("currency")
    val currency :String ,
    @SerializedName("price")
    val price:String)
// değişkenlerin ismi aynıysa seralizedName yapmaya gerek yok