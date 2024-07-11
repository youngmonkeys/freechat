#export EZYFOX_SERVER_HOME=
mvn -pl . clean install
mvn -pl freechat-common -Pexport clean install
mvn -pl freechat-app-api -Pexport clean install
mvn -pl freechat-app-entry -Pexport clean install
mvn -pl freechat-plugin -Pexport clean install
cp freechat-zone-settings.xml $EZYFOX_SERVER_HOME/settings/zones/
