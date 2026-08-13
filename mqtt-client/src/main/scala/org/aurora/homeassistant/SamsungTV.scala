package org.aurora.homeassistant
import sttp.client3._
import sttp.model.StatusCode



object HomeAssistantTvController :
  private val haUrl = "http://homeassistant.local:8123/api/services"
  private lazy val haToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiIzOGM0OGNlM2YzYjU0ZjdhOWIxOGYyN2YwZGFhNjU3NSIsImlhdCI6MTc4MzM2MTM0MiwiZXhwIjoyMDk4NzIxMzQyfQ.KKIISdh1PzRDk_pU2tY8MEDN6njB5obXj2DsSqjPk2o"
  private lazy val tvEntityId = "media_player.65_qled_qn65q8faafxzc" //  TV's exact entity ID on Home Assistant
  private lazy val backend = HttpClientSyncBackend()

  def callHaService(domain: String, service: String): Unit = 
    val url = uri"$haUrl/$domain/$service"
    val jsonBody = s"""{"entity_id": "$tvEntityId"}"""

    val response = basicRequest
        .post(url)
        .header("Authorization", s"Bearer $haToken")
        .header("Content-Type", "application/json")
        .body(jsonBody)
        .send(backend)

    response.code match {
        case StatusCode.Ok => println(s"Successfully called $service on $tvEntityId")
        case code => println(s"Failed with code $code. Check your token and entity ID.")
    }

  def turnOn(): Unit = callHaService("media_player", "turn_on")  
  def turnOff(): Unit = callHaService("media_player", "turn_off")
  def toggle(): Unit = callHaService("media_player", "toggle")
  def setVolume(volumeLevel: Int): Unit = 
    val url = uri"$haUrl/media_player/volume_set"
    val jsonBody = s"""{"entity_id": "$tvEntityId", "volume_level": ${volumeLevel / 100.0}}"""

    val response = basicRequest
        .post(url)
        .header("Authorization", s"Bearer $haToken")
        .header("Content-Type", "application/json")
        .body(jsonBody)
        .send(backend)

    response.code match {
        case StatusCode.Ok => println(s"Successfully set volume to $volumeLevel on $tvEntityId")
        case code => println(s"Failed with code $code. Check your token and entity ID.")
    }

