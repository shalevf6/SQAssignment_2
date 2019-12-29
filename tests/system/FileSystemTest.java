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
        // get a random number between 20 - 50
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
    public void testCreateNewDir() {
        try {
            String[] dirPath = new String[]{"root", "dir1"};
            fileSystem.dir(dirPath);
            fileSystem.dir(dirPath);
            String[] subRoot = fileSystem.lsdir(new String[]{"root"});

            // check if dir1 was created under root
            assertEquals("dir1", subRoot[0]);

            // check if root is the parent of dir1
            Tree dir1 = fileSystem.DirExists(dirPath);
            assertEquals("root", dir1.parent.name);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateNewDirWithNewParentDirectories() {
        try {
            String[] filePath = new String[]{"root", "dir1", "subdir1", "subsubdir1"};
            fileSystem.dir(filePath);

            // check if dir1 was created under root
            String[] ls = fileSystem.lsdir(new String[]{"root"});
            assertEquals("dir1", ls[0]);

            // check if subdir1 was created under dir1
            ls = fileSystem.lsdir(new String[]{"root","dir1"});
            assertEquals("subdir1", ls[0]);

            // check if subdir1 is the parent of file1
            Tree subsubdir1 = fileSystem.DirExists(filePath);
            assertEquals("subdir1", subsubdir1.parent.name);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = BadFileNameException.class)
    public void testCreateDirWithNoRoot() throws BadFileNameException {
        String[] dirPath = new String[]{"dir1"};
        fileSystem.dir(dirPath);
    }

    @Test
    public void testCreateExistingDir() {
        try {
            String[] dirPath = new String[]{"root", "dir1"};
            fileSystem.dir(dirPath);
            fileSystem.dir(dirPath);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void disk() {
    }

    @Test
    public void testCreateNewFile() {
        try {
            String[] filePath = new String[]{"root", "file1"};
            fileSystem.file(filePath, 5);
            String[] subRoot = fileSystem.lsdir(new String[]{"root"});

            // check if file1 was created under root
            assertEquals("file1", subRoot[0]);

            // check if root is the parent of file1
            Leaf file1 = fileSystem.FileExists(filePath);
            assertEquals("root", file1.parent.name);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateNewFileWithNewParentDirectories() {
        try {
            String[] filePath = new String[]{"root", "dir1", "subdir1", "file1"};
            fileSystem.file(filePath, 5);

            // check if dir1 was created under root
            String[] ls = fileSystem.lsdir(new String[]{"root"});
            assertEquals("dir1", ls[0]);

            // check if subdir1 was created under dir1
            ls = fileSystem.lsdir(new String[]{"root","dir1"});
            assertEquals("subdir1", ls[0]);

            // check if subdir1 is the parent of file1
            Leaf file1 = fileSystem.FileExists(filePath);
            assertEquals("subdir1", file1.parent.name);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = BadFileNameException.class)
    public void testCreateFileWithNoRoot() throws BadFileNameException {
        try {
            String[] filePath = new String[]{"file1"};
            fileSystem.file(filePath, 5);
        }
        catch (BadFileNameException badFileNameException) {
            throw badFileNameException;
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = OutOfSpaceException.class)
    public void testCreateFileWithTooMuchMemory() throws OutOfSpaceException {
        try {
            String[] filePath = new String[]{"root", "file1"};
            fileSystem.file(filePath, 51);
        }
        catch (OutOfSpaceException outOfSpaceException) {
            throw outOfSpaceException;
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = BadFileNameException.class)
    public void testCreateFileWithNameOfExistingDirectory() throws BadFileNameException {
        try {
            String[] path = new String[]{"root", "dir1"};
            fileSystem.dir(path);
            fileSystem.file(path, 5);
        }
        catch (BadFileNameException badFileNameException) {
            throw badFileNameException;
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void lsdir() {
    }

    @Test
    public void removeFile() {
        try {
            String[] filePath = new String[]{"root", "file1"};
            fileSystem.file(filePath, 5);

            // check if file1 was created under root
            String[] subRoot = fileSystem.lsdir(new String[]{"root"});
            assertEquals("file1", subRoot[0]);

            fileSystem.rmfile(filePath);
            assertNull(fileSystem.FileExists(filePath));
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void removeNonExistentFile() {
        try {
            String[] filePath = new String[]{"root", "file1"};

            // check if file1 exists under root
            String[] subRoot = fileSystem.lsdir(new String[]{"root"});
            assertNull(subRoot);

            // check if file1 exists
            assertNull(fileSystem.FileExists(filePath));

            fileSystem.rmfile(filePath);
            assertNull(fileSystem.FileExists(filePath));
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void rmdir() {
    }

    @Test
    public void fileExists() {
    }

    @Test
    public void testDirExists() {
        try {
            String[] dirPath = new String[]{"root", "dir1"};
            fileSystem.dir(dirPath);

            // check if dir1 was created under root
            String[] subRoot = fileSystem.lsdir(new String[]{"root"});
            assertEquals("dir1", subRoot[0]);

            assertEquals("dir1", fileSystem.DirExists(dirPath).name);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testNonExistentDirExists() {
        try {
            String[] dirPath = new String[]{"root", "dir1"};

            // checks that dir1 doesn't exist under root
            String[] subRoot = fileSystem.lsdir(new String[]{"root"});
            assertNull(subRoot);

            assertNull(fileSystem.DirExists(dirPath));
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDirExistsWithExistingFileName() {
        try {
            String[] path = new String[]{"root", "file1"};
            fileSystem.file(path, 5);

            // checks that file1 exists under root
            String[] subRoot = fileSystem.lsdir(new String[]{"root"});
            assertEquals("file1", subRoot[0]);

            assertNull(fileSystem.DirExists(path));
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }
}