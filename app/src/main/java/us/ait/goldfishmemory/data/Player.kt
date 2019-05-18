package us.ait.goldfishmemory.data

data class Player(var uid: String = "",
                  var username: String = "",
                  var bestTime: Float = 100f,
                  var gamesPlayed: Int = 0,
                  var icon: String = "")