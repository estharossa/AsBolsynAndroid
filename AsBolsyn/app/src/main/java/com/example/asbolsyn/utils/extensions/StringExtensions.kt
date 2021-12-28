package com.example.asbolsyn.utils.extensions

fun String.phoneGoodFormat() = this.replace("(", "")
    .replace(")", "")
    .replace("-", "")
    .replace(" ", "")
    .replace("+", "")