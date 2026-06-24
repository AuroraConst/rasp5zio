package org.aurora.mqttclient.controllers.scenes

object GarageBikeChargerSceneController:
  import org.aurora.mqttclient.utils.Publisher
  import org.aurora.mqttclient.scenes.{GarageBikeChargerScene}
  import org.aurora.mqttclient.datatypes.ScenePayloadId


  def on = Publisher.publish(GarageBikeChargerScene.topic, GarageBikeChargerScene.SceneType.On() )
  def off = Publisher.publish(GarageBikeChargerScene.topic, GarageBikeChargerScene.SceneType.Off() )

    