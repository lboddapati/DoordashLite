# DoordashLite

## Architecture
### MVP + Router:
The app is built using the MVP architecture pattern with some elements of VIPER (mainly the Router). Each screen consists of several components which follow a pre-defined contract for the View, Presenter and an optional Router.
- Model - This is essentially the Data Layer. The DataRepository is used to fetch/update data (remote or local).
- View - Implemented by Activity (or Fragment). Renders UI elements. Example: [RestaurantListActivity.kt](app/src/main/java/com/interview/doordashlite/ui/restaurantlist/RestaurantListActivity.kt)
- Presenter: Contains Business logic. Interacts with the Data Layer and View. Example: [RestaurantListPresenter.kt](app/src/main/java/com/interview/doordashlite/ui/restaurantlist/RestaurantListPresenter.kt)
- Router: Handles transitions between screens. Example: [RestaurantListRouter.kt](app/src/main/java/com/interview/doordashlite/ui/restaurantlist/RestaurantListRouter.kt)

### [Data Layer](app/src/main/java/com/interview/doordashlite/datalayer): 
- DataRepository - The implementation details of the Data Layer are abstracted away by the DataRepository interface.
- NetworkRepository - Retrofit + RxJava + Moshi are used for working with Network Layer.
- **TODO** - Implement a CacheRepository for (optionally) caching network responses.
- [LifecycleAwareSubscriptionManager](app/src/main/java/com/interview/doordashlite/base/LifecycleAwareSubscriptionManager.kt) - manages Rx subscriptions based on Android Lifecycle events to prevent potential memory leaks caused by subscriptions running beyond the Activity lifecycle.

### Dependency Injection:
The application uses Koin for injecting the DataRepository, Presenters and other 3rd party dependencies. See [KoinModules.kt](app/src/main/java/com/interview/doordashlite/base/KoinModules.kt)

## Libraries used
- Networking: [Retrofit](https://github.com/square/retrofit), [RxJava](https://github.com/ReactiveX/RxJava), [RxAndroid](https://github.com/ReactiveX/RxAndroid), [Moshi](https://github.com/square/moshi)
- Image Loading: [Picasso](https://github.com/square/picasso)
- Dependency Injection: [Koin](https://github.com/InsertKoinIO/koin)
- Testing: koin-test, [mockito-kotlin](https://github.com/nhaarman/mockito-kotlin), [Robolectric](https://github.com/robolectric/robolectric)

## Assumptions & Limitaions
- API level supported: 21+
- The restaurant list screen is a little slow to load since it loads up to 100 restaurants at once. 
  - I set the limit to 100 to make the request a tiny bit faster. The response contains ~480 restaurants at the given lat-lng.
  - **TODO (performance improvements):**
    - Implement paginated + endless scrolling for performance improvements.
    - Implement caching for restaurant list request to make subsequent requests faster.
- The location is hardcoded to Doordash HQ (37.422740, -122.139956).
  - **TODO:** Get and use user's current location instead of harcoding location.
- The restaurant detail screen is a very barebones implementation for now.

## Known Issues
- The LifecycleAwareSubscriptionManager disposes of any inflight requests. As a result, if the user navigates away from the app (backgrounds the app for example) while the restaurant list is still loading and comes back to it, they might see a forever loading spinner.
  - **TODO (potential fixes):** 
    - Build capability into subscription manager to replay inflight requests that were discarded
    - Refresh restaurant list when user comes back to the activity (load requests in onResume instead of onCreate)

## Demo
![demo](https://media.giphy.com/media/QugxBh4CtIlEhBeGAn/giphy.gif)
   
