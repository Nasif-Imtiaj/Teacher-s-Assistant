package com.ni.ui.screens.home

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel : ViewModel() {
   fun logout(){
       FirebaseAuth.getInstance().signOut()
   }
}