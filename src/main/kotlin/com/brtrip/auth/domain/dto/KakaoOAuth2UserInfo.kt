package com.brtrip.auth.domain.dto

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class KakaoOAuth2UserInfo(
    override val attributes: Map<String, Any>
) : OAuth2UserInfo(attributes) {

    private val mapType = object : TypeToken<Map<String, Any>>() {}.type

    override fun getOAuthId() = attributes.get("id").toString()

    override fun getEmail(): String {
        val accounts: Map<String, Any> =
            Gson().fromJson(attributes.get("kakao_account").toString(), mapType)

        return accounts.get("email")?.toString()
            ?: throw RuntimeException("이메일을 불러올 수 없습니다.")
    }
}
