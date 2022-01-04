package com.example.asbolsyn.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toString(format: String): String =
    SimpleDateFormat(format, Locale.getDefault()).format(this)
