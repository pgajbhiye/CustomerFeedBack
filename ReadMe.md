Customer Feedback
=====================

This application is focused to avoid long waiting calls to customer care tollfree line and submit and track issued faced by cutsomers directly online. This application enables customers to track issues and send feedback in realtime to get satisfactory resolution from customer help desk.

The code
--------
We used Android SDK to build mobile application targetting Android Mobile devices and 
Zendesk platform to create and track tickets. Our Android app communicates with Zendesk API through Zendesk's Android SDK. Thus, continuous internet connection will be mandatory for realtime tracking of issues.

Technology Stack
---
- Android SDK
- Zendesk SDK for Android
- Zendesk Platform / Subdomain
- Gradle to build mobile application code
- Git hub for source code version managent
- Android Studio Editor 

Challenges Encountered
---
- Zendesk API for Android is still in evolving state. Not all Core API features are available through Zendesk SDK implemented for android.
- Android's Speech Recognition API not gives access to recorded file. Evaluating alternate technicle approaches consumed more time.
- Understanding JWT authentication menchnisum and attempt to implement the same resulted in to great loss of time

Features Implemened
---
- Anonymous login
- Listing Tickets by status for realtime tracking
- Log an Issue
- Speech to text conversion for issue log
- Editable issue log
- Attach image to issue log
- Promotional intro slides with fade in effect for first time login

Unimplemented Features
---
- Attaching orginal recorded audio file is not handled
- Notifications (limitations from Zendesk SDK for Android)
- Customer - rate the expirience

Git Repo
----
[https://github.com/pgajbhiye/CustomerFeedBack](https://github.com/pgajbhiye/CustomerFeedBack)

Zendesk Credentials
--
Login to zendesk sub domain to manage and update issues posted by users throgh mobile application

- URL: [https://navpal.zendesk.com/access/unauthenticated#login](https://navpal.zendesk.com/access/unauthenticated#login)
- Email Id: socialdevcode@gmail.com
- Password: code@123

Known Issues
------------
- Applicaton may crash for if switched between tab bars while date getting loaded

How to build
------------
- Download archive from Git repo
- Open project using Android Studio Editor
- Build using Run configuration option in IDE

> *Requires Android Studio IDE and gradle pre installed in system to build source. * 