import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day08/input.txt"));
            List<List<Integer>> forest = new ArrayList<>();

            initForest(reader, forest);
            int forestWidth = forest.get(0).size();
            int forestHeight = forest.size();
            int visibleTreeCounter = forestWidth * 2 + forestHeight * 2 - 4;
            for (int i = 1; i < forestHeight - 1; i++) {
                for (int j = 1; j < forestWidth - 1; j++) {
                    int currentTreeHeight = getTreeHeight(forest, i, j);
                    if (isTreeVisibleFromEdge(forest, i, j, Direction.UP, forestWidth, forestHeight, currentTreeHeight) ||
                    isTreeVisibleFromEdge(forest, i, j, Direction.RIGHT, forestWidth, forestHeight, currentTreeHeight) ||
                    isTreeVisibleFromEdge(forest, i, j, Direction.DOWN, forestWidth, forestHeight, currentTreeHeight) ||
                    isTreeVisibleFromEdge(forest, i, j, Direction.LEFT, forestWidth, forestHeight, currentTreeHeight)) {
                        visibleTreeCounter++;
                    }
                }
            }

            reader.close();
            processResult(visibleTreeCounter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isTreeVisibleFromEdge(List<List<Integer>> forest, int i, int j, Direction dir, int forestWidth, int forestHeight, int currentTreeHeight) {
        int visibleTreesToDir;
        visibleTreesToDir = getVisibleTreeCountsFromFreeToDirection(forest, i, j, dir, forestWidth, forestHeight, currentTreeHeight);
        return visibleTreesToDir < 0;
    }

    private static void initForest(BufferedReader reader, List<List<Integer>> forest) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.isBlank()) {
                String[] trees = line.split("");
                List<Integer> treeLine = Arrays.stream(trees)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                forest.add(treeLine);
            }
        }
    }

    private static Integer getTreeHeight(List<List<Integer>> forest, int i, int j) {
        return forest.get(i).get(j);
    }

    private static List<Integer> getTreesInDirection(List<List<Integer>> forest, int y, int x, Direction dir, int forestWidth, int forestHeight) {
        List<Integer> treesInDirection = new ArrayList<>();
        switch (dir) {
            case UP: {
                for (int i = y - 1; i >= 0; i--) {
                    treesInDirection.add(forest.get(i).get(x));
                }
                break;
            }
            case RIGHT: {
                for (int j = x + 1; j < forestWidth; j++) {
                    treesInDirection.add(forest.get(y).get(j));
                }
                break;
            }
            case DOWN: {
                for (int i = y + 1; i < forestHeight; i++) {
                    treesInDirection.add(forest.get(i).get(x));
                }
                break;
            }
            case LEFT: {
                for (int j = x - 1; j >= 0; j--) {
                    treesInDirection.add(forest.get(y).get(j));
                }
                break;
            }
        }
        return treesInDirection;
    }

    private static int getVisibleTreeCountsFromFreeToDirection(List<List<Integer>> forest, int y, int x, Direction dir, int forestWidth, int forestHeight, int currentTreeHeight) {
        List<Integer> treesInDirection = getTreesInDirection(forest, y, x, dir, forestWidth, forestHeight);
        int counter = 0;
        for (Integer integer : treesInDirection) {
            counter++;
            if (integer >= currentTreeHeight) {
                return counter;
            }
        }
        //-1 means it can be seen from the edge
        return counter * -1;
    }

    private static void processResult(int highestCaloriesCount) throws IOException {
        File outputFile = new File("2022/Day08/output.txt");
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFile);
        myWriter.write(String.valueOf(highestCaloriesCount));
        System.out.println(highestCaloriesCount);
        myWriter.close();
    }
}