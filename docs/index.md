---
title: True-Alarm
description: It's not false!
---

# True-Alarm
*The alarm clock that adjusts itself according to your commute time*

True-Alarm is a fresh new take on an alarm application. Designed for the driving commuter, True-Alarm will wake you up not at a specific time, but at the time you need to be woken up. It uses the google maps API to assess your commute time, and based on that time it will wake you up earlier or later as traffic changes. All you need to do is enter a start location, destination location, what time you want to want to arrive, and how much time you want between waking up and walking out the door! Whether it's rain delays or road closures, True-Alarm will make sure you're up in time to get where you need to go!

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

A possible additional feature will be that the user is notified 5min before their suggested departure time. This would help inform the user of potential changes in traffic that occurred after their wake time.



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

## Services Used
* [Android AlarmManager API](https://developer.android.com/reference/android/app/AlarmManager)
* [Android RingtoneManager API](https://developer.android.com/reference/android/media/RingtoneManager)
* [Google Maps - Distance Matrix API](https://developers.google.com/maps/documentation/distance-matrix/intro)

True-Alarm will be able to act as a general purpose alarm clock if disconnected from the Google Maps service. If it becomes disconnected from Google Maps at any point after the alarm creation, the alarm would simply remain set at its most recent set time.
The user could also theoretically only input an Arrival Time and the alarm would go off at that time, using it in an even more basic capacity.
This application would not be able to function, however, without the AlarmManager and RingtoneManager APIs.  


## Design documentation

* [Wireframe diagram](wireframe.md)
* [Entity Relationship Diagram](erd.md)
