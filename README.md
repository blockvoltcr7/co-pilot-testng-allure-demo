# AI Bug Report Generation in Test Automation

This project demonstrates the use of the Allure TestNG framework in a Java-based testing environment, with a special focus on the innovative feature of generating automated bug reports and email notifications. Utilizing AI technologies through LangChain4J and OpenAI’s GPT-4, the system analyzes test outcomes and automatically crafts detailed bug reports and corresponding emails. This integration significantly enhances test analysis and reporting capabilities, streamlining communications within development teams.

![System Architecture](https://github.com/blockvoltcr7/co-pilot-testng-allure-demo/blob/edj-demo/src/test/resources/images/diagram-export-5-16-2024-11_39_46-PM.png "System Architecture Diagram")

## Project Structure

- **src/test/java/AllureTest/**:
  - **`BeforeAndAfterSetup.java`**: Manages setup and teardown methods for the test suite.
  - **`EmployeesDBTest.java`**: Conducts tests to verify department data in the employees database.
  - **`DeviceCapacityAPITest.java`**: Ensures accuracy in the reported capacity of promotional devices.
  - **`StockGlobalQuotesTest.java`**: Verifies Cusip presence in global quotes API responses.

- **src/test/resources/**:
  - Houses essential resources for testing, including JSON files for bug reports, email templates, and prompts for report generation using OpenAI's GPT models.

## Key Features

- **Enhanced Test Reporting**: Utilizes Allure annotations to enrich test documentation.
- **API Response Logging**: Captures and logs API responses within Allure reports for in-depth analysis.
- **Automated Report Generation**: Integrates LangChain4J and OpenAI's GPT-4 API during the `afterSuite` method to analyze test results and automatically generate detailed bug reports and emails.

## Setup and Configuration

1. **Prerequisites**:
   - Java 8 or higher.
   - Maven to manage dependencies and execute tests.
   - Allure
   - PostGresSQL
     

2. **Dependencies**:
   - Allure TestNG for test reporting.
   - RestAssured for API interactions.
   - Jackson for JSON processing.
   - OpenAI API Key

3. **Running Tests and Generating Reports**:
   - Cleans the allure-results directory and executes tests using maven:
     ```
     mvn clean test
     ```
   - Launches the Allure report with:
     ```
     allure serve
     ```  
   - Post-test, the `afterSuite` method uses LangChain4J to call OpenAI's GPT-4 API, which helps in generating and analyzing bug reports.
   - View the report at `target/site/allure-maven-plugin/index.html`.

## Automated Bug Reporting

- After executing tests, LangChain4J communicates with OpenAI's GPT-4 to create comprehensive bug reports and corresponding emails, which are then prepared to send to the development team.
- These documents are stored in `src/test/resources/bug-reports` and `src/test/resources/emails`.

