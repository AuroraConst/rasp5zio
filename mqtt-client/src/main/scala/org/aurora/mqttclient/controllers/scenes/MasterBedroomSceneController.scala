package org.aurora.mqttclient.controllers.scenes

object MasterBedroomSceneController:
  import org.aurora.mqttclient.utils.Publisher
  import org.aurora.mqttclient.scenes.{MasterBedroomLightsScene, MasterBedroomHeatersScene}
  import org.aurora.mqttclient.datatypes.ScenePayloadId


  def on = Publisher.publish(MasterBedroomLightsScene.topic, MasterBedroomLightsScene.SceneType.On() )
  def off = Publisher.publish(MasterBedroomLightsScene.topic, MasterBedroomLightsScene.SceneType.Off() )
  def dim = Publisher.publish(MasterBedroomLightsScene.topic, MasterBedroomLightsScene.SceneType.Dim() )
  def half = Publisher.publish(MasterBedroomLightsScene.topic, MasterBedroomLightsScene.SceneType.Half() )

    