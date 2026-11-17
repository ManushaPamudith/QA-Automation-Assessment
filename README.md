# QA Automation Assessment

A web application login page automation testing project developed using **Selenium WebDriver, Java, TestNG, and Maven**.

This project focuses on automating functional test scenarios for the login functionality of a web application using the Page Object Model (POM) design pattern.

## Technologies Used

- Java
- Selenium WebDriver
- TestNG
- Maven
- ChromeDriver
- Page Object Model (POM)

## Project Structure

```text
QA-Automation-Assessment/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── pages/
│   │       │   └── LoginPage.java
│   │       └── utils/
│   │           └── TestData.java
│   └── test/
│       └── java/
│           ├── base/
│           │   └── BaseTest.java
│           └── tests/
│               └── LoginTests.java
├── pom.xml
├── testng.xml
└── smoke.xml
```

## Automated Test Scenarios

| Test Case | Description |
|---|---|
| TC1 | Verify successful login with valid credentials |
| TC2 | Verify login validation with invalid inputs using TestNG DataProvider |
| TC3 | Verify show/hide password functionality |
| TC4 | Verify Forgot Password navigation |
| TC5 | Verify Remember Me checkbox functionality |

## Test Execution

Run all test cases:

```bash
mvn clean test
```

Run smoke tests:

```bash
mvn clean test -Dsurefire.suiteXmlFiles=smoke.xml
```

## Test Results

Latest execution results:

- **Total:** 8 test executions
- **Passed:** 7
- **Failed:** 0
- **Skipped:** 1

**Note:** The successful login test (TC1) was skipped because Cloudflare CAPTCHA verification blocked automated login. This was documented as an automation environment limitation.

## Key Automation Practices

- Page Object Model (POM) design pattern
- Explicit waits using WebDriverWait
- TestNG annotations and DataProvider
- Reusable test methods
- Test grouping for smoke testing
- Maven-based test execution

## Author

**Manusha Pamudith**

GitHub: [ManushaPamudith](https://github.com/ManushaPamudith)

---

*This project was developed as part of a QA Automation technical assessment.*