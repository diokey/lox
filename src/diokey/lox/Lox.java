package diokey.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {

    private static boolean hadError = false;

    private static void showHelp() {
        System.out.println("Usage: jlox [script]");
        System.exit(64);
    }

    static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }

    private static void run(String code) {
        Scanner scanner = new Scanner(code);
        List<Token> tokens = scanner.scanTokens();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    private static void runFile(String fileName) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(fileName));
        String code = new String(bytes, Charset.defaultCharset());
        run(code);
        if (hadError) {
            System.exit(65);
        }
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        for (;;) {
            System.out.print("> ");
            String code = reader.readLine();
            run(code);
            hadError = false;
        }
    }

    public static void main(String[] args) throws IOException {
	    if (args.length > 1) {
            showHelp();
        } else if (args.length == 1) {
	        runFile(args[0]);
        } else {
	        runPrompt();
        }
    }

}
