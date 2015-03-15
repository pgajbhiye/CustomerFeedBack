Customer Feedback - cDesk
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

Contributors
--
**Pallavi Gajbhiye** [http://in.linkedin.com/pub/pallavi-gajbhiye/29/442/608](http://in.linkedin.com/pub/pallavi-gajbhiye/29/442/608)

**Naveen Ithappu** [https://www.linkedin.com/pub/naveen-ithappu/8/867/517?trk=pub-pbmap](https://www.linkedin.com/pub/naveen-ithappu/8/867/517?trk=pub-pbmap)

Zendesk Credentials
--
Login to zendesk sub domain to manage and update issues posted by users throgh mobile application

- URL: [https://naveenithappu.zendesk.com/access/unauthenticated#login](https://naveenithappu.zendesk.com/access/unauthenticated#login)
- **Email Id**: naveenithappu@gmail.com
- **Password**: mindtree

App Credentials
--
1. Name: testnav, Email: naveen@gmail.com
2. Name: Pallavi G, Email: pallavi.mp88@gmail.com
3. Or you can any Name or Email Id to test new user creation

How to build
------------
- Download archive from Git repo
- Open project using Android Studio Editor
- Build using Run configuration option in IDE

> *Requires Android Studio IDE and gradle pre installed in system to build source. * 


How to Use
--

**Testing Ticket Creation and Load**

- After installing application in mobile, lauch application
- Login with any of the test accounts metioned above
- Application will load all your open tickets
- You can use **LOG AN ISSUE** button at the bottom of the list view to post new ticket
- In **Create Issue** screen you can Record voice or type issue log and submit
- After New ticket is created, App will load the newly created tickt under **Open** tickets tab view
- You can switch between the tabs in tabb view screen to filter tickets by state

**Testing Ticket updates for server**

- Login to **zenddesk** wbsite using the credentials listed above
- Update the ticket created in above scenario with status as pending
- Come back to Mobile App
- Click on **Pending** tab in mobile app
- Updated tick will now list in this tab
- Using **zenddesk** wbsite, update more details such as Type, Status, Priority etc.
- Observe the same changes refected in app for the selected tickt


Known Issues
------------
- Applicaton may crash for if switched between tab bars while date getting loaded
