package dev.wzid.bot;

public class MissingTokenException extends Exception {
    public MissingTokenException() {
        super("Missing Token!");
    }
}
