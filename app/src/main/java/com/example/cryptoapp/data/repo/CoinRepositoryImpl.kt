package com.example.cryptoapp.data.repo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.domain.CoinInfo
import com.example.cryptoapp.domain.CoinRepository
import kotlinx.coroutines.delay
import java.lang.Exception

class CoinRepositoryImpl(private val application: Application) : CoinRepository {

    private val dao = AppDatabase.getInstance(application).coinInfoDao()
    private val apiService = ApiFactory.apiService
    private val mapper = CoinMapper()

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(dao.getPriceList()) { dbModelList ->
            dbModelList.map { dbModel ->
                mapper.mapDbModelToDomainEntity(dbModel)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(dao.getPriceInfoAboutCoin(fromSymbol)) { dbModel ->
            mapper.mapDbModelToDomainEntity(dbModel)
        }
    }

    override suspend fun loadData() {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val fromSymbols = mapper.mapNamesListToString(topCoins)
                val jsonContainer = apiService.getFullPriceList(fSyms = fromSymbols)
                val listInfoDto = mapper.mapJsonContainerToListInfoDto(jsonContainer)
                dao.insertPriceList(listInfoDto.map {
                    mapper.mapDtoToDbModel(it)
                })
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }
}