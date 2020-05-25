package studio.bz_soft.companyinfo.data.http

import retrofit2.http.*
import studio.bz_soft.companyinfo.data.models.*
import studio.bz_soft.companyinfo.root.Constants.BASE_API

interface DataApiInterface {

    @GET(BASE_API)
    suspend fun getCompanies(): List<CompaniesModel>

    @GET(BASE_API)
    suspend fun getCompanyInfo(@Query("id") id: Int): List<CompanyModel>
}