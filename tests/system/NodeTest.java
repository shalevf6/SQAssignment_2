package system;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NodeTest {

    @Before
    public void init(){
        try {
            String root = "root";
            Tree tree = new Tree(root);
        }
        catch (Exception e ){
            fail(e.getMessage());
        }
    }

    @Test
    public void getPath() {
        String root = "root";
        String second = "mail";
        String third = "java";
        Tree tree = new Tree(root);
        Tree first = tree.GetChildByName(second);
        Tree secondTree = first.GetChildByName(third);
        String[] secondTreePath = secondTree.getPath();
        assertTrue(secondTreePath[0].equals("root") && secondTreePath[1].equals("mail")
                && secondTreePath[2].equals("java"));
    }
}