package org.aurora.api
import zio._
import zio.http._

import org.aurora.mqttclient.controllers.scenes.{MasterBedroomSceneController, GarageBikeChargerSceneController}
import org.aurora.mqttclient.controllers.{BikePlugControl, YetiChargerPlugControl,HeaterBathroomPlugControl, HeaterMasterBedroomPlugControl}


object MqttRoutes:
  val mqqtapp = Routes(
  Method.GET / "masterlightson" -> handler{ MasterBedroomSceneController.on;Response.text("Master lights on!")},
  Method.GET / "masterlightsoff" -> handler{ MasterBedroomSceneController.off;Response.text("Master lights off!") },
  Method.GET / "masterlightsdim" -> handler{ MasterBedroomSceneController.dim;Response.text("Master lights dim") },
  Method.GET / "pluggaragebikeon" -> handler{ BikePlugControl.turnOnFor5hours;Response.text("Garage bike charger on!") },
  Method.GET / "pluggaragebikeoff" -> handler{ BikePlugControl.turnOff;Response.text("Garage bike charger off!") },
  Method.GET / "pluggarageauto" -> handler{BikePlugControl.startCharging(4);Response.text("auto charge at 4:00 a.m.") },
  Method.GET / "plugyetichargeron" -> handler{ YetiChargerPlugControl.turnOnFor25Minutes; Response.text("Yeti charger on for 25 minutes!") },
  Method.GET / "plugyetichargeroff" -> handler{ YetiChargerPlugControl.turnOff; Response.text("Yeti charger off!") },
  Method.GET / "plugbathroomheateron" -> handler{HeaterBathroomPlugControl.turnOn; Response.text("Heater On") },
  Method.GET / "plugbathroomheateroff" -> handler{HeaterBathroomPlugControl.turnOff; Response.text("Heater Off") },
  Method.GET / "plugmasterbedroomheateron" -> handler{HeaterMasterBedroomPlugControl.turnOn; Response.text("Heater On") },
  Method.GET / "plugmasterbedroomheateroff" -> handler{HeaterMasterBedroomPlugControl.turnOff; Response.text("Heater Off") }
  ).sandbox 

