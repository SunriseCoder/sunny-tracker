# Sunny-Tracker
Sunny-Tracker is an application tracker to manage issues. Regads tree view it's much easier to monitor the state of development and release date estimation.

## Build and Deployment:

Use following commands to manage project build:

* `mvn clean install` to make development build
* `mvn clean install -Puat` to make a build for UAT environment (with special application.properties file)
* `mvn clean install -Pprod` to make a build for production environment (with special application.properties file)

Note: **tomcat7-maven-plugin** deploys application during **install** phase, therefore if You need only to build a war-file without deployment - use `mvn clean package -P<profile>` instead.

## Liquibase usage:

Use following commands to manage database:

* `mvn liquibase:status` to see the current state of the changelog against real database state
* `mvn liquibase:update` to apply all new changes from the changelog to the database
* `mvn liquibase:rollback -Dliquibase:rollbackCount=1` to revert changes by last changeset
* `mvn liquibase:diff` to generate a changelog between Entity Model and the database (useful for development)
