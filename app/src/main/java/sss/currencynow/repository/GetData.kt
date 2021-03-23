package sss.currencynow.repository

import sss.currencynow.models.CurrencyQuote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetData {

    // Specify the request type and pass the relative URL

    // The full URL will be
    // BASE_URL = "https://api.exchangeratesapi.io/latest"

    @GET("latest")
    fun getCurrencyQuote(@Query("base") baseCurrency: String): Call<CurrencyQuote>

}

