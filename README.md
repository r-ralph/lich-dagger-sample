# Integrate Lich with Dagger2

This is a sample project that integrates Lich libraries with Dagger2.
Please refer to the following documents for Lich libraries.

- [Lich Component](https://github.com/line/lich/tree/master/component)
- [Lich ViewModel](https://github.com/line/lich/tree/master/viewmodel)

## Manage Dagger's components with Lich Component

You can use Lich Component to manage Dagger's components.
The following is sample code for managing the root Dagger component of an application (aka `ApplicationGraph`).

```kotlin
@Singleton
@Component(modules = [AppModule::class])
interface ApplicationGraph {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationGraph
    }

    companion object : ComponentFactory<ApplicationGraph>() {
        override fun createComponent(context: Context): ApplicationGraph =
            DaggerApplicationGraph.factory().create(context)
    }
}
```

By doing this, you can get the instance of `ApplicationGraph` by `context.getComponent(ApplicationGraph)`.
You no longer need to implement a custom `Application` class to hold the instance of `ApplicationGraph`.

```kotlin
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sampleServiceClient: SampleServiceClient

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent(ApplicationGraph).inject(this)
        super.onCreate(savedInstanceState)

        // snip...
    }
}
```

Using Lich Component makes it easy to mock `ApplicationGraph`.

```kotlin
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun testSampleServiceClient() {
        val mockServiceClient = mockk<SampleServiceClient> {
            // snip...
        }
        mockComponent(ApplicationGraph) {
            val slot = slot<MainActivity>()
            every { inject(capture(slot)) } answers {
                slot.captured.sampleServiceClient = mockServiceClient
            }
        }

        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                assertSame(mockServiceClient, activity.sampleServiceClient)
                // snip...
            }
        }
    }
}
```

## Manage components for Dynamic Feature Modules

You can also use Lich Component to manage Dagger's components in
[Dynamic Feature Modules](https://developer.android.com/studio/projects/dynamic-delivery) like this:

```kotlin
@FooFeatureScope
@Component(dependencies = [ApplicationGraph::class])
interface FooFeatureGraph {

    fun inject(activity: FooFeatureActivity)

    @Component.Factory
    interface Factory {
        fun create(applicationGraph: ApplicationGraph): FooFeatureGraph
    }

    companion object : ComponentFactory<FooFeatureGraph>() {
        override fun createComponent(context: Context): FooFeatureGraph =
            DaggerFooFeatureGraph.factory().create(context.getComponent(ApplicationGraph))
    }
}

/**
 * A Scope for singleton objects in the `foo_feature` module.
 */
@Scope
@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FooFeatureScope
```

```kotlin
class FooFeatureActivity : AppCompatActivity() {

    @Inject
    lateinit var fooFeatureService: FooFeatureService

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent(FooFeatureGraph).inject(this)
        super.onCreate(savedInstanceState)

        // snip...
    }
}
```

## Instantiate ViewModels using Dagger2

You can use Dagger2 to instantiate ViewModels.

First, create a Dagger subcomponent that has provision methods for your ViewModel classes.
The subcomponent must have a factory that takes `SavedState` as an argument.

```kotlin
/**
 * A Dagger subcomponent that instantiates ViewModels in the `app` module.
 */
@Subcomponent
interface AppViewModelsGraph {

    fun sampleViewModel(): SampleViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance savedState: SavedState): AppViewModelsGraph
    }
}
```

Then, add a provision method for the subcomponent's factory to a component such as `ApplicationGraph`.

```kotlin
@Singleton
@Component(modules = [AppModule::class])
interface ApplicationGraph {

    fun viewModelsGraphFactory(): AppViewModelsGraph.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationGraph
    }

    companion object : ComponentFactory<ApplicationGraph>() {
        override fun createComponent(context: Context): ApplicationGraph =
            DaggerApplicationGraph.factory().create(context)
    }
}
```

Finally, you can get an instance of the ViewModel like this:

```kotlin
class SampleViewModel @Inject constructor(
    private val context: Context,
    savedState: SavedState,
    private val sampleServiceClient: SampleServiceClient
) : AbstractViewModel() {

    private val message: String by savedState.initial("")

    // snip...

    companion object : ViewModelFactory<SampleViewModel>() {
        override fun createViewModel(context: Context, savedState: SavedState): SampleViewModel =
            context.getComponent(ApplicationGraph).viewModelsGraphFactory().create(savedState)
                .sampleViewModel()
    }
}
```

```kotlin
class SampleActivity : AppCompatActivity() {

    private val sampleViewModel by viewModel(SampleViewModel)

    // snip...
}
```
