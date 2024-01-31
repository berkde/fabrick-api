# FABRICK API DOCS



The Fabrick API Project is a comprehensive Java-based solution designed to seamlessly interact with the Fabrick API, facilitating the retrieval and manipulation of account and transaction information. This project encapsulates various components such as configurations, controllers, entities, exceptions, mappers, models, repositories, services, and utility classes to create a robust and efficient API integration system.

## Key Features:

1. [Configurations](docs/configurations.md)
    - Centralized management of configuration settings for easy customization.

2. [Controllers](docs/controllers.md)
    - Well-organized controllers handling incoming requests and orchestrating API interactions.

3. [Entities](docs/entities.md)
    - Definition and representation of data entities for seamless integration with the database.

4. [Exceptions](docs/exceptions.md)
    - A set of custom exceptions for effective error handling and meaningful error messages.

5. [Mappers](docs/mappers.md)
    - Object mappers to facilitate the conversion between different data structures and formats.

6. [Models](docs/models.md)
    - Clear documentation of data models used throughout the project.

7. [Repositories](docs/repositories.md)
    - Repository classes for interacting with the database, ensuring data persistence.

8. [Services](docs/services.md)
    - Business logic encapsulated in service classes to handle various operations.

9. [Utilities](docs/utils.md)
    - Utility classes providing reusable functions for building URLs, making API calls, and handling JSON data.

## How to Use:

1. **Installation:**
    - Include the provided Maven dependency in your project to integrate the Fabrick API Utilities.

2. **Documentation:**
    - Comprehensive documentation in the "docs" folder, providing insights into various aspects of the project.

3. **Customization:**
    - Easily customize configuration settings and adapt the project to your specific requirements.

4. **API Interactions:**
    - Leverage the utility classes to build URLs, make API calls, and handle JSON data seamlessly.

5. **Error Handling:**
    - Utilize the custom exceptions to gracefully handle errors and provide meaningful feedback to users.

This Fabrick API Integration Project simplifies the integration process, offering a well-structured and extensible foundation for developers to build upon. Whether you are managing financial data, tracking transactions, or extracting account details, this project provides a solid framework for seamless Fabrick API integration within your Java-based applications.

I apologize for any confusion. Here's the corrected guide, emphasizing the need to clone the project to the local environment for development:

# Fabrick API Project Setup Guide

To contribute to and work on the Fabrick API Integration Project, follow these step-by-step instructions:

## Prerequisites:

- **Java 21:** Ensure you have Java 21 installed on your local machine.
- **MySQL 8:** Install and configure MySQL 8 on your local machine.
- **Maven:** Make sure Maven is installed to handle project dependencies.
- **Git:** Install Git for version control.

## Steps:

1. **Clone the Project:**
   ```bash
   git clone https://github.com/bd97it/fabrick-api.git
   ```


2. **Navigate to Project Directory:**
   ```bash
   cd fabrick-api
   ```


3. **Build the Project:**
   ```bash
   mvn clean install test verify
   ```


4. **Set Up the Database:**
   - Configure your MySQL database settings in the `application.properties` file.



5. **Run the Spring Boot App:**
   - Via Maven:
     ```bash
     mvn spring-boot:run
     ```
   - Or using the JAR file:
     ```bash
     java -jar /app/target/fabrickAPI-1.0.0.jar
     ```

6. **Access the API:**
   - Open Postman or Swagger at `<host>:8080/swagger-ui/index.html` to make API endpoint calls.


7. **Run Tests:**
   - Execute the following command to run tests and view results:
     ```bash
     mvn test
     ```

8. **Contributing (Optional):**
   - If you intend to contribute, fork the project on GitHub and clone your forked repository.
   - Set up a remote to the original repository:
     ```bash
     git remote add upstream https://github.com/bd97it/fabrick-api.git
     ```
   - Pull changes from the original repository before pushing your changes:
     ```bash
     git pull upstream master
     ```

---

[![Java CI with Maven](https://github.com/bd97it/fabrick-api/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/bd97it/fabrick-api/actions/workflows/ci-cd.yml)

---

## License

MIT License

Copyright (c) 2024 Berk Delibalta

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

**Note:** Please refer to the actual implementation and configuration files for additional details, as the provided documentation is a high-level overview.