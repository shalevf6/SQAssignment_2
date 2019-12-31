package system;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LeafTest {

    FileSystem fileSystem;
    int fileSystemSize;

    @Before
    public void createFileSystem() {
        try {
            fileSystemSize = 20;
            fileSystem = new FileSystem(fileSystemSize);

            String[] filePath = {"root", "dir1", "file1"};
            fileSystem.file(filePath, 6);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = OutOfSpaceException.class)
    public void testNewLeafWithNoSpace() throws OutOfSpaceException {
        try {
            Leaf file = new Leaf("file2", 15);
        }
        catch (OutOfSpaceException outOfSpaceException) {
            throw outOfSpaceException;
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
