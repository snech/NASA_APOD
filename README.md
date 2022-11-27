## Introduction
* An Android app to display AOPD Images of the current date, allow users to search for the picture for the date of their choice and adding the pictures to the favourites
* Users can see the their favourites images
* Display date, explanination, title and image/video of the day. if its a video it will navigate to external browser
* Caching the information, displaying the last updated information incase of network unavailablity.
* Using the NASA APOD API - https://api.nasa.gov/planetary/apod to fetch the information

## Technologies 
* UI: Single activity, Fragments,
* Architecture: MVVM, Hilt, Flow, Room, ViewModel, Paging, Lifecycle, DataBinding, ViewBinding
* Third Libs: Retrofit, Glide, Gson
* Foundation: Kotlin, KTX, AndroidX, AppCompat
* Behavior: Offline database.
