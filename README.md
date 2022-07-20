Charging Spots App
=================

## App description:

* App display a list of near-by elecric charging spots. Clicking on a spot displays some details of that spot. So, I designed the app to be
on `landscape orientation`, will show two panes on the same screen, one for the list of spots and the second for the spot details.
But, on `portaite orientation` will show the list of spots in a separate screen, and when clicking on any spot, the details show in another separate screen.
(Check the screenshoots at the bottom)

* There is a `Settings` screen to set the `Max Distance` (from your current location) which the user want to see a charging spots. And another setting to set the `Page Size`.

* There is a `Filter` screen has filter by `opened` spots only.

* The app working fine for dark mode.

## Project description:

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

* There are two environment staging and production, you can switch between them in Build Variants section.

## Assumption: 

* I made a mockup API (https://62d5a7e9d4406e52355f2923.mockapi.io/api/v1/spots?) on the mockapi.io website to simulate the near-by electric charging spots. This API has a list of random spots objects like this:

```json
 [
     {
      "id": "1"
      "name": "Electric charging spot 1",
      "thumbnail": "http://loremflickr.com/640/480/people",
      "address": "082 Parker Orchard",
      "status": false,
      "lat": "25.2012",
      "lng": "55.2308",
     },
 ]
 ```
 
* You will note the thumbnails are a random photos (generated using the mockup.io) not related to the electric charging spots. 
 
* You will see the spots sorted ascending by distance (based on the user’s current location and the spot location) but, only on the page level not on all pages as a whole. I can solve this issue after and did the sorting for the whole listed spots (whole pages) by caching the data in the room local DB and sort-by the `distance` parameter (that was already  calculated for every spot before being saved in the local DB)


## Screenshoots:

#### Portraite
![1](https://user-images.githubusercontent.com/17904163/179917458-5c8225f9-adf9-4b62-8d52-8483038dd5de.jpeg)
![2](https://user-images.githubusercontent.com/17904163/179917449-975bc64d-a6a5-4f3e-b05c-da0ee4e9e94b.jpeg)
![3](https://user-images.githubusercontent.com/17904163/179917459-26bd29b6-51c5-4080-af6d-3a8c545475c5.jpeg)
![4](https://user-images.githubusercontent.com/17904163/179917462-e76af696-87ba-469d-bca5-39111592678f.jpeg)

#### Landscape
![landscape](https://user-images.githubusercontent.com/17904163/179922067-7e109b70-3549-448e-bef1-38de14590b16.png)



