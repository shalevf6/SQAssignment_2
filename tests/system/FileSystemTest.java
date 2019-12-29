package system;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class FileSystemTest {

    private FileSystem fileSystem;
    private int fileSystemSize;

    @Before
    public void createFileSystem() {
        Random random = new Random();
        fileSystemSize = random.nextInt(30) + 21;
        fileSystem = new FileSystem(fileSystemSize);
    }

    @Test
    public void testFileSystemConstructor(){
        //checks if the file system is not null
        assertTrue(fileSystem != null);
    }

    @Test
    public void testSpaceOnFileSystem(){
        //checks if the space inside the file system is not null
        assertTrue(fileSystem.fileStorage!=null);
    }


    @Test
    public void testCreateDirWithNoRoot() {

    }

    @Test
    public void disk() {
    }

    @Test
    public void file() {
    }

    @Test
    public void lsdir() {
    }

    @Test
    public void rmfile() {
    }

    @Test
    public void rmdir() {
    }

    @Test
    public void fileExists() {
    }

    @Test
    public void dirExists() {
    }
}