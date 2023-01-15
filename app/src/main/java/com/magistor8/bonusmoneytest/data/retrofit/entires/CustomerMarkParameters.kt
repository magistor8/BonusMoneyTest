package com.magistor8.bonusmoneytest.data.retrofit.entires

import com.google.gson.annotations.SerializedName


data class CustomerMarkParameters (

    @SerializedName("loyaltyLevel" ) var loyaltyLevel : LoyaltyLevel? = LoyaltyLevel(),
    @SerializedName("mark"         ) var mark         : Int?          = null

)