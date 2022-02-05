package com.yt8492.commonjudgesystem.example.target

val ByteArray.hex: String get() {
    return joinToString("") {
        String.format("%02x", it)
    }
}
