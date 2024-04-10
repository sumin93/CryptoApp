package com.example.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: ""
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        val tvPrice = findViewById<TextView>(R.id.tvPrice)
        val tvMinPrice = findViewById<TextView>(R.id.tvMinPrice)
        val tvMaxPrice = findViewById<TextView>(R.id.tvMaxPrice)
        val tvLastMarket = findViewById<TextView>(R.id.tvLastMarket)
        val tvLastUpdate = findViewById<TextView>(R.id.tvLastUpdate)
        val tvFromSymbol = findViewById<TextView>(R.id.tvFromSymbol)
        val tvToSymbol = findViewById<TextView>(R.id.tvToSymbol)
        val ivLogoCoin = findViewById<ImageView>(R.id.ivLogoCoin)

        viewModel.getDetailInfo(fromSymbol).observe(this, Observer {
            tvPrice.text = it.price
            tvMinPrice.text = it.lowDay
            tvMaxPrice.text = it.highDay
            tvLastMarket.text = it.lastMarket
            tvLastUpdate.text = it.getFormattedTime()
            tvFromSymbol.text = it.fromSymbol
            tvToSymbol.text = it.toSymbol
            Picasso.get().load(it.getFullImageUrl()).into(ivLogoCoin)
        })
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}
