package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.CoinInfoDbModel
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.model.CoinNamesListDto
import com.example.cryptoapp.domain.CoinInfo
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class CoinMapper {

    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDbModel(
        dto.fromSymbol,
        dto.toSymbol,
        dto.price,
        dto.lastUpdate,
        dto.highDay,
        dto.lowDay,
        dto.lastMarket,
        dto.imageUrl
    )

    fun mapDbModelToDomainEntity(dbModel: CoinInfoDbModel): CoinInfo {
        val lastUpdateStr = convertTimestampToTime(dbModel.lastUpdate)
        val imageUrlFull = ApiFactory.BASE_IMAGE_URL+dbModel.imageUrl
        return CoinInfo(
            dbModel.fromSymbol,
            dbModel.toSymbol,
            dbModel.price,
            lastUpdateStr,
            dbModel.highDay,
            dbModel.lowDay,
            dbModel.lastMarket,
            imageUrlFull
        )
    }

    fun mapJsonContainerToListInfoDto(container: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = container.coinInfoJsonObject ?: return result
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

    fun mapNamesListToString(namesListDto: CoinNamesListDto): String =
        namesListDto.names?.map { it.coinNameDto?.name }?.joinToString(",") ?: ""

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
}