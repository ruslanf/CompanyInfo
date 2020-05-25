package studio.bz_soft.companyinfo.root.state

data class LoadingState(
    val isLoading: Boolean = false,
    val isError: Boolean = false
)