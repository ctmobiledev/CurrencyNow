package sss.currencynow.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CurrencyQuote {
    @SerializedName("base")
    @Expose
    var base: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("rates")
    @Expose
    lateinit var rates: Rates
}