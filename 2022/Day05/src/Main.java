import java.io.*;

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
            processResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processResult(int highestCaloriesCount) throws IOException {
        File outputFile = new File("2022/Day05/output.txt");
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFile);
        myWriter.write(String.valueOf(highestCaloriesCount));
        System.out.println(highestCaloriesCount);
        myWriter.close();
    }
}