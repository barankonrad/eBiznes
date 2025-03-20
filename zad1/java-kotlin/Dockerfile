FROM ubuntu:22.04

RUN apt-get update && apt-get install -y openjdk-8-jdk \
wget unzip

RUN wget https://services.gradle.org/distributions/gradle-8.13-bin.zip -P /tmp \
 && unzip /tmp/gradle-8.13-bin.zip -d /opt/gradle \
 && rm /tmp/gradle-8.13-bin.zip
ENV PATH="${PATH}:/opt/gradle/gradle-8.13/bin"

RUN wget -q https://github.com/JetBrains/kotlin/releases/download/v1.9.10/kotlin-compiler-1.9.10.zip \
  && unzip kotlin-compiler-1.9.10.zip \
  && mv kotlinc /usr/local/bin \
  && rm kotlin-compiler-1.9.10.zip
ENV PATH="${PATH}:/usr/local/bin/kotlinc/bin"

COPY . /usr/src/app

WORKDIR /usr/src/app

# currently called by docker-compose.yaml
# CMD ["gradle", "run"]