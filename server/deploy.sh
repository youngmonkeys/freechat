projectName=freechat
version=1.0-SNAPSHOT
# ezyfoxServerLocal=
# ezyfoxServerRemote=
# sshCredential=

echo "start deploy $projectName"

echo "start deploy $projectName-common-$version.jar"
scp -p $ezyfoxServerLocal/common/$projectName-common-$version.jar $sshCredential:$ezyfoxServerRemote/common/
echo "deploy $projectName-common-$version.jar done"

echo "start deploy $projectName-plugin"
scp -r $ezyfoxServerLocal/plugins/$projectName-plugin $sshCredential:$ezyfoxServerRemote/plugins
echo "deploy $projectName-plugin done"

echo "start deploy $projectName-app-api-$version.jar"
scp -p $ezyfoxServerLocal/apps/common/$projectName-app-api-$version.jar $sshCredential:$ezyfoxServerRemote/apps/common
echo "deploy $projectName-app-api-$version.jar done"

echo "start deploy $projectName-app-entry"
scp -r $ezyfoxServerLocal/apps/entries/$projectName-app-entry $sshCredential:$ezyfoxServerRemote/apps/entries
echo "deploy $projectName-app-entry done"

echo "start deploy $projectName-zone-settings.xml"
scp -p $ezyfoxServerLocal/settings/zones/$projectName-zone-settings.xml $sshCredential:$ezyfoxServerRemote/settings/zones/
echo "deploy $projectName-zone-settings.xml done"

echo "done all"
