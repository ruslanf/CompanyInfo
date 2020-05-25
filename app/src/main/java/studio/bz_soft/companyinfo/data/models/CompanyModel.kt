package studio.bz_soft.companyinfo.data.models

import com.google.gson.annotations.SerializedName

data class CompanyModel(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("img") val img: String?,
    @SerializedName("description") val desc: String?,
    @SerializedName("lat") val latitude: Double?,
    @SerializedName("lon") val longitude: Double?,
    @SerializedName("www") val web: String?,
    @SerializedName("phone") val phoneNumber: String?
)