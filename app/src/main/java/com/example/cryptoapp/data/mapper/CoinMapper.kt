package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.CoinInfoDbModel
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.model.CoinNamesListDto
import com.example.cryptoapp.domain.CoinInfo
import com.google.gson.Gson

class CoinMapper {

    fun mapDtoToDbModel(dto: CoinInfoDto): CoinInfoDbModel {
        return CoinInfoDbModel(
            dto.fromSymbol,
            dto.toSymbol,
            dto.price,
            dto.lastUpdate,
            dto.highDay,
            dto.lowDay,
            dto.lastMarket,
            dto.imageUrl
        )
    }

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {

        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map { it.coinName?.name }?.joinToString(",") ?: ""
    }

    fun mapDBModelToEntity(dbModel: CoinInfoDbModel): CoinInfo {
        return CoinInfo(
            dbModel.fromSymbol,
            dbModel.toSymbol,
            dbModel.price,
            dbModel.lastUpdate,
            dbModel.highDay,
            dbModel.lowDay,
            dbModel.lastMarket,
            dbModel.imageUrl
        )
    }

    fun mapListDbModelToListEntity(list: List<CoinInfoDbModel>): List<CoinInfo> {
        return list.map {
            mapDBModelToEntity(it)
        }
    }
}