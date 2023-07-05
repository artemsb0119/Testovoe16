package com.example.testovoe16

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FavoriteAdapter(private val context: Context, var crypts: List<Crypta>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(crypta: Crypta)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crypto_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crypta = crypts[position]
        holder.textViewCryptoNumber.text = crypta.title
        holder.textViewCrypto.text = crypta.crypto
        holder.textViewTime.text = "in 24 hours"
        holder.textViewDollar.text = crypta.dollar
        holder.textViewCost.text = crypta.cost.toString()
        holder.textViewHour.text = crypta.hour
        if (crypta.hour.contains("-")) {
            holder.textViewHour.setTextColor(Color.RED)
        } else {
            holder.textViewHour.setTextColor(Color.GREEN)
        }
        if (crypta.day.contains("-")) {
            holder.textViewDay.setTextColor(Color.RED)
        } else {
            holder.textViewDay.setTextColor(Color.GREEN)
        }
        if (crypta.week.contains("-")) {
            holder.textViewWeek.setTextColor(Color.RED)
        } else {
            holder.textViewWeek.setTextColor(Color.GREEN)
        }
        holder.textViewDay.text = crypta.day
        holder.textViewWeek.text = crypta.week
        holder.textView1Hour.text = "1h"
        holder.textView1Day.text = "1d"
        holder.textView1Week.text = "1w"
        holder.textViewFirst.text = crypta.mc+" MC"
        holder.textViewSecond.text = crypta.vol+" Vol"
        holder.textViewThird.text = crypta.cs+" CS"
        holder.imageViewStar.setImageResource(R.drawable.add)
        Glide.with(holder.itemView.context).load(crypta.crypto_image).into(holder.imageCrypto)
        Glide.with(holder.itemView.context).load(crypta.graf).into(holder.imageViewGraf)

        holder.imageViewStar.setOnClickListener {
            if (holder.imageViewStar.drawable.current.constantState?.equals(holder.itemView.context.getDrawable(R.drawable.add)?.constantState) == true) {
                val sharedPreferences = holder.itemView.context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("add_${crypta.id}", false)
                editor.apply()
                itemClickListener?.onItemClick(crypta)
            }
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("crypta", crypta)
            context.startActivity(intent)
            (context as Activity).finish()
        }
    }

    override fun getItemCount(): Int {
        return crypts.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewCryptoNumber: TextView = itemView.findViewById(R.id.textViewCryptoNumber)
        val imageCrypto: ImageView = itemView.findViewById(R.id.imageCrypto)
        val textViewCrypto: TextView = itemView.findViewById(R.id.textViewCrypto)
        val imageViewGraf: ImageView = itemView.findViewById(R.id.imageViewGraf)
        val textViewTime: TextView = itemView.findViewById(R.id.textViewTime)
        val imageViewStar: ImageView = itemView.findViewById(R.id.imageViewStar)
        val textViewDollar: TextView = itemView.findViewById(R.id.textViewDollar)
        val textViewCost: TextView = itemView.findViewById(R.id.textViewCost)
        val textViewHour: TextView = itemView.findViewById(R.id.textViewHour)
        val textViewDay: TextView = itemView.findViewById(R.id.textViewDay)
        val textViewWeek: TextView = itemView.findViewById(R.id.textViewWeek)
        val textView1Hour: TextView = itemView.findViewById(R.id.textView1Hour)
        val textView1Day: TextView = itemView.findViewById(R.id.textView1Day)
        val textView1Week: TextView = itemView.findViewById(R.id.textView1Week)
        val textViewFirst: TextView = itemView.findViewById(R.id.textViewFirst)
        val textViewSecond: TextView = itemView.findViewById(R.id.textViewSecond)
        val textViewThird: TextView = itemView.findViewById(R.id.textViewThird)
    }

    fun removeItem(position: Int) {
        crypts.toMutableList().apply {
            removeAt(position)
            crypts = this
        }
        notifyItemRemoved(position)
    }
}