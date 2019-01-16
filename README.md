# Conversation Moderator 

This app analyzes the conversation directly your device to identify who is speaking and when. It does this with no effort on your part by using modern machine learning principles applied to the sounds recorded from your microphone. The analysis report after a conversation provides the total time each person spoke. Also, a scrollable piano plot is provided to observe the dynamics between speakers throughout the conversation.

This app is a prototype effort by students working with Dr. Ting Xiao in the Loyola University Chicago Computer Science department. It primarily relies on the LIUM package to diarize the audio signal. Initial development was performed by Marcos Leal and Edgard Oliveira with later improvements by Albert Du and final development and deployment by Rejoice Jebamalaidass.

## General Installation
This app was designed in Android Studio and is optimized for phones Android phones 5.0 and up. To install this app, go to Google Play Store and search Conversation Moderator. After being prompt to install click on the app and view the tutorial or read below for use.

## User's Guide
https://github.com/tingxiao/Conversation-Moderator/blob/master/documentation/User%20Guide.pdf

## Main Objective
Isn’t it disturbing when people start to cut you off in a conversation? Wouldn’t be good if managers could have a tool 
keep tracking tons of presentations they need to watch? What about developing a tool to support Speech Pathologists in 
detecting and treating common speech disorders? Having those thoughts in mind we have decided to build a tool as our independent research project at Loyola University. For practical reasons we choose to build an Android application.

## Solution 
We created an Android app which can record audio and determine who is speaking during the recording and when. Our app uses Gaussian Mixture models clustering on short sound clips for preliminary speaker segmentation. Then the clusters are used as observations in a Hidden Markov Model (HMM) to estimate the speaker of each clip and assure continuity between clips. This was done using the LIUM  open source library. Afterwards, the order and timing of each speaker is shown using a piano roll plot using the MPchart open source library. The eventual goal of this work is to produce an app which can indicate when a person has spoken too much, to moderate a group conversation.

## Tools
This issue relates to a certain topic in the speech recognition study: Speaker Diarization. This subject focus in answer 
the question Who spoke when?. In order to solve this problem, we need to track the segments of time in which a speaker spoke.
The tool utilizes a speech recognizing library written in Java called LIUM Speaker Diarization toolkit that analyzes a audio 
recording file and separates the segments of speech present in the audio, then associates these segments to each speaker.

The LIUM library uses certain techniques used in Machine Learning algorithms as Clustering and Hidden Markov Models (HMM).
The Android Studio was used to develop the main core of the application because it is the tool suggested by Google. The 
installation steps can be found at https://developer.android.com/studio/index.html

Each activity is like a new screen of the application. We created the splash screen that implies the legal implications of 
recording voice and warns the user that it might be illegal in some places.
We have the main screen where the user can record the conversation and after the recording the statistics of the 
conversation as the list of speakers identified and how long each one spoke as well as a pie chart displaying the percentage 
of speaking time each speaker had.


## Installation

Android Studio is required to run and use app.

First clone this project from this repository downloading the zip or use the command:

```cmd
git clone https://github.com/tingxiao/Conversation-Moderator.git
```
Then, you can use the Android Studio tool to open the project, following:

File -> Open Project -> Select the folder you cloned (downloaded) the project

More at https://github.com/tingxiao/Conversation-Moderator/blob/master/documentation/Installation%20Guide.pdf

## References
- LIUM Speaker Diarization Toolkit:
    - M. Rouvier, G. Dupuy, P. Gay, E. Khoury, T. Merlin, S. Meignier, “An Open-source State-of-the-art Toolbox for Broadcast News Diarization,“ Interspeech, Lyon (France), 25-29 Aug. 2013.
    - S. Meignier, T. Merlin, “LIUM SpkDiarization: An Open Source Toolkit For Diarization,” in Proc. CMU SPUD Workshop, March 2010, Dallas (Texas, USA).
- BlabberTabber Projects - https://github.com/blabbertabber/blabbertabber
