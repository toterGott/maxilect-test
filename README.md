# Результат выполнения тестового задания

- Результат задеплоен на http://totergott.pro/8084

## Верхнеуровневое описание архитектуры

Приложение состоит из трех компонент:
- Generator - масштабируемый слой, который непосредственно генерирует уникальное значение ID
- Coordinator - вспомогательный сервис, который назначает номер диапазона для каждого инстанса генератора
- Nginx - балансирует нагрузку между инстансами генератора в режиме round-robin

## Описание логики генерации

Исходя из требования горизонтального масштабирования отдельно взятый инстанс генератора генерирует
 не все натуральные числа, а только определенный диапазон. Диапазон имеет фиксированную длину. Номер диапазона назначается сервисом Coordinator для каждого инстанса сервиса Generator на старте приложения. После того, как генератор отдал все ID из выделенного диапазона, он обращается к координатору за ID нового диапазона. Таким образом, увеличивая количество генераторов, мы повышаем общую производительность системы.
 
 ## Используемые технологии
 
- Kotlin
- Spring Boot 2.3.3
- Maven
- [JIB](https://github.com/GoogleContainerTools/jib) - сборка distroless образов
- Ngnix - в качестве балансировщика
- Docker, docker-compose
 
 ## Инструкция по запуску
 
 1. Сборка проекта
     ```
    mvn clean install
    ```
 2. Создание docker образов. `jib:dockerBuild` создаст docker и установит в локальный docker. 
    ```
    mvn jib:dockerBuild -f generator/pom.xml
    mvn jib:dockerBuild -f coordinator/pom.xml 
    ```
 3. Запуск docker-compose. Данная конфигурация настроена на балансировку между двумя инстансами генератора.
     ```
     docker-compose up --scale generator=2
    ```
 4. Для наглядного теста можно использовать скрипт: 
     ```
    while true; do; curl localhost:8084 >> request.log; echo "" >> request.log; done;
    ``` 
    Прервать выполнение через несколько секунд и посмотреть результат в файле `request.log`
