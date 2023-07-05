package com.example.testovoe16

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {

    private lateinit var left: ImageView
    private lateinit var cap: TextView
    private lateinit var star: ImageView
    private lateinit var textViewCost: TextView
    private lateinit var textViewChange: TextView
    private lateinit var imageGraf: ImageView
    private lateinit var minPriceRes: TextView
    private lateinit var maxPriceRes: TextView
    private lateinit var oneHourRes: TextView
    private lateinit var oneDayRes: TextView
    private lateinit var sevenDaysRes: TextView

    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        left = findViewById(R.id.left)
        cap = findViewById(R.id.cap)
        star = findViewById(R.id.star)
        textViewCost = findViewById(R.id.textViewCost)
        textViewChange = findViewById(R.id.textViewChange)
        imageGraf = findViewById(R.id.imageGraf)
        minPriceRes = findViewById(R.id.minPriceRes)
        maxPriceRes = findViewById(R.id.maxPriceRes)
        oneHourRes = findViewById(R.id.oneHourRes)
        oneDayRes = findViewById(R.id.oneDayRes)
        sevenDaysRes = findViewById(R.id.sevenDaysRes)

        val crypta = intent.getSerializableExtra("crypta") as? Crypta

        if (crypta != null) {
            cap.text = crypta.title
            if (crypta.add) {
                star.setImageResource(R.drawable.add)
            } else {
                star.setImageResource(R.drawable.noadd)
            }
            textViewCost.text = crypta.cost.toString() + " USD"
            textViewChange.text = crypta.hour
            if (crypta.hour.contains("-")) {
                textViewChange.setTextColor(Color.RED)
            } else {
                textViewChange.setTextColor(Color.GREEN)
            }
            Glide.with(this)
                .load(crypta.graf)
                .into(imageGraf)

            val initialCost = crypta.cost
            val newCost = crypta.cost * 0.95f
            val decimalFormat = DecimalFormat("#.${"0".repeat(initialCost.toString().split(".")[1].length)}")
            val formattedNewCost = decimalFormat.format(newCost)
            val formattedNewCostWithPeriod = formattedNewCost.replace(',', '.')
            crypta.cost = formattedNewCostWithPeriod.toFloat()
            minPriceRes.text = crypta.cost.toString()

            val newCost_1 = initialCost*1.05f
            val formattedNewCost_1 = decimalFormat.format(newCost_1)
            val formattedNewCostWithPeriod_1 = formattedNewCost_1.replace(',', '.')
            crypta.cost = formattedNewCostWithPeriod_1.toFloat()
            maxPriceRes.text = crypta.cost.toString()

            left.setOnClickListener{
                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            }

            oneHourRes.text = crypta.hour
            oneDayRes.text = crypta.day
            sevenDaysRes.text = crypta.week
            if (crypta.hour.contains("-")) {
                oneHourRes.setTextColor(Color.RED)
            } else {
                oneHourRes.setTextColor(Color.GREEN)
            }
            if (crypta.day.contains("-")) {
                oneDayRes.setTextColor(Color.RED)
            } else {
                oneDayRes.setTextColor(Color.GREEN)
            }
            if (crypta.week.contains("-")) {
                sevenDaysRes.setTextColor(Color.RED)
            } else {
                sevenDaysRes.setTextColor(Color.GREEN)
            }
            star.setOnClickListener {
                if (star.drawable.current.constantState?.equals(getDrawable(R.drawable.noadd)?.constantState) == true) {
                    star.setImageResource(R.drawable.add)
                    val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("add_${crypta.id}", true)
                    editor.apply()
                    sharedViewModel.addCrypto(crypta)
                } else {
                    star.setImageResource(R.drawable.noadd)
                    val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("add_${crypta.id}", false)
                    editor.apply()
                    sharedViewModel.removeCrypto(crypta)
                }
            }
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
        return super.onKeyDown(keyCode, event)
    }
}