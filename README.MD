### Configure docker machine local

- Edit hosts file: sudo vi /etc/hosts
- Add a host: ```<your_ip_server>     registry```

- Open docker app on Mac/Windows -> Setting -> Docker Engine
- Add a config to json file:

```
"insecure-registries": [
  "registry:5043"
  ],
```

Note:

- For Ubuntu/Linux no support UI, please search config 'insecure-registries docker for ubuntu'
- Ref: https://docs.docker.com/registry/insecure/

### Build project with Gradle command

```
cd <path>/prime_be
gradle clean
gradle build
```

- Note: Build project before build docker.

### Create images and push to registry server

+ Eureka server:

```
Go to directoy: cd prime-eureka-server
Build image: docker build . -t prime-eureka
Tag image: docker image tag prime-eureka registry:5043/prime-eureka
Push image to registry server: docker push -a registry:5043/prime-eureka
```

- Note: For case cache local please run command before:

```
Delete image local: docker rmi --force prime-eureka
Build image with no cache:  No cache: docker build . -t prime-eureka --no-cache
```

+ Api gateway:

```
Go to directoy: cd prime-api-gateway
Build image: docker build . -t prime-gateway
Tag image: docker image tag prime-gateway registry:5043/prime-gateway
Push image to registry server: docker push -a registry:5043/prime-gateway
```

- Note: For case cache local please run command before:

```
Delete image local: docker rmi --force prime-gateway
Build image with no cache:  No cache: docker build . -t prime-gateway --no-cache
```

+ User service:

```
Go to directoy: cd prime-user-service
Build image: docker build . -t prime-user
Tag image: docker image tag prime-user registry:5043/prime-user
Push image to registry server: docker push -a registry:5043/prime-user
```

- Note: For case cache local please run command before:

```
Delete image local: docker rmi --force prime-user
Build image with no cache:  No cache: docker build . -t prime-user --no-cache
```

+ Auth service:

```
Go to directoy: cd prime-auth-service
Build image: docker build . -t prime-auth
Tag image: docker image tag prime-auth registry:5043/prime-auth
Push image to registry server: docker push -a registry:5043/prime-auth
```

- Note: For case cache local please run command before:

```
Delete image local: docker rmi --force prime-auth
Build image with no cache:  No cache: docker build . -t prime-auth --no-cache
```

+ Core service:

```
Go to directory: cd prime-core-service
Build image: docker build . -t prime-core
Tag image: docker image tag prime-core registry:5043/prime-core
Push image to registry server: docker push -a registry:5043/prime-core
```

- Note: For case cache local please run command before:

```
Delete image local: docker rmi --force prime-core
Build image with no cache:  No cache: docker build . -t prime-core --no-cache
```

### Deploy on server develop
- Connect to server dev: ssh <user_name@your_ip_server>
- Go to directory: cd /home/test/prime/docker
- Get all images latest from the registry server: ```docker-compose pull```

#### Deploy for all service

- Deploy all services: ```docker-compose up -d```
- Down all services: ```docker-compose down -v```

#### Deploy for one service

- Deploy one service:
  ```
  docker-compose up -d <name_service>
  
  Example:
      docker-compose up -d auth-service
      docker-compose up -d core-service
      docker-compose up -d user-service
      docker-compose up -d api-gateway
      docker-compose up -d eureka
  ```

#### Stop/Start/Restart service:

```
docker-compose start/stop/restart <name_service>

Example:
    docker-compose start auth-service
    docker-compose start core-service
    docker-compose start user-service
    docker-compose start api-gateway
    docker-compose start eureka
```  

### Show log docker-compose

- Show log one service command: ```docker-compose logs <name-of-service>```
- Show all services command: ```docker-compose logs```

```
Usage: logs [options] [SERVICE...]
Options:

--no-color Produce monochrome output.
-f, --follow Follow log output.
-t, --timestamps Show timestamps.
--tail="all" Number of lines to show from the end of the logs for each container
```

- Example:

```
docker-compose logs -t -f auth-service
docker-compose logs -t -f core-service
docker-compose logs -t -f api-gateway
docker-compose logs -t -f eureka
```

Note: Fix liquibase deploy error check sum

```
update databasechangelog set md5sum=null where filename like '%db/migration/changelog.xml';
```