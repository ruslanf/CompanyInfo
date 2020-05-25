package studio.bz_soft.companyinfo.root.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import studio.bz_soft.companyinfo.ui.companies.company.CompanyPresenter
import studio.bz_soft.companyinfo.ui.root.RootPresenter

class LoadingStateViewModel : LoadingStateInterface, ViewModel() {

    val loadingState: MutableLiveData<LoadingState> = MutableLiveData()

    init {
        loadingState.value = LoadingState()
    }

    override fun loadingStarted() {
        loadingState.value = currentLoadingState().copy(isLoading = true)
    }

    override fun loadingFinished() {
        loadingState.value = currentLoadingState().copy(isLoading = false)
    }

    fun registerLoadingStateListener(rootPresenter: RootPresenter, companyPresenter: CompanyPresenter) {
        rootPresenter.loadingStateInterface = this
        companyPresenter.loadingStateInterface = this
    }

    private fun currentLoadingState(): LoadingState = loadingState.value!!
}