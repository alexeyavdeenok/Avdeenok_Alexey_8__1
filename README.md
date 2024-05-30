# Сборка приложения с использованием JDK (Java Development Kit)
## Установите JDK:
+ Если у вас его еще нет, загрузите и установите JDK с официального сайта Oracle:
## Скомпилируйте исходный код:
+ Откройте командную строку в папке с кодом (src).
+ Скомпилируйте исходный код с помощью команды javac:
`javac DiningPhilosophers.java Fork.java Philosopher.java`
## Создайте исполняемый JAR-файл:
Создайте JAR-файл с помощью команды jar:
`jar cfm DiningPhilosophers.jar manifest.txt *.class`
## Запуск JAR-файла из командной строки
+ Откройте командную строку:
+ Перейдите в каталог с JAR-файлом:
+ Запустите JAR-файл с помощью команды java:
`java -jar DiningPhilosophers.jar`
