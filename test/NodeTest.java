import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NodeTest {
    @Test
    public void shouldReturnTrueWhenANodeCanBeReachedFromItself() {
        Node nodeOne = new Node("a", new ArrayList<>());
        assertThat(nodeOne.canReach(nodeOne),is(equalTo(true)));
    }

    @Test
    public void shouldReachTheNeighbour() {
        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        nodeA.connects(nodeB, 0.0);
        assertThat(nodeA.canReach(nodeB),is(equalTo(true)));
    }

    @Test
    public void shouldReachNeighboursNeighbour() {
        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        Node nodeC = new Node("c", new ArrayList<>());
        nodeA.connects(nodeB, 0.0);
        nodeB.connects(nodeC, 0.0);
        assertThat(nodeA.canReach(nodeC),is(equalTo(true)));
    }

    @Test
    public void shouldReachNodeCAvoidingCycle() {
        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        Node nodeC = new Node("c", new ArrayList<>());
        nodeA.connects(nodeB, 0.0);
        nodeB.connects(nodeA, 0.0);
        nodeB.connects(nodeC, 0.0);
        assertThat(nodeA.canReach(nodeC),is(equalTo(true)));
    }

    @Test
    public void shouldReachANodeInAComplexGraph() {

        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        Node nodeC = new Node("c", new ArrayList<>());
        Node nodeD = new Node("d", new ArrayList<>());
        Node nodeE = new Node("e", new ArrayList<>());
        Node nodeF = new Node("f", new ArrayList<>());
        Node nodeG = new Node("g", new ArrayList<>());
        Node nodeH = new Node("h", new ArrayList<>());

        nodeA.connects(nodeF, 0.0);
        nodeB.connects(nodeC, 0.0);
        nodeB.connects(nodeA, 0.0);
        nodeC.connects(nodeD, 0.0);
        nodeC.connects(nodeE, 0.0);
        nodeC.connects(nodeE, 0.0);
        nodeD.connects(nodeE, 0.0);
        nodeE.connects(nodeB, 0.0);
        nodeH.connects(nodeB, 0.0);

        assertTrue(nodeH.canReach(nodeE));
        assertFalse(nodeH.canReach(nodeG));
        assertTrue(nodeB.canReach(nodeD));
    }

    @Test
    public void shouldReturnEmptyListIfTheSourceAndDestinationNodesAreSame() {
        Node nodeA = new Node("a", new ArrayList<>());
        assertEquals(nodeA, nodeA.findShortestPathBasedOnHopCount(nodeA).get(0));
    }

    @Test
    public void shortestPathShouldContainNodeAAndNodeB() {
        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());

        nodeA.connects(nodeB, 0.0);

        List<Node> shortestPath = nodeA.findShortestPathBasedOnHopCount(nodeB);

        assertTrue(shortestPath.contains(nodeA));
        assertTrue(shortestPath.contains(nodeB));
    }

    @Test
    public void shouldReturnMinimumHopCountWhenSeveralPathsExist() {
        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        Node nodeC = new Node("c", new ArrayList<>());
        Node nodeD = new Node("d", new ArrayList<>());
        nodeA.connects(nodeB, 0.0);
        nodeB.connects(nodeC, 0.0);
        nodeC.connects(nodeD, 0.0);
        nodeA.connects(nodeC, 0.0);

        List<Node> shortestPath = nodeA.findShortestPathBasedOnHopCount(nodeD);

        assertTrue(shortestPath.contains(nodeA));
        assertTrue(shortestPath.contains(nodeC));
        assertTrue(shortestPath.contains(nodeD));
    }

    @Test
    public void shouldFindMinHopCountAvoidingCycle() {
        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        Node nodeC = new Node("c", new ArrayList<>());
        nodeA.connects(nodeB, 0.0);
        nodeB.connects(nodeA, 0.0);
        nodeB.connects(nodeC, 0.0);

        List<Node> shortestPath = nodeA.findShortestPathBasedOnHopCount(nodeC);

        assertTrue(shortestPath.contains(nodeA));
        assertTrue(shortestPath.contains(nodeB));
        assertTrue(shortestPath.contains(nodeC));
    }


    @Test
    public void shouldConsiderAlternatePathThroughSameNode() {
        Node nodeA = new Node("A", new ArrayList<>());
        Node nodeB = new Node("B", new ArrayList<>());
        Node nodeC = new Node("C", new ArrayList<>());
        Node nodeD = new Node("D", new ArrayList<>());
        Node nodeE = new Node("E", new ArrayList<>());
        Node nodeF = new Node("F", new ArrayList<>());

        nodeA.connects(nodeB, 0.0);
        nodeB.connects(nodeC, 0.0);
        nodeC.connects(nodeD, 0.0);
        nodeD.connects(nodeE, 0.0);
        nodeE.connects(nodeF, 0.0);
        nodeB.connects(nodeD, 0.0);

        List<Node> shortestPath = nodeA.findShortestPathBasedOnHopCount(nodeF);

        assertTrue(shortestPath.contains(nodeA));
        assertTrue(shortestPath.contains(nodeB));
        assertTrue(shortestPath.contains(nodeE));
        assertTrue(shortestPath.contains(nodeF));

    }

    @Test
    public void shouldReturnAListWithNodeAWhenInvokedWithDestinationNodeAFromNodeA() {
        Node nodeA = new Node("A",new ArrayList<>());

        List<Node> shortestPath = new ArrayList<>(Arrays.asList(nodeA));

        assertEquals(shortestPath,nodeA.findShortestPathBasedOnWeight(nodeA));

    }

    @Test
    public void shouldReturnAListWithNodeAAndNodeBIfThereIsOnlyOnePathFromNodeAtoNodeBandBodeBIsNeighbourOfNodeA() {
        Node nodeA = new Node("A",new ArrayList<>());
        Node nodeB = new Node("B",new ArrayList<>());

        nodeA.connects(nodeB, 10);

        List<Node> shortestPath = new ArrayList<>(Arrays.asList(nodeA, nodeB));

        assertEquals(shortestPath, nodeA.findShortestPathBasedOnWeight(nodeB));
    }


}
