import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day02/input.txt"));
            String line;
            char opponentChoice;
            char myChoice;
            char outcome;
            int sumOfMyScore = 0;

            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    opponentChoice = line.charAt(0);
                    //Part 1 version
                    //myChoice = line.charAt(2);
                    outcome = line.charAt(2);
                    myChoice = getMyChoice(opponentChoice, outcome);
                    sumOfMyScore += getScore(opponentChoice, myChoice);
                }
            }
            reader.close();
            processResult(sumOfMyScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static char getMyChoice(char opponentChoice, char outcome) {
        if (opponentChoice == 'A' && outcome == 'X') {
            return 'Z';
        } else if (opponentChoice == 'A' && outcome == 'Y') {
            return 'X';
        } else if (opponentChoice == 'A' && outcome == 'Z') {
            return 'Y';
        } else if (opponentChoice == 'B' && outcome == 'X') {
            return 'X';
        } else if (opponentChoice == 'B' && outcome == 'Y') {
            return 'Y';
        } else if (opponentChoice == 'B' && outcome == 'Z') {
            return 'Z';
        } else if (opponentChoice == 'C' && outcome == 'X') {
            return 'Y';
        } else if (opponentChoice == 'C' && outcome == 'Y') {
            return 'Z';
        } else if (opponentChoice == 'C' && outcome == 'Z') {
            return 'X';
        }
        else {
            throw new IllegalArgumentException("hibás paraméter!");
        }
    }

    private static int getScore(char opponentChoice, char myChoice) {
        return getScoreForShape(myChoice) + getScoreForOutcome(opponentChoice, myChoice);
    }

    private static int getScoreForShape(char myChoice) {
        switch (myChoice) {
            case 'X': {
                return 1;
            }
            case 'Y': {
                return 2;
            }
            case 'Z': {
                return 3;
            }
            default: {
                throw new IllegalArgumentException("hibás paraméter!");
            }
        }
    }

    private static int getScoreForOutcome(char opponentChoice, char myChoice) {
        if (opponentChoice == 'A' && myChoice == 'Y') {
            return 6;
        } else if (opponentChoice == 'A' && myChoice == 'Z') {
            return 0;
        } else if (opponentChoice == 'B' && myChoice == 'X') {
            return 0;
        } else if (opponentChoice == 'B' && myChoice == 'Z') {
            return 6;
        } else if (opponentChoice == 'C' && myChoice == 'X') {
            return 6;
        } else if (opponentChoice == 'C' && myChoice == 'Y') {
            return 0;
        } else {
            return 3;
        }
    }

    private static void processResult(int highestCaloriesCount) throws IOException {
        File outputFile = new File("2022/Day02/output.txt");
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFile);
        myWriter.write(String.valueOf(highestCaloriesCount));
        System.out.println(highestCaloriesCount);
        myWriter.close();
    }
}