package com.iterika.walli.model

data class UserData(val status: Int?, val result: ProfileResult?)

data class ProfileResult(val data: ProfileDatum?)

data class ProfileDatum(val profile: Profile?, val error: String?)

data class Profile(val id: String?, val name: String?, val surname: String?, val photo: String?, val statusId : String?, val status : String?) {
    fun getProfileUrl() = if (photo?.startsWith("www") == true) "http://$photo" else photo
}

