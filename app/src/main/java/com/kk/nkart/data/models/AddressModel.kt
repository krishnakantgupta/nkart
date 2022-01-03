package com.kk.nkart.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressModel(var addressId: Int = -1,
                        var userId: Int = -1,
                        var address: String = "",
                        var city: String = "",
                        var country: String = "",
                        var pinCode: String = "",
                        var default: Boolean = false,
                        var state: String = "") : Parcelable {
    fun getFullAddress(): String {
        var fulladdress = StringBuilder()
        fulladdress.append(address)
        if (!city.isNullOrEmpty())
            fulladdress.append(",$city")
        if (!state.isNullOrEmpty())
            fulladdress.append(",$state")
        if (!pinCode.isNullOrEmpty())
            fulladdress.append(" - $pinCode")
        if (!country.isNullOrEmpty())
            fulladdress.append("\n$country")
        return fulladdress.toString()
    }
    fun getAddressForView(): String {
        var fulladdress = StringBuilder()
        fulladdress.append(address)
        if (!city.isNullOrEmpty())
            fulladdress.append("\n$city")
        if (!state.isNullOrEmpty())
            fulladdress.append(",$state")
        if (!pinCode.isNullOrEmpty())
            fulladdress.append(" - $pinCode")
        if (!country.isNullOrEmpty())
            fulladdress.append("\n$country")
        return fulladdress.toString()
    }
}