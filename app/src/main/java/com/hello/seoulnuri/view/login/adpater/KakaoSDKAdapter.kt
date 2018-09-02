package com.hello.seoulnuri.view.login.adpater

import com.kakao.auth.IApplicationConfig
import com.kakao.auth.KakaoAdapter
import com.kakao.auth.ApprovalType
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionConfig
import com.kakao.auth.KakaoSDK.getCurrentActivity
import android.app.Activity
import android.content.Context
import com.hello.seoulnuri.network.ApplicationController


/**
 * Created by VictoryWoo
 */
class KaKaoSDKAdapter : KakaoAdapter() {
    override fun getApplicationConfig(): IApplicationConfig {
        return object : IApplicationConfig {

            override fun getApplicationContext(): Context? {
                return ApplicationController.globalApplicationContext
            }
        }
    }


    override fun getSessionConfig(): ISessionConfig {
        return object : ISessionConfig {
            override fun isSecureMode(): Boolean {
                return false
            }

            override fun getAuthTypes(): Array<AuthType> {
                return arrayOf(AuthType.KAKAO_LOGIN_ALL)
            }

            override fun isUsingWebviewTimer(): Boolean {
                return false
            }

            override fun getApprovalType(): ApprovalType {
                return ApprovalType.INDIVIDUAL
            }

            override fun isSaveFormData(): Boolean {
                return true
            }
        }
    }


}