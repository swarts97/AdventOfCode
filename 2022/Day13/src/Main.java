import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day13/input.txt"));
            List<String> lines = new ArrayList<>();
            initLines(reader, lines);
            int indexSumOfOrderedPairs = 0;
            for (int i = 0; i < lines.size(); i += 2) {
                List<Character> lineOne = stringToCharacterList(lines.get(i));
                List<Character> lineTwo = stringToCharacterList(lines.get(i + 1));

                boolean isPairInOrder = isInOrder(lineOne, lineTwo);
                if (isPairInOrder) {
                    indexSumOfOrderedPairs += i / 2 + 1;
                }
            }

            reader.close();
            processResult(indexSumOfOrderedPairs);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            /* if (firstElementOfA.size() == 0 && firstElementOfB.size() != 0) {
                return true;
            }
            if (firstElementOfA.size() == 0 && firstElementOfB.size() != 0) {
                return true;
            } */
            if (areElementsTheSameList(firstElementOfA, firstElementOfB)) {
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
            return AAsString.length() < BAsString.length();
        }
        return AAsString.compareTo(BAsString) < 0;
    }

    private static boolean isSimpleList(List<Character> characterList) {
        return characterList.stream()
                .filter(character -> character == '[')
                .count() <= 1;
    }

    private static boolean areElementsTheSameList(List<Character> firstElementOfA, List<Character> firstElementOfB) {
        return firstElementOfA.toString().equals(firstElementOfB.toString()) && firstElementOfA.size() != 1 && firstElementOfB.size() != 1;
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