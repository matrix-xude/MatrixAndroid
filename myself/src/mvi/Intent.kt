package com.xxd.kt.mvi

/**
 *    author : xxd
 *    date   : 2022/1/27
 *    desc   :
 */

// 视图需要监听的状态
/*
data class MainViewState(val fetchStatus: FetchStatus, val newsList: List<*>)

sealed class FetchStatus {
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    object NotFetched : FetchStatus()
}

// 视图监听的单次事件通知，非粘性
sealed class MainViewEffect {
    data class ShowSnackbar(val message: String) : MainViewEffect()
    data class ShowToast(val message: String) : MainViewEffect()
}

// Intent事件通知
sealed class MainViewEvent {
    data class NewsItemClicked(val newsItem: String) : MainViewEvent()
    object FabClicked : MainViewEvent()
    object OnSwipeRefresh : MainViewEvent()
    object FetchNews : MainViewEvent()
}

//  一个livedata写一个私有的可变，一个公有的不可变方法，防止外部修改
open class AacMviViewModel(application: Application) :
    AndroidViewModel(application), ViewModelContract {


    private val _viewStates: MutableLiveData = MutableLiveData()
    fun viewStates(): LiveData = _viewStates

    private var _viewState: STATE? = null
    protected var viewState: STATE
        get() = _viewState
            ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            Log.d(TAG, "setting viewState : $value")
            _viewState = value
            _viewStates.value = value
        }

    private val _viewEffects: SingleLiveEvent = SingleLiveEvent()
    fun viewEffects(): SingleLiveEvent = _viewEffects

    private var _viewEffect: EFFECT? = null
    protected var viewEffect: EFFECT
        get() = _viewEffect
            ?: throw UninitializedPropertyAccessException("\"viewEffect\" was queried before being initialized")
        set(value) {
            Log.d(TAG, "setting viewEffect : $value")
            _viewEffect = value
            _viewEffects.value = value
        }

    @CallSuper
    override fun process(viewEvent: EVENT) {
        Log.d(TAG, "processing viewEvent: $viewEvent")
    }
}

// 初始化 init
class MainActVM(application: Application) :
    AacMviViewModel(application) {


    init {
        viewState = MainViewState(fetchStatus = FetchStatus.NotFetched, newsList = emptyList())
    }

    override fun process(viewEvent: MainViewEvent) {
        super.process(viewEvent)
    }
}

// 每次修改 viewState 都是修改副本，而非本体，防止线程问题
viewState = viewState.copy(fetchStatus = FetchStatus.Fetched, newsList = result.data)


abstract class AacMviActivity> :
AppCompatActivity() {


    abstract val viewModel: ViewModel

    private val viewStateObserver = Observer {
        Log.d(TAG, "observed viewState : $it")
        renderViewState(it)
    }

    private val viewEffectObserver = Observer {
        Log.d(TAG, "observed viewEffect : $it")
        renderViewEffect(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewStates().observe(this, viewStateObserver)
        viewModel.viewEffects().observe(this, viewEffectObserver)
    }

    abstract fun renderViewState(viewState: STATE)

    abstract fun renderViewEffect(viewEffect: EFFECT)
}


class MainActivity : AacMviActivity() {
    override val viewModel: MainActVM by viewModels()


    override fun renderViewState(viewState: MainViewState) {
//Handle new viewState
    }

    override fun renderViewEffect(viewEffect: MainViewEffect) {
//Show effects
    }
}*/
