1、 Overview: Introduction
This system is a pharmaceutical supply chain and prescription execution system. Mainly includes drug management, 
patient management, pharmacy management, pharmacy drug distribution, prescription creation and execution process, etc

2、 Features: Core Features
2.1 Patient information management: adding patients, updating patients, deleting patients, and querying patients
2.2 Drug information management: adding drugs, updating drugs, deleting drugs, and querying drugs
2.3 Pharmacy information management: adding pharmacies, updating pharmacies, deleting pharmacies, and querying pharmacies
2.4 Distribution of drugs to pharmacies
2.5 Prescription process management: Create and fulfill prescriptions
2.6 Audit log: Record the log information of each prescription creation, prescription execution, and retrieval log

3、 Installation: Installation Instructions
3.1 JDK17+Maven3.6+Mysql8.0+(recommended Mysql9.3) Git
3.2 Cloning project: git clone https://github.com/jing747556248/pharma.git
3.3 Building project: mvn clean install
3.4 Initialize the database: Execute the database initialization SQL file - pharma-core/src/main/resources/sql/pharma-init.sql,
   Modify the database connection information in application.yaml to your own
3.5 Running the project: The project is a springboot project, which can be imported into the IDE to run
   Alternatively, the project can be packaged in a jar file and run from the command line: java - jar {your_target_directory}\pharma-core-1.0.0.jar

4、 API Documentation: API Interface Description
4.1 Swagger address: http://localhost:8081/swagger-ui/index.html
4.2 Using the Elide framework to automatically generate basic CRUD interfaces
4.3 Automatically defined some interfaces, such as checking contract drugs based on pharmacy ID, creating prescriptions, and executing prescriptions

5、 Systems Design: System Design
5.1 Table diagram: pharma-core/src/main/resources/DB-ER/Table-Diagram.png
5.2 Introducing the low code framework Elide, which eliminates some Controller and Service classes
5.3 All interfaces comply with JSON:API style, define a unified return object, a unified business exception class BizException, an exception error code enumeration class, and separate these public objects into pharmaceutical common packages
5.4 Adopt database optimistic lock to control the concurrent execution of prescriptions, ensuring that drug inventory is updated correctly and prescriptions are executed correctly
5.5 Adopt Spring's Event event mechanism to record audit logs
