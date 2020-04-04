FROM alpine:3.11.5

RUN apk --no-cache add \
    openjdk11-jre

WORKDIR /usr/app

COPY far-server-app/target/far*.jar /usr/app/app.jar

ENTRYPOINT [ \
  "sh", \
  "-c", \
  "java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:$DEBUG_PORT -jar app.jar" \
]
