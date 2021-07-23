package hu.progmasters.crudgenerator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar crudgenerator-1.4.jar [DomainName]");
            System.exit(1);
        }

        String packageName = determinePackage();
        if (packageName.length() == 0) {
            System.out.println("Can't determine package name. Please change the current directory to the MainApp.java");
            System.exit(2);
        }

        String domainName = toUpCase(args[0]);
        String variableName = toLoCase(domainName);

        List<Template> templates = new ArrayList<>(List.of(
                new Template("Controller", "controller", domainName + "Controller"),
                new Template("Domain", "domain", domainName),
                new Template("Command", "dto", domainName + "Command"),
                new Template("Info", "dto", domainName + "Info"),
                new Template("Service", "service", domainName + "Service"),
                new Template("Repository", "repository", domainName + "Repository")
        ));

        try {
            for (Template template : templates) {

                if (!Path.of(template.getResultPath()).toFile().exists()) {
                    Files.createDirectories(Path.of(template.getResultPath()));
                }

                InputStream contentStream = Main.class.getResourceAsStream(
                        "/templates/" + template.getTemplateName() + "Template.java");

                if (contentStream != null) {
                    String content = new Scanner(contentStream, StandardCharsets.UTF_8)
                            .useDelimiter("\\A")
                            .next()
                            .replace("{PACKAGE}", packageName)
                            .replace("{DOMAIN}", domainName)
                            .replace("{VARIABLE}", variableName);

                    Files.writeString(
                            Path.of(template.getResultPath(), template.getResultFileName() + ".java"),
                            content);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(3);
        }
        System.out.println("CRUD for "+domainName + " successfully generated!");
    }

    private static String determinePackage() {
        String result = "";

        String packageRoot = "src.main.java.".replace('.', File.separatorChar);
        String workDir = System.getProperty("user.dir");

        int pos = workDir.indexOf(packageRoot);
        if (pos >= 0) {
            result = workDir.substring(pos + packageRoot.length()).replace(File.separatorChar, '.');
        }

        return result;
    }

    private static String toUpCase(String loCase) {
        return loCase.substring(0, 1).toUpperCase() + loCase.substring(1);
    }

    private static String toLoCase(String upCase) {
        return upCase.substring(0, 1).toLowerCase() + upCase.substring(1);
    }
}
