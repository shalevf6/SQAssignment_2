package system;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.DirectoryNotEmptyException;
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
        String [][] disk = fileSystem.disk();
        int size = disk.length;
        boolean checkIfNull = true;
        for(int i = 0 ; i < size ; i++){
            if(disk[i]!= null)
                checkIfNull = false;
        }
        //checks if the free space in the disk is in the same space that allocated to the file system && if all disk slots are null
        assertTrue(checkIfNull && size == fileSystemSize);

    }
    @Test
    public void checkLeafsOnDisk() throws OutOfSpaceException, BadFileNameException {
        String[] file = new String[2];
        boolean checkIfExist = false;
        file[0] = "root";
        file[1] = "idan";
        fileSystem.file(file,2);
        String[][] disk = fileSystem.disk();
        for(int i = 0 ; i< disk.length ; i++){
            if(disk[i]!=null) {
                if (disk[i][0].equals("root") && disk[i][1].equals("idan"))
                    checkIfExist = true;
            }
        }
        //checks if the file path of the file that we inserted is exist in the disk
        assertTrue(checkIfExist);
    }
    @Test
    public void fileCheck() throws OutOfSpaceException, BadFileNameException {
        String[] file = new String[2];
        file[0] = "root";
        file[1] = "idan";
        fileSystem.file(file,2);
        Leaf[] leaf = fileSystem.fileStorage.getAlloc();
        boolean isFileAllocated = false;
        for (int i = 0 ; i < leaf.length ; i++ ){
            if( leaf[i]!= null && leaf[i].name.equals("idan"))
                isFileAllocated = true;
        }
        //if the file name that inserted is allocated in the file system, return true
        assertTrue(isFileAllocated);

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
    public void nullLsdir() {

        String[] toInsert =  new String[1];
        toInsert[0] = "mailBox";
        // inserting string array that doesn't appear in the file system, should return null
        assertTrue(fileSystem.lsdir(toInsert)==null);
    }

    @Test
    public void lsDir() throws BadFileNameException, OutOfSpaceException {
        String first,seconed,third;
        first = "root";
        seconed = "mail";
        third = "gmail";
        String[] toInsert = new String[2];
        String [] toInsert2 = new String[3];
        String [] toInsert3 = new String[4];
        String [] toInsert4 = new String[4];
        toInsert4[0] = first;
        toInsert4[1] = seconed;
        toInsert4[2] = third;
        toInsert4[3] = "shani";
        toInsert3[0] = first;
        toInsert3[1] = seconed;
        toInsert3[2] = third;
        toInsert3[3] = "idan";
        toInsert[0] =first;
        toInsert[1] = seconed;
        toInsert2[0] = first;
        toInsert2[1] = seconed;
        toInsert2[2] = third;
        fileSystem.dir(toInsert);
        fileSystem.dir(toInsert2);
        fileSystem.file(toInsert4,2);
        fileSystem.file(toInsert3,3);
        boolean checkDirs = true;
        String[] check = fileSystem.lsdir(toInsert2);
        if(check[0].equals("idan") && check[1].equals("shani") )
            checkDirs = true;
        else
            checkDirs = false;
        //if the  string array returned as mentioned checkDirs should be true
        assertTrue(checkDirs);

    }

    @Test(expected =   DirectoryNotEmptyException.class)
    public void rmdir() throws DirectoryNotEmptyException, BadFileNameException, OutOfSpaceException {
        String first,seconed,third;
        first = "root";
        seconed = "mail";
        third = "gmail";
        String[] toInsert = new String[2];
        String [] toInsert2 = new String[3];
        String [] toInsert3 = new String[4];
        String [] toInsert4 = new String[4];
        toInsert4[0] = first;
        toInsert4[1] = seconed;
        toInsert4[2] = third;
        toInsert4[3] = "shani";
        toInsert3[0] = first;
        toInsert3[1] = seconed;
        toInsert3[2] = third;
        toInsert3[3] = "idan";
        toInsert[0] =first;
        toInsert[1] = seconed;
        toInsert2[0] = first;
        toInsert2[1] = seconed;
        toInsert2[2] = third;
        fileSystem.dir(toInsert);
        fileSystem.dir(toInsert2);
        fileSystem.file(toInsert4,2);
        fileSystem.file(toInsert3,3);
        //expected to get directory not null exception
        fileSystem.rmdir(toInsert2);
    }
    @Test
    public void rmdirGoodInputCheck() throws BadFileNameException, DirectoryNotEmptyException {
        String first,seconed,third;
        first = "root";
        seconed = "mail";
        third = "gmail";
        String[] toInsert = new String[2];
        String [] toInsert2 = new String[3];
        toInsert[0] =first;
        toInsert[1] = seconed;
        toInsert2[0] = first;
        toInsert2[1] = seconed;
        toInsert2[2] = third;
        fileSystem.dir(toInsert);
        fileSystem.dir(toInsert2);
        //delete the dir "gmail"
        fileSystem.rmdir(toInsert2);
        Tree tree = fileSystem.DirExists(toInsert2 );
        //checks if the dir is not in the file system after the rm action
        assertTrue(tree ==null);
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
    public void fileExistsWithBadPath() throws BadFileNameException {
        String first,seconed,third;
        first = "root";
        seconed = "mail";
        third = "gmail";
        String[] toInsert = new String[2];
        String [] toInsert2 = new String[3];
        String [] toInsert3 = new String[4];
        toInsert[0] =first;
        toInsert[1] = seconed;
        toInsert2[0] = first;
        toInsert2[1] = seconed;
        toInsert2[2] = third;
        toInsert3[0] = first;
        toInsert3[1] = seconed;
        toInsert3[2] = third;
        toInsert3[3] = "idan";
        fileSystem.dir(toInsert);
        fileSystem.dir(toInsert2);
        //check if with bad path, the file system will return null, because that the file is not exist
        assertTrue(fileSystem.FileExists(toInsert3)==null);
    }

    @Test
    public void fileExistsWithDirPath() throws BadFileNameException, OutOfSpaceException {
        String first,seconed,third;
        first = "root";
        seconed = "mail";
        third = "gmail";
        String[] toInsert = new String[2];
        String [] toInsert2 = new String[3];
        toInsert[0] =first;
        toInsert[1] = seconed;
        toInsert2[0] = first;
        toInsert2[1] = seconed;
        toInsert2[2] = third;
        fileSystem.dir(toInsert);
        fileSystem.dir(toInsert2);
        //check if with bad path, the file system will return null, because that the path is to a directory and not for a file
        assertTrue(fileSystem.FileExists(toInsert2)==null);
    }

    @Test
    public void fileExistsWithFilePath() throws BadFileNameException, OutOfSpaceException {
        String first, seconed, third;
        first = "root";
        seconed = "mail";
        third = "gmail";
        String[] toInsert = new String[2];
        String[] toInsert2 = new String[3];
        toInsert[0] = first;
        toInsert[1] = seconed;
        toInsert2[0] = first;
        toInsert2[1] = seconed;
        toInsert2[2] = third;
        fileSystem.dir(toInsert);
        fileSystem.file(toInsert2, 2);
        //check if with good path, the file system will not return null, because that the path is valid
        assertTrue(fileSystem.FileExists(toInsert2) != null);
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