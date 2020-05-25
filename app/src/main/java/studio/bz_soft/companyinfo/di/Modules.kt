package studio.bz_soft.companyinfo.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import studio.bz_soft.companyinfo.data.db.DbClient
import studio.bz_soft.companyinfo.data.db.DbClientInterface
import studio.bz_soft.companyinfo.data.http.ApiClient
import studio.bz_soft.companyinfo.data.http.ApiClientInterface
import studio.bz_soft.companyinfo.data.models.viewmodels.CompaniesViewModel
import studio.bz_soft.companyinfo.data.models.viewmodels.CompanyViewModel
import studio.bz_soft.companyinfo.data.repository.*
import studio.bz_soft.companyinfo.root.App
import studio.bz_soft.companyinfo.root.Constants.API_MAIN_URL
import studio.bz_soft.companyinfo.root.state.LoadingStateViewModel
import studio.bz_soft.companyinfo.ui.companies.company.CompanyController
import studio.bz_soft.companyinfo.ui.companies.company.CompanyPresenter
import studio.bz_soft.companyinfo.ui.root.RootController
import studio.bz_soft.companyinfo.ui.root.RootPresenter

val applicationModule = module {
    single { androidApplication() as App }
}

val networkModule = module {
    single { ApiClient(API_MAIN_URL, androidContext()) as ApiClientInterface }
}

val storageModule = module {
    factory<SharedPreferences> { androidContext().getSharedPreferences("local_storage", Context.MODE_PRIVATE) }
    single { DbClient(androidApplication()) as DbClientInterface }
}

val repositoryModule = module {
    single<DatabaseRepositoryInterface> { DatabaseRepository(get()) }
    single<LocalStorageInterface> { LocalStorage(get()) }
    single<RepositoryInterface> { Repository(get(), get(), get()) }
}

val presenterModule = module {
    single { RootPresenter(androidContext()) }
    single { CompanyPresenter(androidContext()) }
}

val controllerModule = module {
    single { RootController(get()) }
    single { CompanyController(get()) }
}

val liveDataModule = module {
    viewModel { LoadingStateViewModel() }
    viewModel { CompaniesViewModel(get()) }
    viewModel { CompanyViewModel(get()) }
}