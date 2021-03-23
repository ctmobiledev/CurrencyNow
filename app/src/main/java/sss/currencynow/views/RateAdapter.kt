package sss.currencynow.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import sss.currencynow.models.*
import java.math.RoundingMode
import java.text.DecimalFormat

class RateAdapter(
    private var listExchangeRatePairs: List<ExchangeRatePair>,          // may wish to remove this since it's a var - or use VM?
    private val clickListener: (ExchangeRatePair) -> Unit
) : RecyclerView.Adapter<RateAdapter.MyViewHolder>() {

    //private var data: List<ExchangeRatePair>? = null

    companion object {
        var currencyFormat: DecimalFormat = DecimalFormat()
    }

    lateinit var liveTappedItem: LiveData<ExchangeRatePair>
    var mTappedItem: ExchangeRatePair = ExchangeRatePair("XXX", 1.0)


    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var cardItem = view.findViewById(sss.currencynow.R.id.cardItem) as CardView
        var txtCurrencySymbol = view.findViewById(sss.currencynow.R.id.txtCurrencySymbol) as TextView
        var txtRateEquiv = view.findViewById(sss.currencynow.R.id.txtRateEquiv) as TextView

        //String number = numberFormat.format(99.50);

        fun bind(item: ExchangeRatePair) {
            currencyFormat.roundingMode = RoundingMode.HALF_DOWN
            currencyFormat.minimumFractionDigits = 0
            currencyFormat.maximumFractionDigits = 4

            //println(">>> bind() fired with Rate: " + item.currencySymbol)

            txtCurrencySymbol.text = item.currencySymbol
            //txtRateEquiv.text = item.rateMultiplier.toString()
            txtRateEquiv.text = currencyFormat.format(item.conversionResult)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateAdapter.MyViewHolder {
        // create a new view
        var rcView = LayoutInflater.from(parent.context)
            .inflate(sss.currencynow.R.layout.rate_item_layout, parent, false)      // as TextView

        return MyViewHolder(rcView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        currencyFormat.roundingMode = RoundingMode.HALF_DOWN
        currencyFormat.minimumFractionDigits = 0
        currencyFormat.maximumFractionDigits = 4

        holder.txtCurrencySymbol.text = listExchangeRatePairs[position].currencySymbol
        //holder.txtRateEquiv.text = myDataset[position].rateMultiplier.toString()
        holder.txtRateEquiv.text = currencyFormat.format(listExchangeRatePairs[position].conversionResult)

        //tappedItem = listExchangeRatePairs[position]
        val tappedItem = listExchangeRatePairs[position]
        holder.bind(tappedItem)

        holder.cardItem.setOnClickListener {
            println(">>> CLICKED!")
            clickListener(tappedItem)
            mTappedItem = tappedItem
            println(">>> tappedItem is ${mTappedItem.currencySymbol}")

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        println(">>> getItemCount fired, size is ${listExchangeRatePairs.size} ")
        return listExchangeRatePairs.size
    }


    // THESE SHOULD WORK BUT DON'T

    fun refreshData() {
        println(">>> refreshData fired")
        notifyDataSetChanged()                          // fire the observable - this is an adapter method only!
    }

    // this was marked 'internal', not sure why
    fun setQuotesList(pListExchangeRates: MutableList<ExchangeRatePair>) {
        println(">>> setNumberList fired ")
        this.listExchangeRatePairs = pListExchangeRates
        println(">>> listWinningNumbers size is ${listExchangeRatePairs.size}")
        notifyDataSetChanged()                          // fire the observable - this is an adapter method only!
    }

}