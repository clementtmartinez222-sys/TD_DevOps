package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void mainPrintsWelcomeMessageAndCounters() {
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(output));
            Main.main(new String[0]);
        } finally {
            System.setOut(originalOutput);
        }

        String text = output.toString();
        assertTrue(text.contains("Hello and welcome!"));
        assertTrue(text.contains("i = 1"));
        assertTrue(text.contains("i = 5"));
    }
}
