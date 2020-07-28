---
title: True-Alarm
description: It's not false!
---

# True-Alarm
*The alarm clock that adjusts itself according to your commute time*

True-Alarm is a fresh new take on an alarm application. Designed for the driving commuter, True-Alarm will wake you up not at a specific time, but at the time you need to be woken up. It uses the google maps API to assess your commute time, and based on that time it will wake you up earlier or later as traffic changes. All you need to do is enter a start location, destination location, what time you want to want to arrive, and how much time you want between waking up and walking out the door! Whether it's rain delays or road closures, True-Alarm will make sure you're up in time to get where you need to go!

## The Ispiration

I decided to build this particular application for two main reasons: 
1. It spans multiple skill areas of development such as API integration, and connecting to a device's hardware. 
2. Having previously lived in Los Angeles, planning for commute times is imperative and there is no easy way to account for significant changes in drive time outside of leaving an extra hour just in case.

I should also note that the idea for this application was given to me by my fiance's brother who is a postal worker in the greater Boston area. He commutes over 45 minutes to work and says during the winter months, the snowfall overnight has caused him to be late in the past.

He suggested there should be an alarm clock that takes weather delays into account and said it was a 'million dollar idea'. 

# How It Works
## Set-Up
True-Alarm is easy to set up and only requires 4 key pieces of information to do its job. Simply press the plus-sign button to take you to the ALARM CREATION screen. There you will input the following:
  - A starting address
  - A destination address
  - The time you want to arrive at your destination
  - And how much time you want between waking up and departing (buffer time)

## Easy to Edit
Pressing the EDIT ALARM button at the bottom of the ALARMS screen will take you to the ALARM CREATION screen where you can select which alarm you'd like to edit from a dropdown list in the upper left corner. You can change your addresses, buffer time, and alarm name from here.

## Under the Hood (TL;DR)
True-Alarm uses the Google Distance Matrix API (GDMA) to get real-time updates based on live traffic data to return your commute time. Providing a start location, end location, and arrival time will allow the GDMA's traffic_model to return a duration_in_traffic estimate and suggested departure time. These are based on historical traffic data and live traffic data, with live traffic data becomes more important the closer it gets to the actual departure time.


The fixed user-provided buffer time gets deducted from the suggested departure time and and you get the initial alarm time. Upon the first creation of an alarm, this initial alarm time gets set as an alarm in the AlarmManager API on the android device. As duration_in_traffic changes, it will run through the same calculation of determining an alarm time and trigger the setExactAndAllowWhileIdle method in AlarmManager to set the current alarm to the new adjusted time. Optimistic and Pessimistic modes can be used to set the alarm based on the shortest or longest estimated commute time respectively.

Once the alarm goes off, pressing the STOP button will invoke the AlarmManager's cancel method to silence the current alarm and prevent it from going off again if the commute time changes to set the alarm for a couple of minutes later. After pressing the STOP button, the user is returned to their home screen.




## Additional Features
* Add a custom names for your alarms
* Have different ringtones for different alarms
* Optimist & Pessimist mode estimate your time in traffic as being shorter or longer respectively
* (Stretch feature) Get a reminder when you're 5 min out from your suggested departure time

## Intended users

* **People who have longer work commutes**

    > As someone who commutes 1 hour each way for work, I don't always know when there will be construction or closures that will add an extra half hour or more to my drive. Waking up earlier when my commute will be longer would help me from being late or missing my important meetings in the morning."

* **People who live in areas with erratic weather**

    > During the winter months, there's no telling if a storm that happens in the middle of the night is going to be a light dusting or plug the streets up with 2 feet of snow. If it's going to take extra time to get to school in the morning, I need to know so I can wake up with enough time to have a good breakfast before my exams."


#True-Alarm's Current State
Unfortunately True-Alarm's current state puts it in the "Paper Weight" category.
As it currently stands, the app has a home view that displays the list of created alarms with textviews for it's Name, Set Time, and Arrival Time at a destination. It has a floating action button that takes you to an alarm details fragment where the user can create a new alarm.
Each alarm item has an edit button which takes you to the alarm details fragment for that particular alarm. 
Each alarm item also has a delete button which deletes the alarm - but this is not currently functioning. Clicking the button does nothing. 

The alarm detail fragment contains editable fields for attributes of the alarm such as Name, Arrival Time, and Buffer Time.
It also has a SAVE button which saves these details to the current alarm and, if editing a new alarm, creates a new alarm and adds it to the home list, then returns the user to the home view. 
Another feature is a Google Map that is integrated into the alarm detail fragment. This would be used eventually for vieweing the driving route and traffic conditions.

The app also currently requests necessary permissions such as accessing fine location. 


There are several unimplemented elements that fall into one of 3 categories of urgency:
* High Priority
    * INCOMPLETE: App's Delete Alarm button is not yet hooked up to delete an alarm from the database. 
    * INCOMPLETE: App is not currently set up to post/get information from Google Routes - is unable to get estimated commute time. 
    * INCOMPLETE: App is not currently connected to the android AlarmManager and is not set up to SET alarms.
    * INCOMPLETE: App does not currently have a working button to toggle an alarm on/off.
    * INCOMPLETE: App does not currently have a working button to turn an alarm off if it is going off.
    
    
