package com.example.jobedin.ui.presentation.modelsForDetachingListeners

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference

data class DatabaseRefAndChildEventListener(
    val databaseRef: DatabaseReference,
    val childEventListener: ChildEventListener
)