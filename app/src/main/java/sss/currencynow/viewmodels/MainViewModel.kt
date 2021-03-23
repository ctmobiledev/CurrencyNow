package sss.currencynow.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Response
import sss.currencynow.databinding.MainFragmentBinding
import sss.currencynow.models.CurrencyQuote
import sss.currencynow.models.ExchangeRatePair
import sss.currencynow.repository.GetData
import sss.currencynow.repository.RetrofitClient
import sss.currencynow.views.RateAdapter

class MainViewModel : ViewModel() {

    lateinit var vmContext: Context
    lateinit var vmBinding: MainFragmentBinding

    val NUMBER_OF_RESULTS = 32

    private var currencyQuote = CurrencyQuote()

    // User selections
    private var currentCurrencySelection = "USD"
    private var baseCurrencyAmountInput = "1.00"

    // LiveData gives us updated events when they change.
    var listExchangeRatePairs: MutableList<ExchangeRatePair> =
        MutableList(NUMBER_OF_RESULTS) { ExchangeRatePair("", 0.0) }

    val liveExchangeRatePairs: MutableLiveData<MutableList<ExchangeRatePair>> = MutableLiveData(
        mutableListOf())


    fun calculateExchangeValues(amountString: String, refresh: Boolean) {

        val service = RetrofitClient.retrofitInstance.create(GetData::class.java)

        println(">>> currentCurrencySelection = $currentCurrencySelection")
        println(">>> amountString = $amountString")

        val call = service.getCurrencyQuote(currentCurrencySelection)
        val amountDouble = amountString.toDouble()



        // NOW, PARSE THE RESULTS
        // Execute the request asynchronously
        // NOTE the qualifier for "Callback" - there are two kinds

        call.enqueue(object : retrofit2.Callback<CurrencyQuote> {

            var nextListIndex = 0

            // Handle a successful response

            override fun onResponse(
                call: Call<CurrencyQuote>,
                response: Response<CurrencyQuote>
            ) {

                println(">>> **************************** RESPONSE *******************************")
                println(">>> *********************************************************************")
                println(">>> **************************** RESPONSE *******************************")

                println(">>> RESPONSE BODY = " + response.body())

                currencyQuote = response.body()!!

                println(">>> base: ${currencyQuote?.base}")
                println(">>> date: ${currencyQuote?.date}")

                //rates = currencyQuote.rates as List<Rates>

                var rateQuote = currencyQuote.rates

                // total of 32 currencynow at date of production

                // PROGRAMMER'S NOTE: It would have been nice if the API had used a JSONArray instead of individual property names for
                // each of the various currencynow - then a loop across all the values with an index could have been used. As it was,
                // there were 32 individually named properties for the "rates" object, so we wound up with all these lines. Pity.

                listExchangeRatePairs.clear()

                addIfNotSelectedCurrency(ExchangeRatePair("AUD", currencyQuote.rates.AUD, (amountDouble * currencyQuote.rates.AUD)))
                addIfNotSelectedCurrency(ExchangeRatePair("BGN", currencyQuote.rates.BGN, (amountDouble * currencyQuote.rates.BGN)))
                addIfNotSelectedCurrency(ExchangeRatePair("BRL", currencyQuote.rates.BRL, (amountDouble * currencyQuote.rates.BRL)))
                addIfNotSelectedCurrency(ExchangeRatePair("CAD", currencyQuote.rates.CAD, (amountDouble * currencyQuote.rates.CAD)))
                addIfNotSelectedCurrency(ExchangeRatePair("CHF", currencyQuote.rates.CHF, (amountDouble * currencyQuote.rates.CHF)))

                addIfNotSelectedCurrency(ExchangeRatePair("CNY", currencyQuote.rates.CNY, (amountDouble * currencyQuote.rates.CNY)))
                addIfNotSelectedCurrency(ExchangeRatePair("CZK", currencyQuote.rates.CZK, (amountDouble * currencyQuote.rates.CZK)))
                addIfNotSelectedCurrency(ExchangeRatePair("DKK", currencyQuote.rates.DKK, (amountDouble * currencyQuote.rates.DKK)))
                addIfNotSelectedCurrency(ExchangeRatePair("EUR", currencyQuote.rates.EUR, (amountDouble * currencyQuote.rates.EUR)))
                addIfNotSelectedCurrency(ExchangeRatePair("GBP", currencyQuote.rates.GBP, (amountDouble * currencyQuote.rates.GBP)))
                addIfNotSelectedCurrency(ExchangeRatePair("HKD", currencyQuote.rates.HKD, (amountDouble * currencyQuote.rates.HKD)))

                addIfNotSelectedCurrency(ExchangeRatePair("HUF", currencyQuote.rates.HUF, (amountDouble * currencyQuote.rates.HUF)))
                addIfNotSelectedCurrency(ExchangeRatePair("HRK", currencyQuote.rates.HRK, (amountDouble * currencyQuote.rates.HRK)))
                addIfNotSelectedCurrency(ExchangeRatePair("IDR", currencyQuote.rates.IDR, (amountDouble * currencyQuote.rates.IDR)))
                addIfNotSelectedCurrency(ExchangeRatePair("ILS", currencyQuote.rates.ILS, (amountDouble * currencyQuote.rates.ILS)))
                addIfNotSelectedCurrency(ExchangeRatePair("INR", currencyQuote.rates.INR, (amountDouble * currencyQuote.rates.INR)))

                addIfNotSelectedCurrency(ExchangeRatePair("ISK", currencyQuote.rates.ISK, (amountDouble * currencyQuote.rates.ISK)))
                addIfNotSelectedCurrency(ExchangeRatePair("JPY", currencyQuote.rates.JPY, (amountDouble * currencyQuote.rates.JPY)))
                addIfNotSelectedCurrency(ExchangeRatePair("KRW", currencyQuote.rates.KRW, (amountDouble * currencyQuote.rates.KRW)))
                addIfNotSelectedCurrency(ExchangeRatePair("MXN", currencyQuote.rates.MXN, (amountDouble * currencyQuote.rates.MXN)))
                addIfNotSelectedCurrency(ExchangeRatePair("MYR", currencyQuote.rates.MYR, (amountDouble * currencyQuote.rates.MYR)))

                addIfNotSelectedCurrency(ExchangeRatePair("NOK", currencyQuote.rates.NOK, (amountDouble * currencyQuote.rates.NOK)))
                addIfNotSelectedCurrency(ExchangeRatePair("NZD", currencyQuote.rates.NZD, (amountDouble * currencyQuote.rates.NZD)))
                addIfNotSelectedCurrency(ExchangeRatePair("PHP", currencyQuote.rates.PHP, (amountDouble * currencyQuote.rates.PHP)))
                addIfNotSelectedCurrency(ExchangeRatePair("PLN", currencyQuote.rates.PLN, (amountDouble * currencyQuote.rates.PLN)))
                addIfNotSelectedCurrency(ExchangeRatePair("RON", currencyQuote.rates.RON, (amountDouble * currencyQuote.rates.RON)))

                addIfNotSelectedCurrency(ExchangeRatePair("RUB", currencyQuote.rates.RUB, (amountDouble * currencyQuote.rates.RUB)))
                addIfNotSelectedCurrency(ExchangeRatePair("SEK", currencyQuote.rates.SEK, (amountDouble * currencyQuote.rates.SEK)))
                addIfNotSelectedCurrency(ExchangeRatePair("SGD", currencyQuote.rates.SGD, (amountDouble * currencyQuote.rates.SGD)))
                addIfNotSelectedCurrency(ExchangeRatePair("THB", currencyQuote.rates.THB, (amountDouble * currencyQuote.rates.THB)))
                addIfNotSelectedCurrency(ExchangeRatePair("TRY", currencyQuote.rates.TRY, (amountDouble * currencyQuote.rates.TRY)))

                addIfNotSelectedCurrency(ExchangeRatePair("USD", currencyQuote.rates.USD, (amountDouble * currencyQuote.rates.USD)))
                addIfNotSelectedCurrency(ExchangeRatePair("ZAR", currencyQuote.rates.ZAR, (amountDouble * currencyQuote.rates.ZAR)))

                // HERE'S WHERE WE MOVE DATA TO THE RCV
                println(">>> MOVING DATA TO RCV")

                println(">>> listItemAdapter reset?")

                println(">>> refresh flag is $refresh")
                if (refresh) {
                    vmBinding.rcvRateQuotes.adapter = RateAdapter(
                        listExchangeRatePairs,
                        { item ->
                            println(">>> MainViewModel item: ${item.currencySymbol}")
                            exchangeRatePairTapped(item)
                            //vmBinding.txtPrompt.text = item.currencySymbol
                        }
                    )
                }

                println(">>> ****************************** END **********************************")
                println(">>> *********************************************************************")
                println(">>> ****************************** END **********************************")


                // Send latest values to the observable
                println(">>> updating live data list")
                liveExchangeRatePairs.value = listExchangeRatePairs

                // Signal underlying data have changed
                vmBinding.rcvRateQuotes.adapter!!.notifyDataSetChanged()

            }


            // Handle execution failures

            override fun onFailure(call: Call<CurrencyQuote>, throwable: Throwable) {

                //If the request fails, then display the following toast//

                println(">>> throwable = " + throwable.message)
                println(">>> throwable = " + throwable.stackTrace)

                Toast.makeText(vmContext, "Unable to get exchange rates.", Toast.LENGTH_SHORT)
                    .show()

            }


            fun addIfNotSelectedCurrency(newPair: ExchangeRatePair) {
                if (newPair.currencySymbol != currentCurrencySelection) {
                    //println(">>> currencySymbol = ${newPair.currencySymbol}, conversionResult = ${newPair.conversionResult.toString()}")
                    listExchangeRatePairs.add(nextListIndex, newPair)           // was exchangeRatePairs
                    nextListIndex++
                }
            }

        })

    }


    private fun exchangeRatePairTapped(result: ExchangeRatePair) {          // #1 of 2

        println(">>> exchangeRatePairTapped fired")

        currentCurrencySelection = result.currencySymbol.toString()
        println(">>> currentCurrencySelection is now: $currentCurrencySelection")

        vmBinding.txtPrompt.text = "Enter currency amount in $currentCurrencySelection:"
        calculateExchangeValues(baseCurrencyAmountInput, false)

        vmBinding.rcvRateQuotes.adapter!!.notifyDataSetChanged()

        Toast.makeText(vmContext.applicationContext,
            "Now showing amounts for currency ${result.currencySymbol}",
            Toast.LENGTH_SHORT).show()

    }


}