package com.melekdmr.retrofittkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.melekdmr.retrofittkotlin.adapter.RecyclerViewAdapter
import com.melekdmr.retrofittkotlin.databinding.ActivityMainBinding
import com.melekdmr.retrofittkotlin.model.CryptoModel
import com.melekdmr.retrofittkotlin.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity(),RecyclerViewAdapter.Listener {

    private lateinit var binding:ActivityMainBinding
    private val BASE_URL="https://raw.githubusercontent.com/"
    private var cryptoModels:ArrayList<CryptoModel> ?= null
    private var recyclerViewAdapter:RecyclerViewAdapter?=null


    private var job:Job? = null
    val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error:${throwable.localizedMessage}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json


        //RecyclerView
        val layoutManager:RecyclerView.LayoutManager=LinearLayoutManager(this)
        binding.recyclerView.layoutManager=layoutManager

         loadData()
    }
    private fun loadData(){

        val retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            //addConverterFactory dönen yanıt tipini neye çevireceğini söyleyen bir yapı.
            // json’ı java objelerine çevireceğiz
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)
        job= CoroutineScope(Dispatchers.IO).launch {
            val response=retrofit.getData()

            withContext(Dispatchers.Main+exceptionHandler){
                if(response.isSuccessful){
                    response.body()?.let {
                        cryptoModels= ArrayList(it)
                        cryptoModels?.let {
                            recyclerViewAdapter=RecyclerViewAdapter(it,this@MainActivity)
                            binding.recyclerView.adapter=recyclerViewAdapter
                        }}
                }
            }
        }






    }
    private fun handleResponse(cryptoList: List<CryptoModel>) {
        cryptoModels = ArrayList(cryptoList)
        cryptoModels?.let {
            recyclerViewAdapter = RecyclerViewAdapter(it, this@MainActivity)
            binding.recyclerView.adapter = recyclerViewAdapter
        }
    }
    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked: ${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

}