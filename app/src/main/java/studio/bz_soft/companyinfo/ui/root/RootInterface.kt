package studio.bz_soft.companyinfo.ui.root

import studio.bz_soft.companyinfo.data.http.Either
import studio.bz_soft.companyinfo.data.models.CompaniesModel
import studio.bz_soft.companyinfo.data.models.db.Companies

interface RootInterface {
    suspend fun getCompaniesList(): Either<Exception, List<CompaniesModel>>

    suspend fun insertCompanies(companies: Companies)
    suspend fun getRecords(companyId: String): List<Companies>
}