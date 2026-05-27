# resources/README.md

# Important Notes
- Starlink uses CGNAT (Carrier-Grade NAT)Starlink  so port forwarding from my router is impossible
- I need to use Cloud Flair or Tailscale to tunnel

## Tailscale
- https://login.tailscale.com/admin/machines
## Note for DNS naming, look at D-Link configuration of names

### Zio Rasp5 app
- [Hello](http://mypi5.local:8080/hello)
- [View static resources](http://mypi5.local:8080/static)

### Gateway D-Link Eagle Pro Model R18
- [Front End](http://r18-2cfb.local)

### PiHole on raspberry pi 4 (where home assistant is installed as well)
- [Front End](http://homeassistant.local:80/admin/login)

### Home Assistant on raspberry pi 4/
- [Front End](https://homeassistant.local:8123/admin)

### Mosquitto (MQTT BROKER) on mypi5.local
- no front end
- check if service is running
`sudo systemctl is-active mosquitto`
- or `sudo systemctl status mosquitto`
- or check the Network Port
`netstat -tln | grep 1883`

### Zigbee2Mqtt
- [Front End](http://mypi5.local:9090/)
- note that the Zigbee Dongle is attached to *homeassistant.local* using *ser2net* on port 20108 (see configuration.yaml)
- config file is in */opt/zigbee2mqtt/data/configuration.yaml*

### Plex
  [Front End](http://raspnas.local:32400/)

## General Notes:
### Common Debian cli
- `hostname -I` (show current ip information)
- `docker ps -a` (list all containers)
- `docker start <containername>`
- `docker stop <containername>`
- `docker rm <containername>`
- `free -h` see RAM space
- `df -h` (disk free)s ee drives and space
- `rsync -av --remove-source-files ./srcfileordir  ../destination`
  1. -a archive (keep permissions) 
  2. -v verbose

### Set up postgres in docker
`docker compose up -d`
- sets up postgres from docker-compose.yml
- the -d flag runs container in 'detached' mode
-  or --detach flag, Docker starts the container, prints its unique ID, and immediately returns control to your terminal prompt so you can continue running other commands

### tmux
- `tmux ls` lists all tmux sessions
- `ctrl+b d` keyboard shortcut to detach from current tmux session
- `tmux attach -t <name>` attach to specific terminal session (see `tmux ls`)

### configuring network devices with cli
> sudo nmtui
- (network management tool ui) this is where I configured static IP addresses