# Сountee
[![Build Status](https://travis-ci.org/iav0207/countee.svg)](https://travis-ci.org/iav0207/countee)

### Overview

_Android app for personal funds budget management_

Simple autonoumous Android app for everyday costs and balance accounting,
analisys and forecasting (not implemented yet).

### Screenshots

<img src="/screenshots/sc01.jpg" alt="Menu" width="200"/><img src="/screenshots/sc02.jpg" alt="Main diagram" width="200"/><img src="/screenshots/sc03.jpg" alt="Filtered costs diagram, monthly" width="200"/>
<img src="/screenshots/sc04.jpg" alt="'Add new cost' view" width="200"/><img src="/screenshots/sc05.jpg" alt="Filter costs to display" width="200"/>

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
