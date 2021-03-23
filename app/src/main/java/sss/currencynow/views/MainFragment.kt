package sss.currencynow.views

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sss.currencynow.R
import sss.currencynow.databinding.MainFragmentBinding
import sss.currencynow.models.*
import sss.currencynow.repository.GetData
import sss.currencynow.repository.RetrofitClient
import sss.currencynow.viewmodels.MainViewModel
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate


class MainFragment : Fragment() {

    // View Binding has taken the place of synthetics, and the naming is basically the module name + "Binding".
    // Autocomplete finds the object name after view binding has been set to 'true' in the Gradle file.

    // The '_binding' property is only valid between onCreateView and onDestroyView.
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()

        // create a daemon thread for clock watching every 5 seconds
        var timer = Timer("schedule", true)
    }

    private lateinit var currentTimeOfDay: LocalDateTime

    private lateinit var viewModel: MainViewModel

    private var currentCurrencySelection = "USD"
    private var baseCurrencyAmountInput = "1.00"

    private var alternator = 1.0


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)             // activate the toolbar

        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        return view

    }


    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        // Get our VM, pass in the context from this level
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.vmContext = activity!!.applicationContext
        viewModel.vmBinding = binding

        // update the listExchangeRatePairs
        viewModel.calculateExchangeValues(baseCurrencyAmountInput, true)

        // Set once and forget it (except for refreshes)
        _binding?.rcvRateQuotes?.adapter = RateAdapter(
            viewModel.listExchangeRatePairs,
            { item -> exchangeRatePairTapped(item) }
        )

        _binding?.rcvRateQuotes?.layoutManager =
            LinearLayoutManager(activity!!.applicationContext)

        // Fire up the data
        viewModel.liveExchangeRatePairs.observe(this, { allQuotes ->
            allQuotes?.let {
                // first arg: the list; second arg: the click listener
                RateAdapter(
                    viewModel.listExchangeRatePairs,
                    { v -> println(">>> RateAdapter clicked") }
                ).refreshData()
            }
        })

        // Wire up the submit button
        _binding?.btnSubmit?.setOnClickListener {
            println(">>> OnClickListener triggered, updating data adapter with notify")

            baseCurrencyAmountInput = binding.edtBaseCurrencyAmt.text.toString()
            var missingInput: Boolean = false

            if (baseCurrencyAmountInput == null) {
                missingInput = true
            } else {
                if (baseCurrencyAmountInput == "") {
                    missingInput = true
                }
            }
            if (missingInput) {
                Toast.makeText(activity, "Please enter a currency amount.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.calculateExchangeValues(baseCurrencyAmountInput, false)
            binding.rcvRateQuotes.adapter!!.notifyDataSetChanged()

            Toast.makeText(activity!!.applicationContext, "Latest quotes shown.", Toast.LENGTH_SHORT).show()

        }


        binding.txtPrompt.text = "Enter currency amount in $currentCurrencySelection:"

        println(">>> setup call to calculateExchangeValues()")

        viewModel.calculateExchangeValues(baseCurrencyAmountInput, true)
        binding.rcvRateQuotes.adapter!!.notifyDataSetChanged()

        startClock()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item!!.itemId

        if (id == R.id.action_about) {

            val dialogBuilder = AlertDialog.Builder(activity)

            dialogBuilder.setMessage("Currency Now\nVersion 1.0.0\nCharles Tatum II\nCopyright 2021")
                .setCancelable(false)
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                    { var dummy = 0 }
                })

            val alert = dialogBuilder.create()
            alert.setTitle("About Currency Now")
            alert.show()
        }

        return super.onOptionsItemSelected(item)
    }


    fun inversionTest() {           // private test method to prove list elements are changing, and UI reflects changed values

        // to see if updated values will show up - YES, THIS WORKED
        println(">>> inversionTest()")

        if (alternator == 1.0) { alternator = -1.0 } else { alternator = 1.0 }
        println(">>> alternator is now $alternator")

        for (n in 0 until viewModel.listExchangeRatePairs.size) {
            viewModel.listExchangeRatePairs[n].conversionResult =
                alternator * viewModel.listExchangeRatePairs[n].conversionResult!!
        }

        binding.rcvRateQuotes.adapter!!.notifyDataSetChanged()

    }

    private fun exchangeRatePairTapped(result: ExchangeRatePair) {          // #1 of 2

        println(">>> exchangeRatePairTapped fired")

        currentCurrencySelection = result.currencySymbol.toString()

        binding.txtPrompt.text = "Enter currency amount in $currentCurrencySelection:"
        viewModel.calculateExchangeValues(baseCurrencyAmountInput, true)

        binding.rcvRateQuotes.adapter!!.notifyDataSetChanged()

        Toast.makeText(activity,
            "Now showing amounts for currency ${result.currencySymbol}",
            Toast.LENGTH_SHORT).show()

    }


    private fun startClock() {

        timer = Timer("schedule", true)

        // schedule at a fixed rate - 1 second
        timer.scheduleAtFixedRate(1000, 1000) {

            currentTimeOfDay = LocalDateTime.now()

            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            val formattedTimeNow = currentTimeOfDay.format(formatter)

            // fire an update if the last digit of the second is a "0" or "5" (every 5 seconds)
            // (or if it's the first use)
            activity?.runOnUiThread(java.lang.Runnable {
                if ((binding.txtMessage.text == "---") || (currentTimeOfDay.second % 5 == 0)) {
                    viewModel.calculateExchangeValues(baseCurrencyAmountInput, false)
                    binding.txtMessage.text = "Quotes current as of $formattedTimeNow (5-sec refresh)"
                }
            })

        }

    }


}