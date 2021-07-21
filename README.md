# CRUD Generator

This is a simple CRUD generator, which automatically creates the
contoller, domain, dto, repository and controller files based
on the name of the Domain class.

## Build
Just simply write:
```
> mvn clean package
```

## Usage
1. Copy the target/crudgenerator-1.3.jar file into the root folder
   of your package, where the @SpringBootApplication MainApp.java 
   file can be found (src/main/java/group/id/artifact).
2. Open the terminal and change to the previous directory.   
3. Start the generator with the following command:
   ```
   > java -jar crudgenerator-1.3.jar Rabbit
   ```
   where the `Rabbit` is the name of the domain class
4. Insert the fields into the domain, dto/command and dto/info classes
5. You're ready to go!   
