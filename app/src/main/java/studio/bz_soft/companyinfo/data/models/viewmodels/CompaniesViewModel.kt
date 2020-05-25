package studio.bz_soft.companyinfo.data.models.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import studio.bz_soft.companyinfo.data.models.db.Companies
import studio.bz_soft.companyinfo.data.repository.RepositoryInterface

class CompaniesViewModel(
    private val repository: RepositoryInterface
) : ViewModel() {
    fun getCompanies(): LiveData<List<Companies>> = repository.getAllFromCompanies()
}