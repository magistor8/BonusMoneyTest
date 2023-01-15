package com.magistor8.bonusmoneytest.data.retrofit.entires

import com.google.gson.annotations.SerializedName


data class CardsData (

    @SerializedName("company"                ) var company                : Company?                = Company(),
    @SerializedName("customerMarkParameters" ) var customerMarkParameters : CustomerMarkParameters? = CustomerMarkParameters(),
    @SerializedName("mobileAppDashboard"     ) var mobileAppDashboard     : MobileAppDashboard?     = MobileAppDashboard()

)