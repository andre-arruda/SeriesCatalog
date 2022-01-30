package com.venice.seriescatalog.view.util

import android.util.Log

/*
 * Copyright (c) 2021 McDonald's. All rights reserved.
 * Created by Andre Arruda on 1/29/22.
 */class ConvertRuntime {

     open fun convertRuntime(minutes: Int) : String {
         return if(minutes < 60) {
             minutes.toString()
         } else {
             var hoursConverted = minutes/60
             var minutesConverted = minutes%60
             hoursConverted.toString() + "h" + minutesConverted.toString() + "m"
         }
     }
}