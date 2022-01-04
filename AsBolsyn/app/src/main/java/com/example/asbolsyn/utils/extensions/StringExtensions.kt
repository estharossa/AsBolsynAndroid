package com.example.asbolsyn.utils.extensions

import com.example.asbolsyn.utils.CommonConstants
import java.text.SimpleDateFormat
import java.util.*

fun String.phoneGoodFormat() = this.replace("(", "")
    .replace(")", "")
    .replace("-", "")
    .replace(" ", "")
    .replace("+", "")

fun String.toDate(format: String): Date {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.parse(this)
}


val String.priceString: String
    get() {
        return try {
            val priceInt = Integer.parseInt(this.replace(" ", ""))
            priceInt.priceString
        } catch (e: NumberFormatException) {
            "- ${CommonConstants.TENGE}"
        }
    }
