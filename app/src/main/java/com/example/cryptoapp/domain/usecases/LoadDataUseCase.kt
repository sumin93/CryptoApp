package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.CoinRepository

class LoadDataUseCase(private val repository: CoinRepository) {
    operator fun invoke() {
        repository.loadData()
    }
}