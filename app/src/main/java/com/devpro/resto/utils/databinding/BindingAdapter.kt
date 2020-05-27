package com.devpro.resto.utils.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.NumberFormat
import java.util.*


@BindingAdapter("rupiah")
fun rupiah(textView: TextView, currency: String) {
    val value = currency.toDouble()
    val localeID = Locale("in", "ID")
    val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
    textView.text = formatRupiah.format(value).toString()
}
