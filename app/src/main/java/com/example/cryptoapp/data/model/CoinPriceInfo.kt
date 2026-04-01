package com.example.cryptoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptoapp.data.network.ApiFactory.BASE_IMAGE_URL
import com.example.cryptoapp.utils.convertTimestampToTime
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "full_price_list")
data class CoinPriceInfo(
    @SerializedName("TYPE")
    @Expose
    val type: String?,
    @SerializedName("MARKET")
    @Expose
    val market: String?,
    @PrimaryKey
    @SerializedName("FROMSYMBOL")
    @Expose
    val fromSymbol: String,
    @SerializedName("TOSYMBOL")
    @Expose
    val toSymbol: String?,
    @SerializedName("FLAGS")
    @Expose
    val flags: String?,
    @SerializedName("PRICE")
    @Expose
    val price: String?,
    @SerializedName("LASTUPDATE")
    @Expose
    val lastUpdate: Long?,
    @SerializedName("LASTVOLUME")
    @Expose
    val lastVolume: String?,
    @SerializedName("LASTVOLUMETO")
    @Expose
    val lastVolumeTo: String?,
    @SerializedName("LASTTRADEID")
    @Expose
    val lastTradeId: String?,
    @SerializedName("VOLUMEDAY")
    @Expose
    val volumeDay: String?,
    @SerializedName("VOLUMEDAYTO")
    @Expose
    val volumeDayTo: String?,
    @SerializedName("VOLUME24HOUR")
    @Expose
    val volume24Hour: String?,
    @SerializedName("VOLUME24HOURTO")
    @Expose
    val volume24HourTo: String?,
    @SerializedName("OPENDAY")
    @Expose
    val openDay: String?,
    @SerializedName("HIGHDAY")
    @Expose
    val highDay: String?,
    @SerializedName("LOWDAY")
    @Expose
    val lowDay: String?,
    @SerializedName("OPEN24HOUR")
    @Expose
    val open24Hour: String?,
    @SerializedName("HIGH24HOUR")
    @Expose
    val high24Hour: String?,
    @SerializedName("LOW24HOUR")
    @Expose
    val low24Hour: String?,
    @SerializedName("LASTMARKET")
    @Expose
    val lastMarket: String?,
    @SerializedName("VOLUMEHOUR")
    @Expose
    val volumeHour: String?,
    @SerializedName("VOLUMEHOURTO")
    @Expose
    val volumeHourTo: String?,
    @SerializedName("OPENHOUR")
    @Expose
    val openHour: String?,
    @SerializedName("HIGHHOUR")
    @Expose
    val highHour: String?,
    @SerializedName("LOWHOUR")
    @Expose
    val lowHour: String?,
    @SerializedName("TOPTIERVOLUME24HOUR")
    @Expose
    val topTierVolume24Hour: String?,
    @SerializedName("TOPTIERVOLUME24HOURTO")
    @Expose
    val topTierVolume24HourTo: String?,
    @SerializedName("CHANGE24HOUR")
    @Expose
    val change24Hour: String?,
    @SerializedName("CHANGEPCT24HOUR")
    @Expose
    val changePCT24Hour: String?,
    @SerializedName("CHANGEDAY")
    @Expose
    val changeDay: String?,
    @SerializedName("CHANGEPCTDAY")
    @Expose
    val changePCTDay: String?,
    @SerializedName("SUPPLY")
    @Expose
    val supply: String?,
    @SerializedName("MKTCAP")
    @Expose
    val mktCap: String?,
    @SerializedName("TOTALVOLUME24H")
    @Expose
    val totalVolume24Hour: String?,
    @SerializedName("TOTALVOLUME24HTO")
    @Expose
    val totalVolume24HourTo: String?,
    @SerializedName("TOTALTOPTIERVOLUME24H")
    @Expose
    val totalTopTierVolume24Hour: String?,
    @SerializedName("TOTALTOPTIERVOLUME24HTO")
    @Expose
    val totalTopTierVolume24HourTo: String?,
    @SerializedName("IMAGEURL")
    @Expose
    val imageUrl: String?
) {
    fun getFormattedTime(): String {
        return convertTimestampToTime(lastUpdate)
    }

    fun getFullImageUrl(): String {
        return BASE_IMAGE_URL + imageUrl
    }
}