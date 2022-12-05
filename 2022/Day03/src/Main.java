import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day03/input.txt"));
            String line;
            int sumOfPriorities = 0;
            List<List<Character>> ruckSacksOfGroup = new ArrayList<>();
            int groupCounter = 0;

            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    /* Part One
                    List<Character> compartmentOne = convertStringToCharList(line.substring(0, line.length() / 2));
                    List<Character> compartmentTwo = convertStringToCharList(line.substring(line.length() / 2));
                    Character commonChar = getCommonCharOfLists(new ArrayList<>(Arrays.asList(compartmentOne, compartmentTwo)));
                    sumOfPriorities += getPriorityOfChar(commonChar);
                    */

                    ruckSacksOfGroup.add(convertStringToCharList(line));
                    groupCounter++;

                    if (groupCounter == 3) {
                        Character commonChar = getCommonCharOfLists(ruckSacksOfGroup);
                        sumOfPriorities += getPriorityOfChar(commonChar);
                        ruckSacksOfGroup.clear();
                        groupCounter = 0;
                    }
                }
            }
            reader.close();
            processResult(sumOfPriorities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Character> convertStringToCharList(String str)
    {
        return str.chars()
                .mapToObj(e -> (char)e)
                .collect(Collectors.toList());
    }

    private static Character getCommonCharOfLists(List<List<Character>> compartments) {
        List<Character> remainingCharacters = compartments.get(0);
        for (int i = 1; i < compartments.size(); i++) {
            remainingCharacters.retainAll(compartments.get(i));
        }
        return remainingCharacters.get(0);
    }

    private static int getPriorityOfChar(Character c) {
        if ('a' <= c && c <= 'z') {
            return c - ('a' - 1);
        }
        else if ('A' <= c && c <= 'Z') {
            return c - ('A' - 1) + 26;
        }
        else {
            throw new IllegalArgumentException("bad param");
        }
    }

    private static void processResult(int highestCaloriesCount) throws IOException {
        File outputFile = new File("2022/Day03/output.txt");
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFile);
        myWriter.write(String.valueOf(highestCaloriesCount));
        System.out.println(highestCaloriesCount);
        myWriter.close();
    }
}