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
            System.out.println("Usage: java -jar crudgenerator.jar [DomainName]");
            System.exit(1);
        }

        String domainPackage = determinePackage();
        if (domainPackage.length() == 0) {
            System.out.println("Can't determine package name. Please change the current directory to the MainApp.java");
            System.exit(2);
        }

        String domain = toUpCase(args[0]);
        String variable = toLoCase(domain);

        List<Template> templates = new ArrayList<>(List.of(
                new Template("Controller", "controller", domain + "Controller"),
                new Template("Domain", "domain", domain),
                new Template("Command", "dto", domain + "Command"),
                new Template("Info", "dto", domain + "Info"),
                new Template("Service", "service", domain + "Service"),
                new Template("Repository", "repository", domain + "Repository")
        ));

        try {
            for (Template template : templates) {

                if (!Path.of(template.getResultPath()).toFile().exists()) {
                    Files.createDirectory(Path.of(template.getResultPath()));
                }

                InputStream contentStream = Main.class.getResourceAsStream(
                        "/templates/" + template.getTemplateName() + "Template.java");

                if (contentStream != null) {
                    String content = new Scanner(contentStream, StandardCharsets.UTF_8)
                            .useDelimiter("\\A")
                            .next()
                            .replace("{PACKAGE}", domainPackage)
                            .replace("{DOMAIN}", domain)
                            .replace("{VARIABLE}", variable);

                    Files.writeString(
                            Path.of(template.getResultPath(), template.getResultFileName() + ".java"),
                            content);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(2);
        }
    }

    private static String determinePackage() {
        String result = "";

        String root = "src.main.java.".replace('.', File.separatorChar);
        String wd = System.getProperty("user.dir");

        int pos = wd.indexOf(root);
        if (pos >= 0) {
            result = wd.substring(pos + root.length()).replace(File.separatorChar, '.');
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
