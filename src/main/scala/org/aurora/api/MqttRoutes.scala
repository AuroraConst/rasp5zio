package org.aurora.api
import zio._
import zio.http._

import org.aurora.mqttclient.controllers.scenes.{MasterBedroomSceneController, GarageBikeChargerSceneController}
import org.aurora.mqttclient.controllers.BikePlugControl


object MqttRoutes:
  val mqqtapp = Routes(
  Method.GET / "masterlightson" -> handler{ MasterBedroomSceneController.on;Response.text("Master lights on!") },
  Method.GET / "masterlightsoff" -> handler{ MasterBedroomSceneController.off;Response.text("Master lights off!") },
  Method.GET / "pluggaragebikeon" -> handler{ GarageBikeChargerSceneController.on;Response.text("Garage bike charger on!") },
  Method.GET / "pluggaragebikeoff" -> handler{ BikePlugControl.turnOff;Response.text("Garage bike charger off!") },
  Method.GET / "pluggarageauto" -> handler{BikePlugControl.startCharging(4);Response.text("auto charge at 4:00 a.m.") }
  ).sandbox 

