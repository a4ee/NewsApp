package ru.pozdnyakov.news.data.background

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.pozdnyakov.news.domain.usecase.AddSubscriptionUseCase

class RefreshDataWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val updateSubscribedArticlesUseCase: AddSubscriptionUseCase
): CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        Log.d("RefreshDataWorker", "Start")
        updateSubscribedArticlesUseCase
        Log.d("RefreshDataWorker", "Finished")
        return Result.success()
    }

}