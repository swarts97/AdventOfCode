import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day06/input.txt"));
            String line;
            int markerPosition = 0;

            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    markerPosition = getMarkerPosition(line);
                }
            }
            reader.close();
            processResult(markerPosition);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getMarkerPosition(String line) {
        char[] chars = line.toCharArray();
        for (int i = 3; i < chars.length; i++) {
            String lastNLetterString = getLastNLetterString(chars, i, 4);
            if (allCharactersAreDifferent(lastNLetterString)) {
                return i + 1;
            }
        }
        return -1;
    }

    private static boolean allCharactersAreDifferent(String string) {
        for (int i = 0; i < string.length(); i++) {
            for (int j = string.length() - 1; i < j; j--) {
                if (string.charAt(i) == string.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static String getLastNLetterString(char[] chars, int i, int n) {
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < n; j++) {
            result.append(chars[i - (n - 1 - j)]);
        }
        return result.toString();
    }

    private static void processResult(int highestCaloriesCount) throws IOException {
        File outputFile = new File("2022/Day06/output.txt");
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFile);
        myWriter.write(String.valueOf(highestCaloriesCount));
        System.out.println(highestCaloriesCount);
        myWriter.close();
    }
}