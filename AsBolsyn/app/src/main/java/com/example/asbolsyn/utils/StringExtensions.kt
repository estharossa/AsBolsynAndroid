package com.example.asbolsyn.utils

fun String.phoneGoodFormat() = this.replace("(", "")
    .replace(")", "")
    .replace("-", "")
    .replace(" ", "")
    .replace("+", "")