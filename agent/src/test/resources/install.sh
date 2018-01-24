#!/bin/bash
echo "Welcome to install the EAPA Client."

var_server_type=` cat << EOF
You can choose the below server type: \n
 1): tomcat (default) \n
EOF
`
echo -e $var_server_type
echo -n "Please choose the server type:"
read serverType 

if [ "$serverType" = "" ]
then
  serverType="tomcat"
fi


agentOpts=" -javaagent:"`pwd`/agent.jar

function installTomcat
{
  
  javaOpts='JAVA_OPTS=$JAVA_OPTS'"\" $agentOpts\""
  echo "JavaOpts: $javaOpts"
  tomcatHome=$1
  if [ "$tomcatHome" = "" ]
  then
     tomcatHome="."
  fi
  startupscript="$tomcatHome/bin/catalina.sh"
  echo "The tomcat home is $tomcatHome"
  
  if [ ! -e "$startupscript" ] 
  then
      echo "error: Can't found the tomcat startup script under $startupscript, please check the input tomcat home path !!!!"
      exit 1
  fi
  
  sed -i '/cygwin=false/i\'"$javaOpts" $startupscript
}

case $serverType in 
 tomcat)
  echo "The installation of EAPA will be continue...."
  echo -n "Please enter the tomcat home path:"
  read tomcat_home
  installTomcat $tomcat_home
  ;;
 *)
  echo "Your typed serverType is not be supported now!"
  ;; 
esac

echo "Installed Successfully. Thank You for installing EAPA!!!"
