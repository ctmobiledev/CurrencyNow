package sss.currencynow.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Currencies here are shown in the order as they appear in the default (EUR) API call

class Rates {
    @SerializedName("CAD")
    @Expose
    var CAD: Double = 0.0

    @SerializedName("HKD")
    @Expose
    var HKD: Double = 0.0

    @SerializedName("ISK")
    @Expose
    var ISK: Double = 0.0

    @SerializedName("PHP")
    @Expose
    var PHP: Double = 0.0

    @SerializedName("DKK")
    @Expose
    var DKK: Double = 0.0

    @SerializedName("HUF")
    @Expose
    var HUF: Double = 0.0

    @SerializedName("CZK")
    @Expose
    var CZK: Double = 0.0

    @SerializedName("AUD")
    @Expose
    var AUD: Double = 0.0

    @SerializedName("RON")
    @Expose
    var RON: Double = 0.0

    @SerializedName("SEK")
    @Expose
    var SEK: Double = 0.0

    @SerializedName("IDR")
    @Expose
    var IDR: Double = 0.0

    @SerializedName("INR")
    @Expose
    var INR: Double = 0.0

    @SerializedName("BRL")
    @Expose
    var BRL: Double = 0.0

    @SerializedName("RUB")
    @Expose
    var RUB: Double = 0.0

    @SerializedName("HRK")
    @Expose
    var HRK: Double = 0.0

    @SerializedName("JPY")
    @Expose
    var JPY: Double = 0.0

    @SerializedName("THB")
    @Expose
    var THB: Double = 0.0

    @SerializedName("CHF")
    @Expose
    var CHF: Double = 0.0

    @SerializedName("SGD")
    @Expose
    var SGD: Double = 0.0

    @SerializedName("PLN")
    @Expose
    var PLN: Double = 0.0

    @SerializedName("BGN")
    @Expose
    var BGN: Double = 0.0

    @SerializedName("TRY")
    @Expose
    var TRY: Double = 0.0

    @SerializedName("CNY")
    @Expose
    var CNY: Double = 0.0

    @SerializedName("NOK")
    @Expose
    var NOK: Double = 0.0

    @SerializedName("NZD")
    @Expose
    var NZD: Double = 0.0

    @SerializedName("ZAR")
    @Expose
    var ZAR: Double = 0.0

    @SerializedName("USD")
    @Expose
    var USD: Double = 0.0

    @SerializedName("MXN")
    @Expose
    var MXN: Double = 0.0

    @SerializedName("ILS")
    @Expose
    var ILS: Double = 0.0

    @SerializedName("GBP")
    @Expose
    var GBP: Double = 0.0

    @SerializedName("KRW")
    @Expose
    var KRW: Double = 0.0

    @SerializedName("MYR")
    @Expose
    var MYR: Double = 0.0

    // PROGRAMMER'S NOTE: The default API call should return EUR (at a value of 1.00) but it doesn't.
    // So it's added here for completeness.

    @SerializedName("EUR")
    @Expose
    var EUR: Double = 0.0
}