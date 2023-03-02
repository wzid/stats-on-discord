package dev.wzid.bot;

public class InvalidConfigException extends Exception {
    // Class for all the exceptions that we may run into when reading bot-properties.yml
    public InvalidConfigException(String message) {
        super("Invalid Config: " + message);
    }
}
