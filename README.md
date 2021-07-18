#CRUD Generator

This is a simple generator, which automatically creates the
contoller, domain, dto, repository and controller files.

##Usage
1. Change to that dir, where the MainApp.java can be found
   (src/main/java/group/id/artifact)
2. Start the generator with `java -jar crudgenerator-1.0.jar [Domain]`
   command, where [Domain] is the name of the domain class.
   
The program automatically finds out the package from the current
working directory.
