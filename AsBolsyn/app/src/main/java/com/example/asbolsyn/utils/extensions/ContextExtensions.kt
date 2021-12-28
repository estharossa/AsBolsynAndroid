package com.example.asbolsyn.utils.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.getCompatColor(@ColorRes id: Int) = ContextCompat.getColor(this, id)