package com.magistor8.bonusmoneytest.data.retrofit.entires

import com.google.gson.annotations.SerializedName


data class MobileAppDashboard (

  @SerializedName("companyName"         ) var companyName         : String? = null,
  @SerializedName("logo"                ) var logo                : String? = null,
  @SerializedName("backgroundColor"     ) var backgroundColor     : String? = null,
  @SerializedName("mainColor"           ) var mainColor           : String? = null,
  @SerializedName("cardBackgroundColor" ) var cardBackgroundColor : String? = null,
  @SerializedName("textColor"           ) var textColor           : String? = null,
  @SerializedName("highlightTextColor"  ) var highlightTextColor  : String? = null,
  @SerializedName("accentColor"         ) var accentColor         : String? = null

)