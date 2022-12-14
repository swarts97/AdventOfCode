import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day13/input.txt"));
            List<String> lines = new ArrayList<>();
            initLines(reader, lines);
            int indexSumOfOrderedPairs = 0;
            indexSumOfOrderedPairs = calculateIndexSumOfOrderedPairs(lines, indexSumOfOrderedPairs);

            reader.close();
            processResult(indexSumOfOrderedPairs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int calculateIndexSumOfOrderedPairs(List<String> lines, int indexSumOfOrderedPairs) {
        for (int i = 0; i < lines.size(); i += 2) {
            List<Character> lineOne = stringToCharacterList(lines.get(i));
            List<Character> lineTwo = stringToCharacterList(lines.get(i + 1));

            boolean isPairInOrder = isInOrder(lineOne, lineTwo);
            if (isPairInOrder) {
                indexSumOfOrderedPairs += i / 2 + 1;
            }
        }
        return indexSumOfOrderedPairs;
    }

    private static List<Character> stringToCharacterList(String line) {
        return line
                .chars()
                .mapToObj(x -> (char) x)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static String characterListToString(List<Character> characterList) {
        StringBuilder result = new StringBuilder();
        for (Character character : characterList) {
            result.append(character);
        }
        return result.toString();
    }

    private static boolean isInOrder(List<Character> A, List<Character> B) {
        if (!isList(A) && !isList(B)) {
             int firstIntElementOfA = Integer.parseInt(characterListToString(A));
             int firstIntElementOfB = Integer.parseInt(characterListToString(B));
             return firstIntElementOfA < firstIntElementOfB;
        }
        else if (isList(A) && !isList(B)) {
            List<Character> firstElementOfBAsList = makeListFromElement(B);
            return isInOrder(A, firstElementOfBAsList);
        }
        else if (!isList(A) && isList(B)) {
            List<Character> firstElementOfAAsList = makeListFromElement(A);
            return isInOrder(firstElementOfAAsList, B);
        }
        else if (isList(A) && isList(B)) {
            if (isSimpleList(A) && isSimpleList(B)) {
                return areSimpleListsInOrder(A, B);
            }
            List<Character> firstElementOfA = getFirstElement(A);
            List<Character> firstElementOfB = getFirstElement(B);
            if (areElementsTheSame(firstElementOfA, firstElementOfB)) {
                List<Character> AWithoutFirstElementOfA = removeFirstOccurrenceFromList(A, firstElementOfA);
                List<Character> BWithoutFirstElementOfB = removeFirstOccurrenceFromList(B, firstElementOfB);
                return isInOrder(AWithoutFirstElementOfA, BWithoutFirstElementOfB);
            }
            else {
                return isInOrder(firstElementOfA, firstElementOfB);
            }
        }
        else {
            throw new IllegalArgumentException("something terrible happened");
        }
    }

    private static boolean areSimpleListsInOrder(List<Character> A, List<Character> B) {
        String AAsString = characterListToString(A).replaceAll("]", "").replaceAll("\\[", "");
        String BAsString = characterListToString(B).replaceAll("]", "").replaceAll("\\[", "");
        if (AAsString.isBlank() && BAsString.isBlank()) {
            return A.size() < B.size();
        }
        if (AAsString.length() == 0) {
            return true;
        }
        if (BAsString.length() == 0) {
            return false;
        }
        List<Integer> AAsListOfIntegers = Stream.of(AAsString.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Integer> BAsListOfIntegers = Stream.of(BAsString.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        for (int i = 0; ; i++) {
            if (AAsListOfIntegers.get(i) < BAsListOfIntegers.get(i)) {
                return true;
            }
            if (AAsListOfIntegers.get(i) > BAsListOfIntegers.get(i)) {
                return false;
            }
            if (i == AAsListOfIntegers.size() - 1) {
                return true;
            }
            if (i == BAsListOfIntegers.size() - 1) {
                return false;
            }
        }
    }

    private static boolean areElementsTheSame(List<Character> A, List<Character> B) {
        String AAsString = characterListToString(A).replaceAll("]", "").replaceAll("\\[", "");
        String BAsString = characterListToString(B).replaceAll("]", "").replaceAll("\\[", "");
        if (AAsString.isBlank() && BAsString.isBlank()) {
            return A.size() == B.size();
        }
        if (AAsString.length() != BAsString.length()) {
            return false;
        }

        List<Integer> AAsListOfIntegers = Stream.of(AAsString.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Integer> BAsListOfIntegers = Stream.of(BAsString.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        for (int i = 0; i < AAsListOfIntegers.size(); i++) {
            if (!AAsListOfIntegers.get(i).equals(BAsListOfIntegers.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSimpleList(List<Character> characterList) {
        return characterList.stream()
                .filter(character -> character == '[')
                .count() <= 1;
    }

    private static List<Character> removeFirstOccurrenceFromList(List<Character> characterList, List<Character> charactersToRemove) {
        String charactersAsString = characterListToString(characterList);
        String charactersToRemoveAsString = characterListToString(charactersToRemove);
        String result = charactersAsString.replaceFirst(Pattern.quote(charactersToRemoveAsString), "");
        //Deleting unnecessary ',' characters
        result = result.replaceAll(Pattern.quote("[,"), "[");
        return stringToCharacterList(result);
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
        if (characters.size() == 0) {
            return characters;
        }
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
        return characters.size() == 0 || characters.get(0) == '[';
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