package flashcards;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    private final static TreeMap<String, String> cards = new TreeMap<>();
    private final static TreeMap<String, String> cardsSwap = new TreeMap<>();
    private final static TreeMap<String, Integer> mistakes = new TreeMap<>();

    private final static Scanner scanner = new Scanner(System.in);
    private final static ArrayList<String> log = new ArrayList<>();

    protected static void addCard () {
        System.out.println("The card:");
        log.add("The card:");
        String cardKey = scanner.nextLine();
        log.add(cardKey);

        if (cards.containsKey(cardKey)) {
            System.out.println("The card \"" + cardKey + "\" already exists.");
            log.add("The card \"" + cardKey + "\" already exists.");
        } else {
            System.out.println("The definition of the card:");
            log.add("The definition of the card:");

            String definition = scanner.nextLine();
            log.add(definition);

            if (cards.containsValue(definition)) {
                System.out.println("The definition \"" + definition + "\" already exists.");
                log.add("The definition \"" + definition + "\" already exists.");
            } else {

                cards.put(cardKey, definition);
                cardsSwap.put(definition, cardKey);
                mistakes.put(cardKey, 0);
                System.out.println("The pair (\"" + cardKey + "\":\"" + definition + "\")" + " has been added.");
                log.add("The pair (\"" + cardKey + "\":\"" + definition + "\")" + " has been added.");
            }
        }
    }

    protected static void removeCard () {
        System.out.println("The card:");
        log.add("The card:");
        String cardKey = scanner.nextLine();
        log.add(cardKey);

        if (cards.containsKey(cardKey)) {
            cards.remove(cardKey);
//            cardsSwap.remove(cards.get(cardKey)); ошибка??? NullPointer
            mistakes.remove(cardKey);
            System.out.println("The card has been removed.");
            log.add("The card has been removed.");
        } else {
            System.out.println("Can't remove \"" + cardKey + "\": there is no such card.");
            log.add("Can't remove \"" + cardKey + "\": there is no such card.");
        }
    }

    protected static void importFromFile () {
        System.out.println("File name:");
        log.add("File name:");
        String fileName = scanner.nextLine();
        log.add(fileName);

        importFileMethod(fileName);
    }

    protected static void importFileMethod (String fileName) {
        String cardKey;
        String definition;
        int mistake;

        File file = new File("C:\\Users\\Alexandra\\Desktop\\IAD_LAB4ANOTHER\\Flashcards\\Flashcards\\task\\src\\flashcards\\" + fileName);
        if (file.isFile()){
            int i = 0;
            try (Scanner scr = new Scanner(file)) {
                while (scr.hasNextLine()) {
                    cardKey = scr.nextLine();
                    log.add(cardKey);
                    definition = scr.nextLine();
                    log.add(definition);
                    mistake = Integer.parseInt(scr.nextLine());

                    cards.put(cardKey, definition);
                    cardsSwap.put(definition, cardKey);
                    mistakes.put(cardKey, mistake);

                    i++;
                }
            } catch (FileNotFoundException e) {
                System.out.println("Plak-plak: " + fileName);
                log.add("Plak-plak: " + fileName);
            }

            System.out.println(i + " cards have been loaded.");
            log.add(i + " cards have been loaded.");
        } else {
            System.out.println("File not found.");
            log.add("File not found.");
        }
    }

    protected static void exportToFile () {
        System.out.println("File name:");
        log.add("File name:");
        String fileName = scanner.nextLine();
        log.add(fileName);

        exportFileMethod(fileName);
    }

    protected static void exportFileMethod (String fileName) {

        File file = new File("C:\\Users\\Alexandra\\Desktop\\IAD_LAB4ANOTHER\\Flashcards\\Flashcards\\task\\src\\flashcards\\" + fileName);

        if (file.isFile()){
            try (PrintWriter printWriter = new PrintWriter(file)) {
                for (String key: cards.keySet()) {
                    printWriter.println(key);
                    printWriter.println(cards.get(key));
                    printWriter.println(mistakes.get(key));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(cards.size() + " cards have been saved.");
            log.add(cards.size() + " cards have been saved.");
        } else {
            System.out.println("File not found.");
            log.add("File not found.");
        }
    }

    protected static void askDefinition () {
        System.out.println("How many times to ask?");
        log.add("How many times to ask?");
        int times = Integer.parseInt(scanner.nextLine());
        log.add(String.valueOf(times));

        String[] arrayKeys = cards.keySet().toArray(new String[0]);

        for (int i = 0; i < times; i++) {
            Random random = new Random();
            String key = arrayKeys[random.nextInt(arrayKeys.length)];
            log.add(key);

            String answer;
            System.out.println("Print the definition of \"" + key +  "\":");
            log.add("Print the definition of \"" + key +  "\":");
            answer = scanner.nextLine();
            log.add(answer);

            if (answer.equals(cards.get(key)) ) {
                System.out.println("Correct answer.");
                log.add("Correct answer.");
            } else  if (cards.containsValue(answer)) {
                System.out.println("Wrong answer. The correct one is \"" + cards.get(key) + "\"," +
                        " you've just written the definition of \"" + cardsSwap.get(answer) + "\"");
                log.add("Wrong answer. The correct one is \"" + cards.get(key) + "\"," +
                        " you've just written the definition of \"" + cardsSwap.get(answer) + "\"");

                mistakes.put(key, mistakes.get(key) + 1);
            } else {
                System.out.println("Wrong answer. The correct one is \"" + cards.get(key) + "\"");
                log.add("Wrong answer. The correct one is \"" + cards.get(key) + "\"");
                mistakes.put(key, mistakes.get(key) + 1);
            }
        }

    }

    protected static void setLog(){
        System.out.println("File name:");
        log.add("File name:");
        String fileName = scanner.nextLine();
        log.add(fileName);

        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file.isFile()){
            try (PrintWriter printWriter = new PrintWriter(file)) {
                System.out.println("The log has been saved.");
                log.add("The log has been saved.");

                for (String line : log) {
                    printWriter.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            log.clear();

        } else {
            System.out.println("File not found.");
            log.add("File not found.");
        }
    }

    protected static void hardestCard() {
        int max = 0;
        ArrayList<String> answers = new ArrayList<>();
        for (String key : mistakes.keySet()) {
            if (mistakes.get(key) > max) {
                answers.clear();
                max = mistakes.get(key);
                answers.add(key);
            } else if (mistakes.get(key) == max) {
                answers.add(key);
            }
        }

        if (max == 0) {
            System.out.println("There are no cards with errors.");
            log.add("There are no cards with errors.");
        } else if (answers.size() == 1) {
            System.out.print("The hardest card is \"" + answers.get(0) + "\". You have " + max + " errors answering it.");
            log.add("The hardest card is \"" + answers.get(0) + "\". You have " + max + " errors answering it.");
        } else {
            System.out.print("The hardest card are ");
            StringBuilder logString = new StringBuilder();
            for (int i = 0; i < answers.size() - 1; i++) {
                System.out.print("\"" + answers.get(i) + "\", ");
                logString.append("\"").append(answers.get(i)).append("\", ");
            }
            System.out.println("\"" +  answers.get(answers.size() - 1) + "\". You have " + max + " errors answering it.");

            logString.append("\"").append(answers.get(answers.size() - 1)).append("\". You have \" + max + \" errors answering it.");
            log.add(String.valueOf(logString));
        }
    }

    protected static void resetStats() {
        for (String key : mistakes.keySet()) {
            mistakes.replace(key, 0);
        }

        System.out.println("Card statistics has been reset.");
        log.add("Card statistics has been reset.");
    }


    protected static void exportArg(String[] args) {

    }

    public static void main(String[] args) {

        String exportFile = null;
        if (args.length > 0 ) {
            if (args[0].equals("-import")) {
                importFileMethod(args[1]);
                log.add(args[0] + " " + args[1]);
                if (args.length > 2 && args[2].equals("-export")) {
                    exportFile = args[3];
                    log.add(" " + args[2] + " " + args[3]);
                }
            } else if (args[0].equals("-export")) {
                exportFile = args[1];
                log.add(args[0] + " " + args[1]);
                if (args.length > 2&& args[2].equals("-import")) {
                    importFileMethod(args[3]);
                    log.add(" " + args[2] + " " + args[3]);
                }
            }
        }

        boolean end = false;

        while (!end) {

            System.out.println("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats) :");
            log.add("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats) :");

            String input = scanner.nextLine();
            log.add(input);

            switch (input) {
                case ("add"): {
                    addCard();
                    break;
                }
                case ("ask"): {
                    askDefinition();
                    break;
                }
                case ("remove"): {
                    removeCard();
                    break;
                }
                case ("import"): {
                    importFromFile();
                    break;
                }
                case ("export"): {
                    exportToFile();
                    break;
                }
                case ("exit"): {
                    System.out.println("Bye bye!");
                    end = true;
                    if (exportFile != null) {
                        exportFileMethod(exportFile);
                    }
                    break;
                }
                case ("log"): {
                    setLog();
                    break;
                }
                case ("hardest card"): {
                    hardestCard();
                    break;
                }
                case ("reset stats"): {
                    resetStats();
                    break;
                }
            }
        }
    }
}
