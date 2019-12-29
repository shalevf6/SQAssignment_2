package system;

import org.junit.*;
import java.util.Random;
import static org.junit.Assert.*;

public class SpaceTest {

    private static Leaf file;
    private static Space space;
    private static int spaceSize;
    private static int fileSize;

    @Before
    public void createSpaceAndFile() {
        try {
            // get a random number between 20 - 50
            Random random = new Random();
            spaceSize = random.nextInt(30) + 21;
            FileSystem.fileStorage = new Space(spaceSize);
            space = FileSystem.fileStorage;

            // get a random number between 10 - 20
            fileSize = random.nextInt(10) + 11;
            Tree fileParent = new Tree("root");
            file = new Leaf("file1", fileSize);
            file.parent = fileParent;
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testBuilderWithOnlyEmptyBlocks() {

        // get a random number between 1 - 50
        Random random = new Random();
        int randomNumber = random.nextInt(50) + 1;

        Space space = new Space(randomNumber);

        // check if the space is clean of files and as the given size
        assertEquals(randomNumber, space.countFreeSpace());
        assertEquals(randomNumber, space.getAlloc().length);

        // check if the space is clean of files
        Leaf[] blocks = space.getAlloc();
        for (Leaf leaf : blocks) {
            assertNull(leaf);
        }
    }

    @Test
    public void testFileAllocationsNotNull() {
        Leaf[] blocks = space.getAlloc();
        int[] fileAllocations = file.allocations;
        for (int fileAllocation : fileAllocations) {
            assertNotNull(blocks[fileAllocation]);
        }
    }

    @Test
    public void testFileSizeRemovedFromFreeSpace() {
        assertEquals(fileSize, spaceSize - space.countFreeSpace());
    }

    @Test
    public void testBlocksContainsOnlyFileSizeBlocks() {
        Leaf[] blocks = space.getAlloc();
        int fileBlocks = 0;
        for (Leaf leaf : blocks) {
            if (leaf == file)
                fileBlocks++;
        }
        assertEquals(fileSize, fileBlocks);
    }

    @Test
    public void testBlocksFreedAfterDealloc() {
        space.Dealloc(file);
        assertEquals(spaceSize, space.countFreeSpace());
        Leaf[] blocks = space.getAlloc();
        int emptyBlocks = 0;
        for (Leaf leaf : blocks) {
            if (leaf == null)
                emptyBlocks++;
        }
        assertEquals(spaceSize, emptyBlocks);
    }

    @Test(expected = OutOfSpaceException.class)
    public void testOutOfSpaceExceptionWithLargeFile() throws OutOfSpaceException {
        Leaf bigFile = new Leaf("file2", 100);
        fail("Expected \"OutOfSpaceException\"");
    }
}