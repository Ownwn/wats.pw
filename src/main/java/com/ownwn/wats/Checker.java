package com.ownwn.wats;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Checker {
    private static final AtomicLong lastRequest = new AtomicLong(0);
    private static final Path dockerPath = Paths.get("dock").toAbsolutePath();
    private static final String runName = "RunMe.java";
    private static final String logFile = "logs.txt";
    private static final String target = "TARGET REPLACEMENT 473882928347567473734";


    @PostMapping("check")
    public String check(@RequestBody String input, String id) {
        if (input == null || id == null || input.length() > 1500 || id.length() > 1500) {
            return "Bad request";
        }

        int questionId;
        try {
            questionId = Integer.parseInt(id);
        } catch (Exception e) {
            return "Bad request";
        }

        if (System.currentTimeMillis() - lastRequest.get() < 2000L) {
            return "STOP SPAMMING!!";
        }
        lastRequest.set(System.currentTimeMillis());

        Template template = new Template(questionId, input);
        template.writeIntoTemplateFile();

        Path targetPath = dockerPath.resolve(runName);

        if (!Files.exists(targetPath)) {
            throw new RuntimeException("Missing template");
        }

        String mountArg = targetPath + ":/app/Main.java";

        Command dockerCommand = buildCommand(mountArg);

        writeLog(input);
        String res = dockerCommand.run(dockerPath.toFile());



        if (res.isBlank()) {
            return "OK!";
        } else {
            return res;
        }
    }

    record Template(int id, String userInput) {
        public void writeIntoTemplateFile() {
            String templateCode = findTemplate(id);

            try {
                Files.writeString(dockerPath.resolve(runName), templateCode.replaceFirst(target, userInput));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private String findTemplate(int id) {
            try {
                Class<?> clazz = Class.forName("com.ownwn.wats.problems.Problem" + id);
                Field field = clazz.getDeclaredField("challenge");
                return field.get(clazz).toString();

            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeLog(String input) {
        String logText = System.currentTimeMillis() + "\n" + input + "\n\n============\n";
        try {
            Files.writeString(
                    dockerPath.resolve(logFile),
                    logText,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Command buildCommand(String mountArg) {
        return new Command(
                "docker",
                "run",
                "--rm",
                "-v", mountArg,
                "java-executor",
                "sh",
                "-c",
                "javac Main.java && java -ea Main"
        );
    }
}
