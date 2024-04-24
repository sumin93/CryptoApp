package com.example.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.domain.CoinInfo
import com.example.cryptoapp.domain.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImpl(
    application: Application
) : CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinInfoDao()
    private val apiService = ApiFactory.apiService
    private val mapper = CoinMapper()
    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return coinInfoDao.getPriceList().map {
            mapper.mapListDbModelToListEntity(it)
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return coinInfoDao.getPriceInfoAboutCoin(fromSymbol).map {
            mapper.mapDBModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while(true) {
            val topCoins = apiService.getTopCoinsInfo(limit = 50)
            val fromSymbols = mapper.mapNamesListToString(topCoins)
            val jsonContainer = apiService.getFullPriceList(fSyms =  fromSymbols)
            val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
            val dbModelList = coinInfoDtoList.map {
                mapper.mapDtoToDbModel(it)
            }
            coinInfoDao.insertPriceList(dbModelList)
            delay(10000)
        }
    }
}