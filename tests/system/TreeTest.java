package system;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TreeTest {

    @Before
    public void initConstructorCheck(){
        String emptyString = "";
    }

    @Before
    public void initTree(){
        try {
            //root name named "root"
            String root = "root";
            //create new Tree with "root" as root name
            Tree tree = new Tree(root);
            //insert new nodes to the tree:
            String first,second,third;
            int treeSize = 3;
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
        assertTrue(treeSize + 1 ==insertionTree.depth);
    }

    @Test
    public void getMapSize(){
        //root name named "root"
        String root = "root";
        //create new Tree with "root" as root name
        Tree tree = new Tree(root);
        //insert new nodes to the tree:
        String first,second,third,forth,toInsert;
        int treeSize = 3;
        first = "mail";
        forth = "unit testing";
        Tree firstTree = tree.GetChildByName(first);
        tree.GetChildByName(forth);
        //checks if the map size of the parent is 2 (the children are first && forth)
        assertTrue(2==tree.children.size());
    }
}