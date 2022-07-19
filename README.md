Charging Spots App
=================

* The Project designed based on the `clean architecture` concepts and `MVVM` design architecture:

* The project consists of two modules:

    1- `app` module: consists of two layers:-

    * `Data Layer`: provides the data either from the local database or from the network using the helper class that called `NetworkBoundResource`. (Note, currently in this app the data comes from network only)

    * `Presentation Layer`: any thing related to the UI exists in this layer, and this layer designed using the MVVM architecture.

    2- `core` module: consists of three layers:-

    * `Gateways Layer`: represents an abstract definition of all the data sources.

    * `Usecases Layer`: defines actions the user can trigger and all the business logic exists here in this layer.

    * `Entity Layer`: contains the data classes of the whole App.


* I applied the dependency injection using `Hilt`, you will see it in a separate package called `di` under the `app` module .

* For charging spots list, I used the `Paging` component of Jetpack to handle the paging and infinite scrolling.

* I used the `navigation` component to navigate between fragments.

* I wrote one ui test using `espresso` and one unit test using `junit4`.

* The App support `English` and `Arabic` languages, you can switch between them from the action bar menu.

* You can switch between `dark mode` and `normal mode` from the action bar menu.

* App display a list of spots. Clicking on a spot displays some details of that spot. So, I designed the app to be
on `landscape orientation`, will show two panes on the same screen, one for the list of spots and the second for the spot details.
But, on `portaite orientation` will show the list of spots in a separate screen, and when clicking on any spot, the details show in another separate screen.
(Check screenshoots in main directory)


