package studio.bz_soft.companyinfo.data.models.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import studio.bz_soft.companyinfo.data.models.db.Company
import studio.bz_soft.companyinfo.data.repository.RepositoryInterface

class CompanyViewModel(
    private val repository: RepositoryInterface
) : ViewModel() {
    fun getCompanyInfo(companyId: String): LiveData<Company> = repository.getCompanyInfo(companyId)
}