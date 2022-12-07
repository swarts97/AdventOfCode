import java.io.*;

public class Main {
    public static ElfDirectory currentDir = new ElfDirectory("/", null);
    public static ElfDirectory rootDir = currentDir;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2022/Day07/input.txt"));

            reader.readLine(); //Skip 1st line
            initFileSystem(reader);

            int limit = 100000;
            int directoriesUnderLimitSizeSum = sumDirectorySizesUnderLimit(rootDir, limit);

            reader.close();
            processResult(directoriesUnderLimitSizeSum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initFileSystem(BufferedReader reader) throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            if (!line.isBlank()) {
                LineType lineType = getLineType(line);
                switch (lineType) {
                    case ChangeDir:
                        HandleChangeDir(line);
                        break;
                    case List:
                        HandleList(reader);
                        break;
                }
            }
        }
    }

    private static void HandleChangeDir(String line) {
        String targetDirName = line.split(" ")[2];
        if (targetDirName.equals("..")) {
            currentDir = currentDir.getParentDir();
        }
        else {
            currentDir = currentDir.getSubDirs().stream()
                    .filter(dir -> dir.getName().equals(targetDirName))
                    .findFirst()
                    .get();
        }
    }

    private static void HandleList(BufferedReader reader) throws IOException {
        int readerBufferSize = 100;
        reader.mark(readerBufferSize);
        String line;
        while ((line = reader.readLine()) != null) {
            LineType lineType = getLineType(line);
            if (!line.isBlank()) {
                if (lineType == LineType.ChangeDir || lineType == LineType.List) {
                    reader.reset();
                    break;
                }
                else {
                    reader.mark(readerBufferSize);
                    switch (lineType) {
                        case Directory:
                            AddDirectoryToCurrentDir(line);
                            break;
                        case File:
                            AddFileToCurrentDir(line);
                            break;
                    }
                }
            }
        }
    }


    private static LineType getLineType(String line) {
        if (line.startsWith("$ cd"))
            return LineType.ChangeDir;
        else if (line.startsWith("$ ls"))
            return LineType.List;
        else if (line.startsWith("dir"))
            return LineType.Directory;
        else
            return LineType.File;
    }

    private static void AddDirectoryToCurrentDir(String line) {
        String dirName = line.split(" ")[1];
        ElfDirectory dir = new ElfDirectory(dirName, currentDir);
        currentDir.getSubDirs().add(dir);
    }

    private static void AddFileToCurrentDir(String line) {
        int fileSize = Integer.parseInt(line.split(" ")[0]);
        String fileName = line.split(" ")[1];
        ElfFile file = new ElfFile(fileName, fileSize);
        currentDir.getFiles().add(file);
    }

    private static int getDirectorySize(ElfDirectory dir) {
        int sum = 0;
        if (!dir.getSubDirs().isEmpty()) {
            for (ElfDirectory directory : dir.getSubDirs()) {
                sum += getDirectorySize(directory);
            }
        }
        for (ElfFile file : dir.getFiles()) {
            sum += file.getSize();
        }
        return sum;
    }

    private static int sumDirectorySizesUnderLimit(ElfDirectory dir, int limit) {
        int sum = 0;
        if (!dir.getSubDirs().isEmpty()) {
            for (ElfDirectory subDir : dir.getSubDirs()) {
                sum += sumDirectorySizesUnderLimit(subDir, limit);
            }
        }
        if (getDirectorySize(dir) <= limit) {
            sum += getDirectorySize(dir);
        }
        return sum;
    }

    private static void processResult(int highestCaloriesCount) throws IOException {
        File outputFile = new File("2022/Day07/output.txt");
        outputFile.createNewFile();
        FileWriter myWriter = new FileWriter(outputFile);
        myWriter.write(String.valueOf(highestCaloriesCount));
        System.out.println(highestCaloriesCount);
        myWriter.close();
    }
}