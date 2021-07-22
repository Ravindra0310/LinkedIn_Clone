
<h1 align="center">LinkedIn_Clone</h1>

<p align="center">  
We have create a clone of Linkedin App based on MVVM architecture. The app allows users to connect with people socialy, the app uses firebase for the backend.
</p>
</br>
<p align="center">
<img src="/Screenshots/New Project.png"/>
</p>

 

<img src="/screenshots/appworking.gif" align="right" width="32%"/>


## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Firebase](https://firebase.google.com/) - for user authentication & realtime database for storing and retrieving data. 
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- JetPack
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - compose - Modern Android UI toolkit.
- Architecture
  - MVVM Architecture (View - ViewBinding - ViewModel - Model)
  - Repository pattern
- [Glide](https://github.com/bumptech/glide) - loading images.
</br>
</br>
</br>
</br>
</br>


## Architecture
Book Parking is based on MVVM architecture and repository pattern.
![architecture](/Screenshots/architecture.png)

## Getting Started

1. [Add Firebase to your Android Project.](https://firebase.google.com/docs/android/setup)
2. Copy the google-services.json file you just downloaded into the app/ or mobile/ directory of your Android Studio project.
3. Select the Auth panel and then click the Sign In Method tab.
4. Click Google and turn on the Enable switch, then click Save
 
