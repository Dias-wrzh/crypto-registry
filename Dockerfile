FROM eclipse-temurin:21-jdk

WORKDIR /app

# Устанавливаем зависимости
RUN apt-get update && apt-get install -y curl git build-essential libssl-dev pkg-config unzip

RUN curl -L https://github.com/foundry-rs/foundry/releases/download/v1.1.0/foundry_v1.1.0_linux_amd64.tar.gz \
  | tar -xz -C /usr/local/bin

ENV PATH="/root/.foundry/bin:$PATH"

# Копируем jar
COPY build/libs/*.jar app.jar

RUN anvil --version
# Запускаем anvil и Spring Boot
CMD bash -c "anvil & \
  for i in {1..20}; do \
    curl -s http://127.0.0.1:8545 > /dev/null && break; \
    echo 'Waiting for anvil...'; sleep 1; \
  done && \
  java -jar app.jar"
