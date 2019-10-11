#!/usr/bin/env bash

function startRemoteAppServerWildfly18(){
  echo "-----------------------------------------"
  echo "start remote AppServer Wildfly 18"
  echo "-----------------------------------------"
  echo "reminder: hit CTRL-C to stop this Server"
  echo "-----------------------------------------"
  cd ~/j/srv/wildfly-18.0.0.Final/bin
  ./standalone.sh
}

function stopRemoteAppServerGlassfish51(){
  echo "-------------------------------------"
  echo "stop remote AppServer Glassfish 5.1"
  echo "-------------------------------------"
  cd ~/j/srv/glassfish-5.1.0/bin
  ./asadmin stop-domain
  ./asadmin stop-database
}

function startRemoteAppServerGlassfish51(){
  echo "-------------------------------------"
  echo "start remote AppServer Glassfish 5.1"
  echo "-------------------------------------"
  cd ~/j/srv/glassfish-5.1.0/bin
  ./asadmin start-database
  ./asadmin start-domain
  echo "http://localhost:8080/"
}

function stopRemoteAppServerOpenLibertyWlp(){
  echo "-----------------------------------------"
  echo "stop remote AppServer Open Liberty WLP"
  echo "-----------------------------------------"
  cd ~/j/srv/openliberty-19.0.0.9/wlp/bin
  ./server stop
}

function setupRemoteAppServerOpenLibertyWlp(){
  ./mvnw clean install -DskipTests=true
  cp -f src/main/liberty/config/server.xml ~/j/srv/openliberty-19.0.0.9/wlp/usr/servers/defaultServer/
  cp -f target/petclinic/WEB-INF/lib/postgresql-42.2.7.jar ~/j/srv/openliberty-19.0.0.9/wlp/lib/postgresql.jar
}

function startRemoteAppServerOpenLibertyWlp(){
  echo "-----------------------------------------"
  echo "start remote AppServer Open Liberty WLP"
  echo "-----------------------------------------"
  cd ~/j/srv/openliberty-19.0.0.9/wlp/bin
  #tail -f ~/j/srv/openliberty-19.0.0.9/wlp/usr/servers/defaultServer/logs/console.log &
  tail -f ~/j/srv/openliberty-19.0.0.9/wlp/usr/servers/defaultServer/logs/messages.log &
  ./server start
  echo "http://localhost:9080/"

}

function startAppServer(){
  #startRemoteAppServerWildfly18
  setupRemoteAppServerOpenLibertyWlp
  startRemoteAppServerOpenLibertyWlp
  #startRemoteAppServerGlassfish51
}

function stopAppServer(){
  stopRemoteAppServerOpenLibertyWlp
  #stopRemoteAppServerGlassfish51
}

function main() {
  startAppServer
  #stopAppServer
}

main