import java.util.ArrayList;
import java.util.List;

public class ElfDirectory {

    private String name;
    private ElfDirectory parentDir;
    private final List<ElfDirectory> subDirs = new ArrayList<>();
    private final List<ElfFile> files = new ArrayList<>();

    public ElfDirectory(String name, ElfDirectory parentDir) {
        this.name = name;
        this.parentDir = parentDir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ElfDirectory getParentDir() {
        return parentDir;
    }

    public void setParentDir(ElfDirectory parentDir) {
        this.parentDir = parentDir;
    }

    public List<ElfDirectory> getSubDirs() {
        return subDirs;
    }

    public List<ElfFile> getFiles() {
        return files;
    }
}
