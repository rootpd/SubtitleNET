#!/bin/bash

SUBTITLENET_HOME="$HOME/SubtitleNET"
SUBTITLENET_JAR="$SUBTITLENET_HOME/subtitlenet.jar"

mkdir -p $SUBTITLENET_HOME
wget https://github.com/rootpd/SubtitleNET/raw/master/subtitlenet.jar -O $SUBTITLENET_JAR

echo "#!/bin/bash
java -jar $SUBTITLENET_JAR \"\$1\"
" > $SUBTITLENET_HOME/subtitlenet.sh

chmod +x $SUBTITLENET_HOME/subtitlenet.sh

if [ ! -f /usr/local/bin/subtitlenet ]; then
    sudo ln -s $SUBTITLENET_HOME/subtitlenet.sh /usr/local/bin/subtitlenet
fi