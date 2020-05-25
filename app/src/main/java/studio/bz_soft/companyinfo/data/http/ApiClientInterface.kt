package studio.bz_soft.companyinfo.data.http

import studio.bz_soft.companyinfo.data.models.*

interface ApiClientInterface {
    suspend fun getCompaniesList(): List<CompaniesModel>
    suspend fun getCompanyInfo(id: Int): List<CompanyModel>
}