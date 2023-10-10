# RO_LAB4
Виконати завдання
a)з використанням самостійної реалізації синхронізатора JAVA
б)з стандартної бібліотеки JAVA
с) на мові Golang


а) Завдання про читачів і письменників. Базу даних поділяють два типи процесів - читачі та письменники. Читачі виконують транзакції, які переглядають записи бази даних, транзакції письменників і переглядають і змінюють записи.
Створити багатопоточний додаток, що працює із загальним файлом.
Для захисту операцій із загальним файлом використовувати блокування читання-запису. Файл містить послідовність записів виду: Ф.І.О.1 - телефон1, Ф.І.О.2 - телефон2 ... Повинні працювати наступні потоки:
1) потоки, що знаходять телефони за вказаним прізвищем;
2) потоки, на які ходять П.І.Б. за вказаним телефоном;
3) потоки, що видаляють і додають записи в файл.
 
б) Питання про сад. Створити багатопоточний додаток, що працює із загальним двомірними масивом. Для захисту операцій з загальним масивом використовувати блокування читання-запису. Двовимірний масив описує сад. У додатку повинні працювати такі потоки:
1) потік-садівник стежить за садом і поливає зів'ялі рослини;
2) потік-природа може довільно змінювати стан рослин;
3) потік-монітор1 періодично виводить стан саду в файл (не стираючи попередній стан );
4) потік-монітор2 виводить стан саду на екран.
 
с) Питання про автобус. Створити багатопоточний додаток, що працює із загальним графом.
Для захисту операцій з графом використовувати блокування читання-запису.
Граф описує множину міст і множину рейсів автобусів від міста А до міста Б із зазначенням ціни квитка (за замовчуванням, якщо рейс від А до Б, він йде і від Б до А, з однаковою ціною). У додатку повинні працювати наступні потоки:
1) потік, що змінює ціну квитка;
2) потік, що видаляє і додає рейси між містами;
3) потік, що видаляє старі міста і додає нові;
4) потоки, що визначають чи є шлях від довільного міста А до довільного міста Б, і яка ціна такої поїздки (якщо прямого шляху немає, то знайти будь-який шлях з існуючих)
