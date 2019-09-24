FROM openjdk:12-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8443/tcp
ENTRYPOINT ["java","-cp","app:app/lib/*","by.vironit.training.basumatarau.messenger.MessengerApp"]