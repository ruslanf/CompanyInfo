package studio.bz_soft.companyinfo.ui.companies.company

import studio.bz_soft.companyinfo.data.http.Either
import studio.bz_soft.companyinfo.data.models.CompanyModel
import studio.bz_soft.companyinfo.data.models.db.Company

interface CompanyInterface {
    suspend fun getCompanyInfo(id: Int): Either<Exception, List<CompanyModel>>

    suspend fun insertCompany(company: Company)
    suspend fun getRecords(companyId: String): List<Company>
}