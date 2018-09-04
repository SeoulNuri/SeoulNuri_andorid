package com.hello.seoulnuri.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by VictoryWoo
 */
open class BaseModel(
        @SerializedName("status")
        @Expose
        open var status : Int?=null,
        @SerializedName("code")
        @Expose
        open var code : Int?=null,
        @SerializedName("message")
        @Expose
        open var message : String?=null

)