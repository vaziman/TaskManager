

#FROM openjdk:21-slim
#
#EXPOSE 8080
#
#COPY target/TaskManager-0.0.1-SNAPSHOT.jar /app/TaskManager.jar
#
#WORKDIR /app
#
#CMD ["java", "-jar", "TaskManager.jar"]

# Используем образ с Java 21
FROM openjdk:21-jdk-slim

# Устанавливаем Maven
RUN apt-get update && apt-get install -y maven

# Копируем файлы в контейнер
COPY . /app

# Устанавливаем рабочую директорию
WORKDIR /app

# Указываем порт, который будет использовать ваше приложение
EXPOSE 8080

# Запускаем приложение
CMD ["java", "-jar", "target/TaskManager-0.0.1-SNAPSHOT.jar"]