# RecruitmentTasksJuniorJavaProgrammer

1. StatisticsElectricityImbalanceHtml
2. StatisticsElectricityImbalanceXls
3. O3ProductionValue
4. StormWindSpeed
5. SQL commands (below)

#O3ProductionValue
#Selenium was used to complete a task
#To succesfully open the project u have to use Google Chrome with downloaded latest version of your browser driver.
#chromedriver.exe should be placed into the project.

#SQL commands
SELECT ID, ContactName, City, Country, CountryAmount, Type, OrderID, CustomerID, OrderDate
FROM customers JOIN orders 
ON ID = CustomerID;

UPDATE customers SET Type='corn' WHERE id='2';

SELECT SUM(CountryAmount) FROM customers WHERE Country = 'UK';
