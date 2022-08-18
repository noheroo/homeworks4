# homeworks4
* homework 4.1. Реализован весь функционал:
  - Возможность получить количество всех студентов в школе.
  - Возможность получить средний возраст студентов.
  - Возможность получать только пять последних студентов.
  - Добавлен метод для вывода список аватаров постранично.
* homework 4.2. Реализован весь функционал:
  - Создан файл scripts421.sql. В нем находятся запросы для создания ограничений.
  - Создан файл scripts422.sql. В нем находятся запросы для создания таблиц.
  - Создан файл scripts423.sql. В нем находятся два JOIN-запроса (т.к. ко второму запросу нет никакой конкретики по выводимым полям, то выводится все данные из таблицы avatars и name из students).
* homework 4.3. Реализован весь функционал:
  - Подключена зависимость liquibase-core. Создан пустой файл миграций. Настроен файл changelog-master.yml.
  - В файле миграций созданы два changeset с SQL-командами для создания индексов.
* homework 4.4. Реализован весь функционал:
* homework 4.5. Реализован весь функционал:
  - Создан эндпоинт, который возвращает отсортированные в алфавитном порядке имена всех студентов в верхнем регистре, чье имя начинается на букву ~~А~~ на любую введеную букву, игнорирую регистр.
  - Создан эндпоинт, который возвращает средний возраст всех студентов.
  - Создан эндпоинт, который возвращает самое длинное название факультета.
  - Создан эндпоинт (в StudentController), который возвращает целочисленное значение. Это значение вычисляется следующей формулой:int sum = Stream.iterate(1, a -> a +1) .limit(1_000_000) .reduce(0, (a, b) -> a + b ). т.к. сложно оценить время затраченное на параллельную обработку и последовательную (время то одинаковое, то паралельная обработка быстрее, то последовательная, в общем плохой пример), предоставлены обе реализации с выводом времени в лог.
