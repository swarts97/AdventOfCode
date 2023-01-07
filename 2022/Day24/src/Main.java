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
            String line;


            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {

                }
            }
            reader.close();
            //processResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processResult(List<Stack<Character>> stacks) throws IOException {
        File outputFile = new File("2022/Day24/output.txt");
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