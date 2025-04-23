package com.picpay.desafio.android.commons.utils

class TimeProviderImpl() : TimeProvider {

    override fun getActualTime(): Long = System.currentTimeMillis()
}
