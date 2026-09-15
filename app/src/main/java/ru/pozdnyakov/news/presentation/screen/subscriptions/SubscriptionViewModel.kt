package ru.pozdnyakov.news.presentation.screen.subscriptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.pozdnyakov.news.domain.entity.Article
import ru.pozdnyakov.news.domain.usecase.AddSubscriptionUseCase
import ru.pozdnyakov.news.domain.usecase.ClearAllArticlesUseCase
import ru.pozdnyakov.news.domain.usecase.GetAllSubscriptionsUseCase
import ru.pozdnyakov.news.domain.usecase.GetArticlesByTopicsUseCase
import ru.pozdnyakov.news.domain.usecase.RemoveSubscriptionUseCase
import ru.pozdnyakov.news.domain.usecase.UpdateSubscribedArticlesUseCase
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val addSubscriptionUseCase: AddSubscriptionUseCase,
    private val clearAllArticlesUseCase: ClearAllArticlesUseCase,
    private val getAllSubscriptionUseCase: GetAllSubscriptionsUseCase,
    private val getArticlesByTopicsUseCase: GetArticlesByTopicsUseCase,
    private val removeSubscriptionUseCase: RemoveSubscriptionUseCase,
    private val updateSubscribedArticleUseCase: UpdateSubscribedArticlesUseCase

): ViewModel() {

    private val _state = MutableStateFlow(SubscriptionState())
    val state = _state.asStateFlow()

    init {
        observeSubscriptions()
        observeSelectedTopics()
    }

    fun processCommand(command: SubscriptionsCommand) {
        when(command) {
            SubscriptionsCommand.ClearArticles -> {
                viewModelScope.launch {
                    val topics = state.value.selectedTopic
                    clearAllArticlesUseCase(topics)
                }
            }
            SubscriptionsCommand.ClickSubscribe -> {
                viewModelScope.launch {
                    _state.update { previousState ->
                        val topic = state.value.query.trim()
                        addSubscriptionUseCase(topic)
                        previousState.copy(query = "")
                    }
                }
            }
            is SubscriptionsCommand.InputTopic -> {
                viewModelScope.launch {
                    _state.update { previousState ->
                        previousState.copy(query = command.query)
                    }
                }
            }
            SubscriptionsCommand.RefreshData -> {
                viewModelScope.launch {
                    updateSubscribedArticleUseCase()
                }
            }
            is SubscriptionsCommand.RemoveSubscriptions -> {
                viewModelScope.launch {
                    removeSubscriptionUseCase(command.topic)
                }
            }
            is SubscriptionsCommand.ToggleTopicSelection -> {
                _state.update { previousState ->
                    val subscriptions = previousState.subscriptions.toMutableMap()
                    val isSelected = subscriptions[command.topic] ?: false
                    subscriptions[command.topic] = !isSelected
                    previousState.copy(subscriptions = subscriptions)
                }
            }
        }
    }
    private fun observeSelectedTopics() {
        state.map {
            it.selectedTopic
        }.distinctUntilChanged()
            .flatMapLatest {
                getArticlesByTopicsUseCase(it)
            }
            .onEach { articles ->
                _state.update { prevState ->
                    prevState.copy(articles = articles)
                }
            }.launchIn(viewModelScope)
    }

    private fun observeSubscriptions() {
        getAllSubscriptionUseCase()
            .onEach { subscriptions ->
                _state.update { previousState ->
                    val updateTopics = subscriptions.associateWith { topic ->
                        previousState.subscriptions[topic] ?: true
                    }
                    previousState.copy(subscriptions = updateTopics)
                }
            }.launchIn(viewModelScope)
    }

}


sealed interface SubscriptionsCommand {
    data class InputTopic(val query: String): SubscriptionsCommand
    data object ClickSubscribe: SubscriptionsCommand
    data object RefreshData: SubscriptionsCommand
    data class ToggleTopicSelection(val topic: String): SubscriptionsCommand
    data object ClearArticles: SubscriptionsCommand
    data class RemoveSubscriptions(val topic: String): SubscriptionsCommand
}

data class SubscriptionState(
    val query: String = "",
    val subscriptions: Map<String, Boolean> = mapOf(),
    val articles: List<Article> = listOf()
) {

    val subscribeButtonEnabled: Boolean
        get() = query.isNotBlank()

    val selectedTopic: List<String>
        get() = subscriptions.filter {it.value}.map{it.key}

}