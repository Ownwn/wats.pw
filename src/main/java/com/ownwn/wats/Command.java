package com.ownwn.wats;

import java.io.File;
import java.io.IOException;

record Command(String name, String... args) {
    public static String runCommand(Command command, File directory) {
        try {
            var v = new ProcessBuilder(command.getCommand()).directory(directory).start().getErrorStream().readAllBytes();
            return new String(v);

        } catch (IOException e) {
            throw new RuntimeException("Exception running command" + command);
        }
    }

    public String[] getCommand() {
        String[] cmd = new String[args.length + 1];
        System.arraycopy(args, 0, cmd, 1, args.length);
        cmd[0] = name;
        return cmd;
    }

    @Override
    public String toString() {
        return name + " " + String.join(" ", args);
    }

    public String run(File directory) {
        return runCommand(this, directory);
    }
}