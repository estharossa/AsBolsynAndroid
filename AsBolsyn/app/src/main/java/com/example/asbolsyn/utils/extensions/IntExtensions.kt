package com.example.asbolsyn.utils.extensions

import com.example.asbolsyn.utils.CommonConstants
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

private const val DEFAULT_SPACE = ' '
private const val THIN_SPACE = '\u2009'

val Int.priceString: String
    get() = try {
        String.format("%s ${CommonConstants.TENGE}", this.configureDecimalFormat().format(this))
    } catch (e: NumberFormatException) {
        "- ${CommonConstants.TENGE}"
    }

fun Int.configureDecimalFormat() = DecimalFormat("#,###", DecimalFormatSymbols().apply {
    groupingSeparator =
        if (this@configureDecimalFormat > 9999 || this@configureDecimalFormat > -9999)
            DEFAULT_SPACE
        else
            THIN_SPACE
})

