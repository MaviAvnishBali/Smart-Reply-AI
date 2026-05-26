package com.myai.smartreplyai.core.extensions

fun String.truncate(maxLength: Int): String =
    if (length <= maxLength) this else take(maxLength - 3) + "..."

fun String?.orDefault(default: String = ""): String = this ?: default

fun String.normalizeSender(): String = trim().removePrefix("@").trim()
