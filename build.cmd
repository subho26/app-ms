mvn -q -DskipTests=true -am -pl %1 package && docker-compose rm -fs %2 && docker-compose up --build -d %2