import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day05/input.txt"));
            String command;
            List<Stack<Character>> stacks = new ArrayList<>();
            List<String> startingPositionInputs = new ArrayList<>();

            initStacks(reader, stacks, startingPositionInputs);

            while ((command = reader.readLine()) != null) {
                if (!command.isBlank()) {
                    executeCommand(command, stacks);
                }
            }
            reader.close();
            processResult(stacks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initStacks(BufferedReader reader, List<Stack<Character>> stacks, List<String> startingPositionInputs) throws IOException {
        String line;
        for (int i = 0; i < 9; i++) {
            stacks.add(new Stack<>());
        }
        while ((line = reader.readLine()).charAt(1) != '1') {
            startingPositionInputs.add(line);
        }
        for (int i = startingPositionInputs.size() - 1; i >= 0; i--) {
            String currentInputLine = startingPositionInputs.get(i);
            for (int j = 1; j < 9 * 4 - 2; j += 4) {
                char currentCharacter = currentInputLine.charAt(j);
                if (currentCharacter != ' ') {
                    int stackPosition = j / 4;
                    stacks.get(stackPosition).push(currentCharacter);
                }
            }
        }
    }

    private static void executeCommand(String command, List<Stack<Character>> stacks) {
        List<Integer> commandParameters = extractIntegersFromCommand(command);
        int numberOfCrates = commandParameters.get(0);
        int from = commandParameters.get(1);
        int to = commandParameters.get(2);

        for (int i = 0; i < numberOfCrates; i++) {
            Character crateToMove = stacks.get(from - 1).pop();
            stacks.get(to - 1).push(crateToMove);
        }
    }

    static List<Integer> extractIntegersFromCommand(String str)
    {
        str = str.replaceAll("[^0-9]", " ");
        str = str.replaceAll(" +", " ");
        if (str.charAt(0) == (' ')) {
            str = str.substring(1);
        }
        List<String> numbers = Arrays.asList(str.split(" "));

        return numbers.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static void processResult(List<Stack<Character>> stacks) throws IOException {
        File outputFile = new File("2022/Day05/output.txt");
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFile);

        StringBuilder result = new StringBuilder();
        for (Stack<Character> currentStack : stacks) {
            if (!currentStack.empty()) {
                result.append(currentStack.pop());
            } else {
                result.append(" ");
            }
        }
        myWriter.write(result.toString());
        System.out.println(result);
        myWriter.close();
    }
}