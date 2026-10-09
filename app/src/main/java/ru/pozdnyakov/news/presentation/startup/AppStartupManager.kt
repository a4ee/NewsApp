package ru.pozdnyakov.news.presentation.startup

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.pozdnyakov.news.domain.usecase.StartRefreshDataUseCase
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppStartupManager @Inject constructor(
    private val startRefreshDataUseCase: StartRefreshDataUseCase
) {

    private val scope = CoroutineScope(Dispatchers.IO)

    fun startRefreshData() {
        scope.launch {
            startRefreshDataUseCase
        }
    }
}