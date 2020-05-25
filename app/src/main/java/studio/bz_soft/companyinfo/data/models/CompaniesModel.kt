package studio.bz_soft.companyinfo.data.models

import com.google.gson.annotations.SerializedName

data class CompaniesModel(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("img") val img: String?
)