* Medium Priority
    * BUG: Current alarm detail fragment layout overlaps certain items while in landscape mode - potential fix is locking the app into vertical mode.
    * INCOMPLETE: App does not have any current protocol for how often/when to check for changes in commute time.
    
* Low Priority
    * Clean up code so that it presents in the same order between similar classes
    * Ensure all string resources are extracted to the resource file. 
    
Cosmetic Fixes:

* Would prefer to have the "Leave By" time (once it is calculated) displayed on Home screen instead of selected Arrival time
* Implement a text style for Names/Times
* Create custom color schemes for light/dark themes
* Re-space the alarm details layout to optimize the view
* Add custom icons for the application launcher and its various buttons

Functional Stretch Goals:

* Add functionality to change alarm ringtone
* Add functionality to schedule alarms on different days
* Add a poly line route after entering start/end locations
* Be able to view traffic conditions when the alarm goes off
* Assist in filling in a starting address based on current GPS location

# Technical Requirements & Dependencies

True Alarm was written using Java 1.8 and targeting Android API 29 with a minumum requirement of Android API 24.
The application should only be operated in Vertical as the detail view becomes too congested when in landscape mode. 


True-Alarm uses the following dependencies:
```
// Google, Gson, and Google Maps dependencies
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    implementation 'com.google.android.gms:play-services-location:17.0.0'
    implementation 'com.google.maps.android:android-maps-utils:0.6.2'
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'com.google.android.material:material:1.1.0'

// Retrofit dependencies
    def retrofit_version = "2.6.0"
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
    implementation "com.squareup.retrofit2:adapter-rxjava2:$retrofit_version"

// ReactiveX dependencies
    implementation 'io.reactivex.rxjava2:rxjava:2.2.19'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'

// Room and SQLite dependencies
    def room_version = "2.2.5"
    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version"
    implementation "androidx.room:room-rxjava2:$room_version"


// Stetho (inspection) dependency
    implementation 'com.facebook.stetho:stetho:1.5.1'
```

In its completed state, True Alarm requires the following persmissions:
  * INTERNET
    * Without internet, True Alarm will not be able to account for traffic changes with its alarm. 
    The alarm will remain set for what the traffic conditions were at the time it was last updated while connected to the internet.
    It may also crash if it tried to access google maps for information while not connected to the internet. 
    But a set alarm will still go off without the internet.
  * ACCESS_FINE_LOCATION
    * True Alarm will still work without this permission. This permission is intended to be used to assist in providing a starting location for an alarm.
  * SET_ALARM
    * True Alarm will not work without this permission. It will provide an estimated commute time when given a preferred arrival time and starting/ending locations, but it will not function as an alarm clock.
  * WAKE_LOCK
    * True Alarm will not be able to signal an alarm if the device goes asleep without this permission.
    So unless the user plans on actively using their device when they need the alarm to go off, the application will be substantially less useful without this permission.
  * RECEIVE_BOOT_COMPLETED
    * This permission is required to make sure any alarms that were set when a device is re-booted, will be set again once it boots up.
    This prevents the user from accidentally disabling any alarms on a device re-start. 

## Services Used
* [Android AlarmManager API](https://developer.android.com/reference/android/app/AlarmManager)
* [Android RingtoneManager API](https://developer.android.com/reference/android/media/RingtoneManager)
* [Google Maps - Distance Matrix API](https://developers.google.com/maps/documentation/distance-matrix/intro)

True-Alarm will be able to act as a general purpose alarm clock if disconnected from the Google Maps service. If it becomes disconnected from Google Maps at any point after the alarm creation, the alarm would simply remain set at its most recent set time.
The user could also theoretically only input an Arrival Time and the alarm would go off at that time, using it in an even more basic capacity.
This application would not be able to function, however, without the AlarmManager and RingtoneManager APIs.  


## Design & Data Model Documentation

* [Wireframe diagram](wireframe.md)
* [Entity Relationship Diagram](erd.md)

## Entity Classes

* [Alarm Entity](https://github.com/pcutter1/true-alarm/blob/master/app/src/main/java/edu/cnm/deepdive/truealarm/model/entity/Alarm.java)
* [Location Entity](https://github.com/pcutter1/true-alarm/blob/master/app/src/main/java/edu/cnm/deepdive/truealarm/model/entity/Location.java)

## Repositories

* [Alarm Repository](https://github.com/pcutter1/true-alarm/blob/master/app/src/main/java/edu/cnm/deepdive/truealarm/service/AlarmRepository.java)
* [Location Repository](https://github.com/pcutter1/true-alarm/blob/master/app/src/main/java/edu/cnm/deepdive/truealarm/service/LocationRepository.java)

## Dao'n Town

* [Alarm Dao](https://github.com/pcutter1/true-alarm/blob/master/app/src/main/java/edu/cnm/deepdive/truealarm/model/dao/AlarmDao.java)
* [Location Dao](https://github.com/pcutter1/true-alarm/blob/master/app/src/main/java/edu/cnm/deepdive/truealarm/model/dao/LocationDao.java)

## Database and DDL

* [Room Database](https://github.com/pcutter1/true-alarm/blob/master/app/src/main/java/edu/cnm/deepdive/truealarm/service/AlarmDatabase.java)
* [SQLite DDL](ddl.md)