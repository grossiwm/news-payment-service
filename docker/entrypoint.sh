service datadog-agent start

java -javaagent:/var/dd-java-agent.jar -Ddd.service=news-payment-service \
-Ddd.env=prod -Ddd.logs.injection=true -jar /var/quarkus-app/quarkus-run.jar
