1. Сборка проекта.
Должен быть установление Maven версии не ниже 3.4
В командной строке в папке проекта выполнить mvn package

2. Запуск проекта
В командной строке в папке с проектом, в папке target выполнить java -jar demo-abs-0.0.1-SNAPSHOT.jar
Приложение запустится на порту 8080

Rest запросы:
/account_operation/transfer - перевести деньги со счета на счет
/account_operation/put_money - положить деньги на счет
/account_operation/take_money - взять деньги со счета
/account_operation/block - заблокировать счет