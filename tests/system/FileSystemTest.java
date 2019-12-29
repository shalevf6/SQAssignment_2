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
        //checks if the free space in the file system is in the same space that allocated to the file system
        assertTrue(fileSystem.fileStorage.getAlloc().length==fileSystemSize);

    }
    @Test
    public void checkLeafsOnDisk(){
        boolean toCheckIfNull = true;
        Leaf[] leaf = fileSystem.fileStorage.getAlloc();
        for( int i = 0 ; i < leaf.length ; i++){
            if( leaf[i] != null)
                toCheckIfNull = false;
        }
        //if all leafs are null, the creation of the disk work as should be
        assertTrue(toCheckIfNull);
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
    public void file() {
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
    public void rmfile() {
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
    public void dirExists() {

        }

}