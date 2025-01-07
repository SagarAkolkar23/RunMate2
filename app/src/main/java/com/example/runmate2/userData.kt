package com.example.runmate2

import android.provider.ContactsContract.CommonDataKinds.Email

data class userData(
    var id : String = "",
    var name : String,
    var photoUrl : String = "",
    var email : String
    )
