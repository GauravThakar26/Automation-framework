# Selenium Java TestNG Framework (Page Object Model)

This is a hybrid test automation framework built using **Selenium WebDriver**, **Java**, **TestNG**, and **Maven**.  
It follows the **Page Object Model (POM)** design pattern and supports **data-driven testing** using Excel.  
Test execution reports are generated using **ExtentReports**.


## Features

- Page Object Model (POM) Design Pattern
- Data-Driven Testing using Excel (Apache POI)
- TestNG Grouping (e.g., Smoke Tests, Regression Tests)
- Dynamic Wait Utilities (Explicit & Fluent Waits)
- ExtentReports Integration
- Git Version Control


## Folder Structure

`src/test/java`

 `base` 			# Base test class

 `tests` 			# Test classes

 `listeners`    	# ExtentReports and TestNG listeners

 `pages`			# Page Object Model (POM) classes

 `utils`        	# Utility classes (e.g., ExcelReader, Waits)

`testng.xml` 	    # TestNG suite configuration

`pom.xml`			# Maven dependencies file

---

## How to Run Tests

1. Open the project in your IDE (e.g., IntelliJ IDEA or Eclipse).  
2. Right-click on `testng.xml`.  
3. Select **Run As TestNG Suite**.

---

## Tech Stack

- Java  
- Selenium WebDriver  
- TestNG  
- Maven  
- Apache POI (Excel)  
- ExtentReports  

---

## Prerequisites

- JDK 8 or above  
- Maven installed and configured  
- Supported IDE (IntelliJ, Eclipse, etc.)

---


## Author

Developed by Gaurav Thakar 
Contributions and suggestions welcome!
