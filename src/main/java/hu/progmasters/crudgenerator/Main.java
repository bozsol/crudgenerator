package hu.progmasters.crudgenerator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    private static final Map<String, String> pluralExceptions = new HashMap<>();
    private static final String CONSONANTS = "bcdfghjklmnpqrstvwxz";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar crudgenerator-1.6.jar [DomainName]");
            System.exit(1);
        }

        String packageName = determinePackage();
        if (packageName.length() == 0) {
            System.out.println("Can't determine package name. Please change the current directory to the MainApp.java");
            System.exit(2);
        }

        String domainName = toUpCase(args[0]);
        String pluralDomainName = toUpCase(pluralize(args[0]));
        String variableName = toLoCase(domainName);
        String pluralVariableName = toLoCase(pluralDomainName);

        List<Template> templates = new ArrayList<>(List.of(
                new Template("Controller", "controller", domainName + "Controller"),
                new Template("Domain", "domain", domainName),
                new Template("Command", "dto", domainName + "Command"),
                new Template("Info", "dto", domainName + "Info"),
                new Template("Service", "service", domainName + "Service"),
                new Template("Repository", "repository", domainName + "Repository"),
                new Template("GlobalExceptionHandler", "exception", "GlobalExceptionHandler"),
                new Template("NotFoundException", "exception", "NotFoundException"),
                new Template("ValidateError", "dto", "ValidateError"),
                new Template("GeneralLogger", "log", "GeneralLogger"),
                new Template("Beans", "config", "Beans")
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
                            .replace("{PL_DOMAIN}", pluralDomainName)
                            .replace("{VARIABLE}", variableName)
                            .replace("{PL_VARIABLE}", pluralVariableName);

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

    private static String pluralize(String singular) {
        if (pluralExceptions.size() == 0) {
            setupPluralExceptions();
        }

        String loCaseSingular = singular.toLowerCase();
        if (pluralExceptions.containsKey(loCaseSingular)) {
            return pluralExceptions.get(loCaseSingular);
        }

        boolean preceedingLetterIsConsonant = CONSONANTS.contains(singular.subSequence(singular.length()-2, singular.length()-1));

        // Handle ending with "o" (if preceeded by a consonant, end with -es, otherwise -s: Potatoes and Radios)
        if (singular.endsWith("o") && preceedingLetterIsConsonant) {
            return singular + "es";
        }

        // Handle ending with "y" (if preceeded by a consonant, end with -ies, otherwise -s: Companies and Trays)
        if (singular.endsWith("y") && preceedingLetterIsConsonant) {
            return singular.substring(0, singular.length() - 1) + "ies";
        }

        // Ends with a whistling sound: boxes, buzzes, churches, passes
        if (singular.endsWith("s") ||
                singular.endsWith("sh") ||
                singular.endsWith("ch") ||
                singular.endsWith("x") ||
                singular.endsWith("z"))
        {
            return singular + "es";
        }

        return singular + "s";
    }

    private static void setupPluralExceptions() {
        pluralExceptions.put("person", "people");
        pluralExceptions.put("trash", "trash");
        pluralExceptions.put("life", "lives");
        pluralExceptions.put("man", "men");
        pluralExceptions.put("woman", "women");
        pluralExceptions.put("child", "children");
        pluralExceptions.put("foot", "feet");
        pluralExceptions.put("tooth", "teeth");
        pluralExceptions.put("dozen", "dozen");
        pluralExceptions.put("dwarf", "dwarves");
        pluralExceptions.put("hundred", "hundred");
        pluralExceptions.put("thousand", "thousand");
        pluralExceptions.put("million", "million");
        pluralExceptions.put("datum", "data");
        pluralExceptions.put("criterion", "criteria");
        pluralExceptions.put("analysis", "analyses");
        pluralExceptions.put("fungus", "fungi");
        pluralExceptions.put("index", "indices");
        pluralExceptions.put("matrix", "matrices");
        pluralExceptions.put("settings", "settings");
        pluralExceptions.put("mouse", "mice");
        pluralExceptions.put("goose", "geese");
        pluralExceptions.put("ox", "oxen");
        pluralExceptions.put("sheep", "sheep");
        pluralExceptions.put("fish", "fish");
    }
}
