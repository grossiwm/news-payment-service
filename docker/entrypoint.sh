service datadog-agent start

export RABBIT_USERNAME=$RABBIT_USERNAME
export RABBIT_PASSWORD=$RABBIT_PASSWORD        
export RABBIT_HOST=$RABBIT_HOST          
export RABBIT_PORT=$RABBIT_PORT      

java -jar /var/quarkus-app/quarkus-run.jar
