package com.example.proptit_protify.models

import java.io.Serializable

data class Song (
  val title:String,
  val artist:String,
  val image: String,
  val resource: String
): Serializable