# Blood Bank Management System (Java + PostgreSQL + Swing)

## Project Overview
This is a Java Swing-based GUI application that serves as a Blood Bank Management System. It uses PostgreSQL as the backend database to manage blood donations, patients, hospitals, and blood bank data efficiently.

## Features
- **Donor Management:** Add and view donor details including age, blood group, contact info, and date of birth.  
- **Patient Management:** Register patients along with blood group requirements and associated hospital.  
- **Hospital Directory:** View hospital list with ID, name, and location.  
- **Blood Bank Directory:** View details of blood banks including city, state, and current stock.  
- **Blood Inventory Search:** Check which blood banks have available stock of a specific blood group using data from the `Collects_Blood` table.  

## Technologies Used

| Layer     | Technology            |
|-----------|---------------------|
| Frontend  | Java Swing (GUI)     |
| Backend   | Java (JDBC)          |
| Database  | PostgreSQL           |
| IDE       | IntelliJ IDEA        |

## Database Structure
The PostgreSQL database includes the following tables:

- **Donor** – stores donor details.  
- **Patient** – stores patient requests and blood requirements.  
- **Hospital** – stores hospital details.  
- **Blood_Bank** – stores information about blood banks and their stock.  
- **Donates** – tracks which donor donated how much blood and where.  
- **Collects_Blood** – tracks which hospital collected how much blood of which type from which blood bank.  
