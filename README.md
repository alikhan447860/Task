# AutomationFramework

-> A ready Selenium Automation Framework built with Java 17, Selenium 4.x, TestNG, Maven, and POM design pattern.

-> In this framework, the data has been passed using parameters from config.properties file and kept the scripts separate.

-> In this framework, only web automation is covered since I implemented another framework POC for mobile browser testing.

-> This framework is mature, robust and supports data driven testing, cross browser testing and can be executed across all the 
platforms and devices.

-> BONUS QUESTION - Tried to cover that too but due to the slowness of the game and loading time, didn't execute it. you can find the code for bonus question too.

---

## Tech Stack

| Component       | Technology           |
|----------------|-----------------------|
| Language        | Java 17              |
| Automation      | Selenium 4.x         |
| Test Framework  | TestNG               |
| Build Tool      | Maven                |
| IDE             | Eclipse              |
| Logging         | Log4j2               |
| Reporting       | Extent Reports       |
| Driver Mgmt     | WebDriverManager     |
| Design Pattern  | Page Object Model    |

---

## Project Structure

```
AutomationFramework/
├── src/
│   ├── main/java/
│   │   ├── base/           # BaseTest, BasePage
│   │   ├── config/         # ConfigReader
│   │   ├── constants/      # FrameworkConstants
│   │   ├── driver/         # DriverFactory (ThreadLocal)
│   │   ├── exceptions/     # FrameworkException
│   │   ├── listeners/      # TestListener
│   │   ├── pages/          # Page Objects (LoginPage)
│   │   ├── reports/        # ExtentManager, ExtentReportManager
│   │   └── utilities/      # WaitUtil, ScreenshotUtil, ElementActions, JavaScriptUtil
│   ├── main/resources/     # log4j2.xml
│   ├── test/java/tests/    # Test classes
│   └── test/resources/     # config.properties
├── reports/                # Generated Extent Reports
├── screenshots/            # Captured Screenshots
├── logs/                   # Log4j2 Log Files
├── pom.xml
├── testng.xml
└── README.md
```

---

## Features

- **ThreadLocal WebDriver** - Parallel test execution support
- **Multi-browser support** - Chrome, Firefox, Edge (configurable)
- **Headless mode** - Configurable via config.properties
- **Explicit waits** - No Thread.sleep() used anywhere
- **POM Pattern** - Clean separation of test logic and page interactions
- **Extent Reports** - HTML reports with screenshot attachments on failure
- **Log4j2 Logging** - Comprehensive logging to console and file
- **Screenshot on Failure** - Automatic capture and report attachment
- **Reusable Utilities** - ElementActions, JavaScriptUtil, WaitUtil, ScreenshotUtil
- **Configurable** - All settings in config.properties

---

## How to Run

### Run all tests:

mvn clean test


### Run specific test:

mvn clean test -Dtest=LoginTest


### Run with specific browser:
mvn clean test -Dbrowser=firefox


### Run headless:
mvn clean test -Dheadless=true


---

## Configuration

Edit `src/test/resources/config.properties`:

```properties
browser=chrome
baseUrl=https://www.stumbleguys.com
explicitWait=20
headless=false
```

---

## Reports

After execution, find reports at:
- **Extent Report**: `reports/extent-report.html`
- **Logs**: `logs/automation.log`
- **Screenshots**: `screenshots/`
