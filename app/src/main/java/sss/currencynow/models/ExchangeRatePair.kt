package sss.currencynow.models

data class ExchangeRatePair (
    var currencySymbol: String?,
    var rateMultiplier: Double? = 0.0,
    var conversionResult: Double? = 0.0
)