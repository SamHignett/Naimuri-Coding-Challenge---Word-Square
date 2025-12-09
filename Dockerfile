FROM openjdk:27-ea-jdk

WORKDIR /app

COPY . .

RUN javac src/*.java

CMD ["java", "-cp", "src", "Main"]