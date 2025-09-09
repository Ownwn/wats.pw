package com.ownwn.wats;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Checker {
    private static final AtomicLong lastRequest = new AtomicLong(0);
    private static final Path dockerPath = Paths.get("dock").toAbsolutePath();
    private static final String templateName = "Template.java";
    private static final String runName = "RunMe.java";
    private static final String target = "TARGET REPLACEMENT 473882928347567473734";


    @PostMapping("check")
    public String check(@RequestBody String input) {
        if (System.currentTimeMillis() - lastRequest.get() < 2000L) {
            return "STOP SPAMMING!!";
        }
        lastRequest.set(System.currentTimeMillis());

        insertIntoTemplate(input);

        Path targetPath = dockerPath.resolve(runName);

        if (!Files.exists(targetPath)) {
            throw new RuntimeException("Missing template");
        }

        String mountArg = targetPath + ":/app/Main.java";

        Command dockerCommand = buildCommand(mountArg);

        String res = dockerCommand.run(dockerPath.toFile());

        if (res.isBlank()) {
            return "OK!";
        } else {
            return res;
        }
    }

    private void insertIntoTemplate(String content) {
        Path template = dockerPath.resolve(templateName);

        try {
            List<String> newLines = Files.lines(template).map(line -> line.replace(target, content)).toList();
            Files.write(dockerPath.resolve(runName), newLines, StandardCharsets.UTF_8);
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
