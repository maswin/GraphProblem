import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class NodeTest {
    @Test
    public void shouldReturnTrueIfTwoNodesHaveTheSameValue() {
        Node nodeOne = new Node("a", new ArrayList<>());
        assertThat(nodeOne.canReach(nodeOne),is(equalTo(true)));
    }

    @Test
    public void shouldReachTheNeighbour() {
        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        nodeA.connects(nodeB);
        assertThat(nodeA.canReach(nodeB),is(equalTo(true)));
    }

    @Test
    public void shouldReachNeighboursNeighbour() {
        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        Node nodeC = new Node("c", new ArrayList<>());
        nodeA.connects(nodeB);
        nodeB.connects(nodeC);
        assertThat(nodeA.canReach(nodeC),is(equalTo(true)));
    }

    @Test
    public void shouldReachANodeCAvoidingCycle() {
        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        Node nodeC = new Node("c", new ArrayList<>());
        nodeA.connects(nodeB);
        nodeB.connects(nodeA);
        nodeB.connects(nodeC);
        assertThat(nodeA.canReach(nodeC),is(equalTo(true)));
    }

    @Test
    public void shouldReachANodeInaComplexGraph() {

        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        Node nodeC = new Node("c", new ArrayList<>());
        Node nodeD = new Node("d", new ArrayList<>());
        Node nodeE = new Node("e", new ArrayList<>());
        Node nodeF = new Node("f", new ArrayList<>());
        Node nodeG = new Node("g", new ArrayList<>());
        Node nodeH = new Node("h", new ArrayList<>());

        nodeA.connects(nodeF);
        nodeB.connects(nodeC);
        nodeB.connects(nodeA);
        nodeC.connects(nodeD);
        nodeC.connects(nodeE);
        nodeC.connects(nodeE);
        nodeD.connects(nodeE);
        nodeE.connects(nodeB);
        nodeH.connects(nodeB);

        assertTrue(nodeH.canReach(nodeE));
        assertFalse(nodeH.canReach(nodeG));
        assertTrue(nodeB.canReach(nodeD));
    }
}
