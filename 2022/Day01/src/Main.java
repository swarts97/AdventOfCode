import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day01/input.txt"));
            List<Integer> highestCalories = new LinkedList<>();
            int totalCaloriesForCurrentElf = 0;
            int totalCaloriesForTopElves;
            int requiredElfsCounter = 3;
            initHighestCaloriesList(highestCalories, requiredElfsCounter);
            setTopHighestCalories(reader, highestCalories, totalCaloriesForCurrentElf);

            totalCaloriesForTopElves = highestCalories.stream().reduce(0, Integer::sum);
            processResult(totalCaloriesForTopElves);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setTopHighestCalories(BufferedReader reader, List<Integer> highestCalories, int totalCaloriesForCurrentElf) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.isBlank()) {
                totalCaloriesForCurrentElf += Integer.parseInt(line);
            } else {
                int currentElfRank = Math.abs(Collections.binarySearch(highestCalories, totalCaloriesForCurrentElf)) - 1;
                if (currentElfRank != 0) {
                    highestCalories.add(currentElfRank, totalCaloriesForCurrentElf);
                    highestCalories.remove(0);
                }
                totalCaloriesForCurrentElf = 0;
            }
        }
    }

    private static void initHighestCaloriesList(List<Integer> highestCaloriesCount, int requiredElfsCounter) {
        for (int i = 0; i < requiredElfsCounter; i++) {
            highestCaloriesCount.add(0);
        }
    }

    private static void processResult(int highestCaloriesCount) throws IOException {
        File outputFile = new File("2022/Day01/output.txt");
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFile);
        myWriter.write(String.valueOf(highestCaloriesCount));
        System.out.println(highestCaloriesCount);
        myWriter.close();
    }
}