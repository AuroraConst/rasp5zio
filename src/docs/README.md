# docs/site/README.md
- I used Laikahtml from sbt plugin to generate html from markdown files in docs/site

# Important Notes
- Starlink uses CGNAT (Carrier-Grade NAT)Starlink  so port forwarding from my router is impossible
- I need to use Cloud Flair or Tailscale to tunnel

## Tailscale
- https://login.tailscale.com/admin/machines
- note, i had to confure yaml file for zigbee2mqtt to listen to host 0.0.0.0 instead of 192.168.0.200 which means to listen to all network devices (including tailscale's virtual device tailscale0)

## Note for DNS naming, look at D-Link configuration of names


# Main
## Zio Rasp5 app 🍓
- [View static resources](http://mypi5:8080/static)
- [Charge bike overnight](http://mypi5:8080/pluggarageauto)
- [Master Lights On](http://mypi5:8080/masterlightson)
- [Master Lights Off](http://mypi5:8080/masterlightsoff)
- [Master Lights Dim](http://mypi5:8080/masterlightsdim)

## Gateway D-Link Eagle Pro Model R18 🛜
- [Front End](http://r18-2cfb)

## PiHole on raspberry pi 4 (where home assistant is installed as well) 🥧🕳️
- [Front End](http://homeassistant:80/admin/login)

## Home Assistant on raspberry pi 4/
- [Front End](https://homeassistant:8123/admin)

## Mosquitto (MQTT BROKER) on mypi5.local
- no front end
- check if service is running
`sudo systemctl is-active mosquitto`
- or `sudo systemctl status mosquitto`
- or check the Network Port
`netstat -tln | grep 1883`

## Zigbee2Mqtt
- [Front End](http://mypi5:9090/)
- note that the Zigbee Dongle is attached to *homeassistant.local* using *ser2net* on port 20108 (see configuration.yaml)
- config file is in */opt/zigbee2mqtt/data/configuration.yaml*

## Plex
  [Front End](http://raspnas:32400/)

## General Notes:


### configuring network devices with cli
> sudo nmtui
- (network management tool ui) this is where I configured static IP addresses