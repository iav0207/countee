# Сountee
[![Build Status](https://travis-ci.org/iav0207/countee.svg)](https://travis-ci.org/iav0207/countee)

### Overview

_Android app for personal funds budget management_

Simple autonoumous Android app for everyday costs and balance accounting,
analisys and forecasting (not implemented yet).

### Screenshots

![Screenshot 1](/screenshots/sc01.png?raw=true "Menu")
![Screenshot 2](/screenshots/sc02.png?raw=true "Main diagram")
![Screenshot 3](/screenshots/sc03.png?raw=true "Filtered costs diagram, monthly")

![Screenshot 4](/screenshots/sc04.png?raw=true "'Add new cost' view")
![Screenshot 5](/screenshots/sc05.png?raw=true "Filter costs to display")

### Technical info

The project is written in Java. Migration (at least partial)
to Kotlin is considered.

Libraries used:
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) for charts
- [ButterKnife](https://github.com/JakeWharton/butterknife) for view binding
- [EventBus](https://github.com/greenrobot/EventBus) for event handling
- [Typesafe Config](https://github.com/lightbend/config) for configuration
- [Dagger 2](https://github.com/google/dagger) for DI
- [Gson](https://github.com/google/gson) for serialization
- [TestNG](http://testng.org/doc/) for unit testing
