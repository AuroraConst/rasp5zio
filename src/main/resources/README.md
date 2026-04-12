## Set up postgres in docker

- docker compose up -d
- sets up postgres from docker-compose.yml
- the -d flag runs container in 'detached' mode

-  or --detach flag, Docker starts the container, prints its unique ID, and immediately returns control to your terminal prompt so you can continue running other commands


## Common Debian cli
- hostname -I (show current ip information)
- docker ps -a (list all containers)
- docker start <containername>
- docker stop <containername>
- docker rm <containername>
