package com.magistor8.bonusmoneytest.data.retrofit.entires

import com.google.gson.annotations.SerializedName


data class LoyaltyLevel (

  @SerializedName("number"      ) var number      : Int?    = null,
  @SerializedName("name"        ) var name        : String? = null,
  @SerializedName("requiredSum" ) var requiredSum : Int?    = null,
  @SerializedName("markToCash"  ) var markToCash  : Int?    = null,
  @SerializedName("cashToMark"  ) var cashToMark  : Int?    = null

)