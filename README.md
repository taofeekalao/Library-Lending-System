# cs5031-p2 Library Application

## Introduction
This project is a RESTful API application with a web-based user interface component designed for library management, enabling librarians to efficiently manage books and members entities in a library management system. It facilitates borrowing and returning of books for registered members. The system adopts a 3-tier architecture using the MVC pattern, utilizing Java with the Spring framework for the backend to provide the RESTful APIs. The frontend is implemented using TypeScript and React for a dynamic user interface. During development, we utilized the H2 database for data storage.

## Development
Ensure you have Maven version 3.8.1 or higher and Java JDK 17 installed for system development.

### Run the Back-End (Spring Application)
```shell
# Check if Java is installed
java -version

# Check if Maven is installed
mvn -version 

# Install the dependencies
mvn clean install 

# Start the back-end
mvn spring-boot:run 
```

#### Run the front-end (React Application)
Ensure Node.js version 20.10.0 or higher and npm version 10.5.0 or higher are installed.

```shell
# check if node is installed
node -v

# check if npm is installed
npm -v

# move to the front-end directory
cd frontend

# install the dependencies
npm install

# start the front-end
npm start 
```

## Deployment
#### Database Initialization
For production deployment, you must set up a database to persist data. We recommend SQLite, MySQL, or PostgreSQL. Execute the `schema.sql` file to create the database tables, followed by `data.sql` to insert sample data. In addition, configure the database connection details in the `src/main/resources/application.properties` file.

#### Build and Deploy the Back-End
Use mvn clean package to build the Spring application. Copy `target/library-0.0.1-SNAPSHOT.jar` to your server. Start the application with `java -jar library-0.0.1-SNAPSHOT.jar`.

#### Build and Deploy the Front-End
To deploy the front-end, use a web server like Nginx to serve the static files. First, build the front-end with `npm run build`. Then, copy the build directory to your server and configure Nginx to serve these static files. Finally, start the Nginx server.

## Testing
Run unit tests using Maven by executing `mvn test` in the project root. This will execute all JUnit5 tests located in the `src/test` directory.

## Code Collaboration
The project was developed collaboratively following the GitLab Flow. The repository is hosted on [GitLab](https://gitlab.cs.st-andrews.ac.uk/cs5031-p2-group01/cs5031-p2). Each team member used separate branches for each feature or bug fix. Merge requests were created whenever a feature build is completed. We agreed that only the assigned repository owner should merge pull requests to the `main` branch.