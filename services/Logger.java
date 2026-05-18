package services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static Logger instance;
    private static final String LOG_FILE = "system.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private List<String> inMemoryLog = new ArrayList<>();

    private Logger() {
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String action, String user) {
        String entry = String.format("[%s] %s - %s", LocalDateTime.now().format(FORMATTER), user, action);
        inMemoryLog.add(entry);
        writeToFile(entry);
    }

    public void log(String action) {
        log(action, "SYSTEM");
    }

    private void writeToFile(String entry) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            pw.println(entry);
        } catch (IOException e) {
            System.err.println("Logger error: " + e.getMessage());
        }
    }

    public List<String> getInMemoryLog() {
        return inMemoryLog;
    }

    public void printLog() {
        if (inMemoryLog.isEmpty()) {
            System.out.println("Log is empty.");
            return;
        }
        System.out.println("\n--- SYSTEM LOG ---");
        for (String entry : inMemoryLog) {
            System.out.println(entry);
        }
    }
}
