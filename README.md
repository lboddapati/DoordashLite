# DoordashLite

## Architecture
### MVP + Router:
The app is built using the MVP architecture pattern with some elements of VIPER (mainly the Router). Each screen consists of several components which follow a pre-defined contract for the View, Presenter and an optional Router.
- Model - This is essentially the Data Layer. The DataRepository is used to fetch/update data (remote or local).
- View - Implemented by Activity (or Fragment). Renders UI elements. Example: [RestaurantListActivity.kt](https://github.com/lboddapati/DoordashLite/blob/master/app/src/main/java/com/interview/doordashlite/ui/restaurantlist/RestaurantListActivity.kt)
- Presenter: Contains Business logic. Interacts with the Data Layer and View. Example: [RestaurantListPresenter.kt](https://github.com/lboddapati/DoordashLite/blob/master/app/src/main/java/com/interview/doordashlite/ui/restaurantlist/RestaurantListPresenter.kt)
- Router: Handles transitions between screens. Example: [RestaurantListRouter.kt](https://github.com/lboddapati/DoordashLite/blob/master/app/src/main/java/com/interview/doordashlite/ui/restaurantlist/RestaurantListRouter.kt)

### [Data Layer](https://github.com/lboddapati/DoordashLite/tree/master/app/src/main/java/com/interview/doordashlite/datalayer): 
- DataRepository - The implementation details of the Data Layer are abstracted away by the DataRepository interface.
- NetworkRepository - Retrofit + RxJava + Moshi are used for working with Network Layer.
- TODO - Implement a CacheRepository for (optionally) caching network responses.
- [LifecycleAwareSubscriptionManager](https://github.com/lboddapati/DoordashLite/blob/master/app/src/main/java/com/interview/doordashlite/base/LifecycleAwareSubscriptionManager.kt) - manages Rx subscriptions based on Android Lifecycle events to prevent potential memory leaks caused by subscriptions running beyond the Activity lifecycle.

### Dependency Injection:
The application uses Koin for injecting the DataRepository, Presenters and other 3rd party dependencies. See [KoinModules.kt](https://github.com/lboddapati/DoordashLite/blob/master/app/src/main/java/com/interview/doordashlite/base/KoinModules.kt)

## Libraries used
- Networking: [Retrofit](https://github.com/square/retrofit), [RxJava](https://github.com/ReactiveX/RxJava), [RxAndroid](https://github.com/ReactiveX/RxAndroid), [Moshi](https://github.com/square/moshi)
- Image Loading: [Picasso](https://github.com/square/picasso)
- Dependency Injection: [Koin](https://github.com/InsertKoinIO/koin)
- Testing: koin-test, [mockito-kotlin](https://github.com/nhaarman/mockito-kotlin), [Robolectric](https://github.com/robolectric/robolectric)

## Miscellaneous
- API level supported: 21+
