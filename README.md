## playwright-groovy-starter

Test automation project boilerplate including packages structure, reporting, logging, multi-env run configuration for WEB and API test automation.

Pure Playwright APIs wrapped a little to add more logs/traceability to test execution and to make theirs usage less-verbose in tests.

### Project Structure

- `src/main/../core`  - project-agnostic code, common for any product/project to be automated
- `src/main/../project`  - project-specific base code, including objects and utils for the particular project (pageobjects for web, services for api, utils, datagenerators, etc.)
- `src/test/` - project tests, grouped by directories, components, etc. also test config definition is there (base urls, etc.)

### Quick Start

#### Run Tests

```shell
./mvnw clean test -Dsuite-name=httpbin -DENV=dev
```

```shell
./mvnw clean test -Dsuite-name=booking -DENV=dev -Dplaywright-browser=chrome -Dheadless=true -Dthread-count=3
```

#### Generate/Show Report

```shell
./mvnw allure:serve
```

### Other Implementations

- [playwright-typescript-starter](https://github.com/daroshchanka/playwright-typescript-starter)