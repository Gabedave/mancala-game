FROM openjdk:21-jdk as build
WORKDIR /workspace/app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
FROM openjdk:21-jdk
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
CMD [ "cp", "/etc/secrets/.env", "/app/.env" ]
ENTRYPOINT ["java","-cp","app:app/lib/*","com.gabedave.mancalagame.MancalaGameApplication"]