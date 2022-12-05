import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day04/input.txt"));
            String line;
            int fullyContainedPairCounter = 0;

            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    String[] rangesOfPair = line.split(",");
                    int firstElfIDFrom = Integer.parseInt(rangesOfPair[0].split("-")[0]);
                    int firstElfIDTo = Integer.parseInt(rangesOfPair[0].split("-")[1]);
                    int secondElfIDFrom = Integer.parseInt(rangesOfPair[1].split("-")[0]);
                    int secondElfIDTo = Integer.parseInt(rangesOfPair[1].split("-")[1]);

                    if (areIDsPartiallyContained(firstElfIDFrom, firstElfIDTo, secondElfIDFrom, secondElfIDTo)) {
                        fullyContainedPairCounter++;
                    }
                }
            }
            reader.close();
            processResult(fullyContainedPairCounter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean areIDsFullyContained(int firstFrom, int firstTo, int secondFrom, int secondTo) {
        return (firstFrom <= secondFrom && secondTo <= firstTo) ||
                (secondFrom <= firstFrom && firstTo <= secondTo);
    }

    private static boolean areIDsPartiallyContained(int firstFrom, int firstTo, int secondFrom, int secondTo) {
        return !(firstTo < secondFrom || secondTo < firstFrom);
    }

    private static void processResult(int highestCaloriesCount) throws IOException {
        File outputFile = new File("2022/Day04/output.txt");
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFile);
        myWriter.write(String.valueOf(highestCaloriesCount));
        System.out.println(highestCaloriesCount);
        myWriter.close();
    }
}