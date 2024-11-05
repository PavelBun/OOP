package org.example;

import Expressions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String expressionString = "";
        String assignments = "";

        if (args.length > 0 && args[0].equals("-f")) {
            // Чтение из файла
            try (BufferedReader expressionReader = new BufferedReader(new FileReader("expression.txt"));
                 BufferedReader variablesReader = new BufferedReader(new FileReader("variables.txt"))) {
                expressionString = expressionReader.readLine();
                assignments = variablesReader.readLine();
            } catch (IOException e) {
                System.err.println("Error reading files: " + e.getMessage());
                return;
            }
        } else {
            // Чтение с консоли
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Enter the expression:");
                expressionString = scanner.nextLine();

                System.out.println("Enter the variable assignments(x=_; y=_):");
                assignments = scanner.nextLine();
            }
        }


        Map<String, Double> variables = Parser.parseVariables(assignments);
    }
}