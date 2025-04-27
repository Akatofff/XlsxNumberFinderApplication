# README.md

```markdown
# XLSX Number Finder Service

Сервис для поиска N-ного минимального числа в XLSX файле

## Описание

Сервис предоставляет REST API для:
1. Загрузки XLSX файла с числами в первом столбце
2. Поиска N-ного минимального числа в этих данных
3. Использует эффективный алгоритм Quickselect (O(n) в среднем)

## Технологии

- Java 11
- Spring Boot 2.7.18
- Apache POI 5.2.3 (для работы с Excel)
- SpringDoc OpenAPI 1.7.0 (документация API)

## Требования

- JDK 11+
- Maven 3.6+
- Доступ к порту 8080
   ```
## Установка и запуск

1. Клонировать репозиторий:
   ```bash
   git clone https://github.com/your-repo/xlsx-number-finder.git
   cd xlsx-number-finder


2. Собрать проект:
   ```bash
   mvn clean package
   ```

3. Запустить приложение:
   ```bash
   java -jar target/xlsx-number-finder.jar
   ```

4. Приложение будет доступно по адресу:
   ```
   http://localhost:8080
   ```

## Использование API

### Через Swagger UI
Откройте в браузере:
```
http://localhost:8080/swagger-ui.html
```

### Через cURL
```bash
curl -X POST -F "file=@numbers.xlsx" "http://localhost:8080/api/find-nth-min?n=3"
```

### Формат запроса
- Метод: POST
- URL: `/api/find-nth-min`
- Параметры:
    - `file`: XLSX файл с числами в первом столбце
    - `n`: Порядковый номер минимального числа (1-based)

### Пример ответа
```json
5
```

## Алгоритм работы

1. Чтение чисел из первого столбца XLSX файла
2. Применение алгоритма Quickselect для поиска N-ного минимального числа
3. Возврат результата

## Формат XLSX файла

Файл должен содержать числа в первом столбце:
```
| A  |
|----|
| 10 |
| 5  |
| 8  |
| 3  |
| ...|
```

## Решение проблем

Если возникают ошибки зависимостей:
1. Удалите папку `target` и `.idea`
2. Выполните:
   ```bash
   mvn clean install
   ```
3. В IntelliJ IDEA: File → Invalidate Caches / Restart...
