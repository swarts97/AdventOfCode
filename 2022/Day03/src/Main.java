import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day03/input.txt"));
            String line;
            int sumOfPriorities = 0;

            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    List<Character> compartmentOne = convertStringToCharList(line.substring(0, line.length() / 2));
                    List<Character> compartmentTwo = convertStringToCharList(line.substring(line.length() / 2));
                    Character commonChar = getCommonCharOfLists(compartmentOne, compartmentTwo);
                    sumOfPriorities += getPriorityOfChar(commonChar);
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

    private static Character getCommonCharOfLists(List<Character> compartmentOne, List<Character> compartmentTwo) {
        return compartmentOne
                .stream()
                .filter(compartmentTwo::contains)
                .findFirst()
                .get();
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