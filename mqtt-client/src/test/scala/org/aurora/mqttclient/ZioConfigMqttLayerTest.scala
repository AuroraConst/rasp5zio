package org.aurora.mqttclient

import org.scalatest._
import wordspec._
import matchers._

class ZioConfigMqttLayerTest extends AnyWordSpec with should.Matchers:
  // 1. Define the Service Interface

  import zio.*
  import org.eclipse.paho.client.mqttv3.MqttConnectOptions
  import org.eclipse.paho.client.mqttv3.{IMqttClient, MqttClient}

  trait MqttClientService :
    val address: UIO[String]
    val publisherId: UIO[String]
    val subscriberId: UIO[String]
    protected lazy val options  = new MqttConnectOptions();
      options.setAutomaticReconnect(true);
      options.setCleanSession(true);
      options.setConnectionTimeout(10);




  // 2. Provide the Live Implementation
  object MqttClientService :
    val live: ZLayer[Any, Nothing, MqttClientService] = ZLayer.succeed(new MqttClientService :
      override val address: UIO[String] = 
        ZIO.succeed("tcp://192.168.0.198:1883")
      override val publisherId: UIO[String] =
        ZIO.succeed("publisher-1")
      override val subscriberId: UIO[String] =
        ZIO.succeed("subscriber-1")
    )

    // 3. Optional: Convenience accessor methods for easier usage
    def address(): URIO[MqttClientService, String] = 
      ZIO.serviceWithZIO[MqttClientService](_.address)

    def publisherId(): URIO[MqttClientService, String] =
      ZIO.serviceWithZIO[MqttClientService](_.publisherId)

    def subscriberId(): URIO[MqttClientService, String] =
      ZIO.serviceWithZIO[MqttClientService](_.subscriberId)
