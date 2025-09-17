package com.ownwn.wats;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.*;

record Command(String name, String... args) {
    private static final int timeout = 5;
    public static String runCommand(Command command, File directory) throws ExecutionException, InterruptedException, IOException {

        Process process = new ProcessBuilder(command.getCommand()).directory(directory).start();

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<String> stdErr;
        try {
            stdErr = executor.submit(new StreamReader(process.getErrorStream()));
        } finally {
            executor.shutdownNow();
        }


        boolean done = process.waitFor(timeout, TimeUnit.SECONDS);

        if (!done) { // probs infinite loop
            process.destroyForcibly();
            return "Time limit exceeded. Likely infinite loop!";
        }
        return stdErr.get();
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
        try {
            return runCommand(this, directory);
        } catch (Exception e) {
            return "Error running command!\n" + e;
        }
    }

    private record StreamReader(InputStream input) implements Callable<String> {
        @Override
        public String call() throws IOException {
            return new String(input.readAllBytes());
        }
    }
}