package system;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TreeTest {



    @Before
    public void initTree(){
        try {
            //root name named "root"
            String root = "root";
            //create new Tree with "root" as root name
            Tree tree = new Tree(root);
            //insert new nodes to the tree:
            String first,second,third;
            first = "mail";
            second = "docs";
            third = "java";
            tree.GetChildByName(first);
            tree.GetChildByName(second);
            tree.GetChildByName(third);
        }
        catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    public void getChildByName() {
        //root name named "root"
        String root = "root";
        //create new Tree with "root" as root name
        Tree tree = new Tree(root);
        //insert new nodes to the tree:
        String second;
        second = "docs";
        Tree checkTree = tree.GetChildByName(second);
        //checks if the GetChildByName method returns an Tree instance
        assertTrue(checkTree instanceof Tree);
    }
    @Test
    public void getTreeDepth(){
        //root name named "root"
        String root = "root";
        //create new Tree with "root" as root name
        Tree tree = new Tree(root);
        //insert new nodes to the tree:
        String first,second,third,forth,toInsert;
        int treeSize = 3;
        first = "mail";
        second = "docs";
        third = "java";
        toInsert = "test";
        forth = "unit testing";
        Tree firstTree = tree.GetChildByName(first);
        tree.GetChildByName(forth);
        Tree secondTree = firstTree.GetChildByName(second);
        Tree thirdTree  = secondTree.GetChildByName(third);
        //checks if the depth size of the tree getting bigger when inserting new node
        Tree insertionTree = thirdTree.GetChildByName(toInsert);
        assertEquals(treeSize + 1, insertionTree.depth);
    }

    @Test
    public void getMapSize(){
        //root name named "root"
        String root = "root";
        //create new Tree with "root" as root name
        Tree tree = new Tree(root);
        //insert new nodes to the tree:
        String first, second;
        first = "mail";
        second = "unit testing";
        tree.GetChildByName(first);
        tree.GetChildByName(second);
        //checks if the map size of the parent is 2 (the children are first && forth)
        assertEquals(2, tree.children.size());
    }

    // TODO : UNCOMMENT THIS BEFORE HANDING OVER THE ASSIGNMENT !!!
//    @Test
//    public void getPath() {
//        String root = "root";
//        String second = "mail";
//        String third = "java";
//        Tree tree = new Tree(root);
//        Tree first = tree.GetChildByName(second);
//        Tree secondTree = first.GetChildByName(third);
//        String[] secondTreePath = secondTree.getPath();
//        assertTrue(secondTreePath[0].equals("root") && secondTreePath[1].equals("mail")
//                && secondTreePath[2].equals("java"));
//    }

    // TODO : DELETE THIS BEFORE HANDING OVER THE ASSIGNMENT !!!
    @Test
    public void getPath() {
        String root = "root";
        String second = "mail";
        String third = "java";
        Tree tree = new Tree(root);
        Tree first = tree.GetChildByName(second);
        Tree secondTree = first.GetChildByName(third);
        String[] secondTreePath = secondTree.getPath();
        assertTrue(secondTreePath[0].equals("mail")
                && secondTreePath[1].equals("java"));
    }
}