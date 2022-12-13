import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day13/testinput.txt"));
            List<String> lines = new ArrayList<>();
            initLines(reader, lines);
            int indexSumOfOrderedPairs = 0;
            for (int i = 0; i < lines.size(); i += 2) {
                List<Character> lineOne = lines.get(i)
                        .chars()
                        .mapToObj(x -> (char) x)
                        .collect(Collectors.toCollection(ArrayList::new));
                List<Character> lineTwo = lines.get(i + 1)
                        .chars()
                        .mapToObj(x -> (char) x)
                        .collect(Collectors.toCollection(ArrayList::new));

                boolean isPairInOrder = isInOrder(lineOne, lineTwo);
                if (isPairInOrder) {
                    indexSumOfOrderedPairs += i + 1;
                }
            }

            reader.close();
            processResult(indexSumOfOrderedPairs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isInOrder(List<Character> A, List<Character> B) {
        List<Character> firstElementOfA = getFirstElement(A);
        List<Character> firstElementOfB = getFirstElement(B);
        //TODO egyenl? esetén menjen tovább
        if (!isList(firstElementOfA) && !isList(firstElementOfB)) {
             int firstIntElementOfA = Integer.parseInt(firstElementOfA.toString().replaceAll("[^0-9]", ""));
             int firstIntElementOfB = Integer.parseInt(firstElementOfB.toString().replaceAll("[^0-9]", ""));
             return firstIntElementOfA < firstIntElementOfB;
        }
        else if (isList(firstElementOfA) && !isList(firstElementOfB)) {
            List<Character> firstElementOfBAsList = makeListFromElement(firstElementOfB);
            return isInOrder(firstElementOfA, firstElementOfBAsList);
        }
        else if (!isList(firstElementOfA) && isList(firstElementOfB)) {
            List<Character> firstElementOfAAsList = makeListFromElement(firstElementOfA);
            return isInOrder(firstElementOfAAsList, firstElementOfB);
        }
        else if (isList(firstElementOfA) && isList(firstElementOfB)) {
            return isInOrder(firstElementOfA, firstElementOfB);
        }
        else {
            throw new IllegalArgumentException("something terrible happened");
        }
    }

    private static List<Character> makeListFromElement(List<Character> B) {
        return new ArrayList<>() {
            {
                add('[');
                addAll(B);
                add(']');
            }
        };
    }

    private static List<Character> getFirstElement(List<Character> characters) {
        return isFirstElementList(characters) ?
                getFirstListElement(characters) :
                getFirstNonListElement(characters);
    }

    private static List<Character> getFirstListElement(List<Character> characters) {
        int openingBracketCounter = 0;
        int closingBracketCounter = 0;
        for (int i = 1; i < characters.size() - 1; i++) {
            switch (characters.get(i)) {
                case '[':
                    openingBracketCounter++;
                    break;
                case ']':
                    closingBracketCounter++;
                    break;
                default:
                    break;
            }
            if (openingBracketCounter == closingBracketCounter) {
                return characters.subList(1, i + 1);
            }
        }

        return characters.subList(1, characters.size() - 1);
    }

    private static List<Character> getFirstNonListElement(List<Character> characters) {
        List<Character> nonListElement = new ArrayList<>();
        int i = 0;
        if (characters.get(0) == '[') {
            i++;
        }
        while (characters.get(i) != ',' && i < characters.size() - 1) {
            nonListElement.add(characters.get(i));
            i++;
        }
        return nonListElement;
    }

    private static boolean isList(List<Character> characters) {
        return characters.get(0) == '[';
    }

    private static boolean isFirstElementList(List<Character> characters) {
        return characters.get(1) == '[';
    }

    private static void initLines(BufferedReader reader, List<String> lines) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.isBlank()) {
                lines.add(line);
            }
        }
    }

    private static void processResult(int highestCaloriesCount) throws IOException {
        File outputFile = new File("2022/Day13/output.txt");
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFile);
        myWriter.write(String.valueOf(highestCaloriesCount));
        System.out.println(highestCaloriesCount);
        myWriter.close();
    }
}