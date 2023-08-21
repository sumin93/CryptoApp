package com.example.cryptoapp.domain.usecases

import androidx.lifecycle.LiveData
import com.example.cryptoapp.domain.CoinInfo
import com.example.cryptoapp.domain.CoinRepository

class GetCoinInfoUseCase(private val repository: CoinRepository) {
    operator fun invoke(fromSymbol: String): LiveData<CoinInfo> {
        return repository.getCoinInfo(fromSymbol)
    }
}