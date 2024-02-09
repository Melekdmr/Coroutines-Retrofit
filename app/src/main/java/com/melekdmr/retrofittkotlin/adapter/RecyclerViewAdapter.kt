package com.melekdmr.retrofittkotlin.adapter

import android.graphics.Color
import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.melekdmr.retrofittkotlin.R
import com.melekdmr.retrofittkotlin.databinding.RowLayoutBinding
import com.melekdmr.retrofittkotlin.model.CryptoModel

class RecyclerViewAdapter(private val cryptoList: ArrayList<CryptoModel>,private val listener:Listener): RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> (){

    private val colors:Array<String> = arrayOf("#4c001f","#f1f8a2", "#07004c","#464a4e","#daa520","#008080","#c0d6e4","#c0d6e4")

    interface Listener{
        fun onItemClick(cryptoModel: CryptoModel)
    }
    class RowHolder(private val binding:RowLayoutBinding): RecyclerView.ViewHolder(binding.root) {


         fun bind(cryptoModel: CryptoModel,colors:Array<String>,position: Int,listener: Listener){
             itemView.setOnClickListener {
                 listener.onItemClick(cryptoModel)
             }
             itemView.setBackgroundColor(Color.parseColor(colors[position %7]))
             binding.textName.text=cryptoModel.currency
             binding.textPrice.text=cryptoModel.price
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding =
            RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)


    }

    override fun getItemCount(): Int {
             return cryptoList.size
    }
      // hangi item ne verisi gönderecek?
    // daha kolay gösterebilmek için Rowholder içeriisnde bir fonksiyon tanımladık
    override fun onBindViewHolder(holder: RowHolder, position: Int) {
            holder.bind(cryptoList[position],colors,position,listener)

    }
}