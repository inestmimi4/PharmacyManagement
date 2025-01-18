# Pharmacy Management System

## Overview

The Pharmacy Management System is a Java-based application designed to manage various aspects of a pharmacy, including managing medications, clients, and purchases. The application uses JavaFX for the user interface and MySQL for the database.

## Features

- Add, update, and delete medications
- Manage client information
- Handle purchases of medical devices and medications
- Apply discounts on expiring medications
- Search for medications by name, category, or first letters

## Technologies Used

- Java
- JavaFX
- MySQL
- Maven

## Project Structure

- `src/main/java/com/example/pharmacymanagement`: Contains the main application code
- `src/main/java/com/example/pharmacymanagement/models`: Contains the model classes
- `src/main/java/com/example/pharmacymanagement/services`: Contains the service classes
- `src/main/java/com/example/pharmacymanagement/views`: Contains the JavaFX view classes
- `src/main/resources`: Contains the FXML files for the JavaFX views
- `src/test/java/com/example/pharmacymanagement`: Contains the JUnit test classes

## Setup and Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/ines312692/PharmacyManagement.git
    cd PharmacyManagement
    ```

2. Set up the database:
    - Create a MySQL database named `bibliotheque`.
    - Import the database schema from the `schema.sql` file located in the `resources` directory.

3. Configure the database connection:
    - Update the database connection settings in the `application.properties` file.

4. Build the project using Maven:
    ```sh
    mvn clean install
    ```

5. Run the application:
    ```sh
    mvn javafx:run
    ```

## Running Tests

To run the JUnit tests, use the following command:
```sh
mvn test