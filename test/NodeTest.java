import org.junit.Test;

import java.util.ArrayList;
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
    public void shouldReachNodeCAvoidingCycle() {
        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        Node nodeC = new Node("c", new ArrayList<>());
        nodeA.connects(nodeB);
        nodeB.connects(nodeA);
        nodeB.connects(nodeC);
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

    @Test
    public void shouldReturn0WhenANodeIsReachedFromItself() {
        Node nodeA = new Node("a",new ArrayList<>());
        assertEquals(0,nodeA.findMinDistance(nodeA));
    }

    @Test
    public void shouldReturn1WhenANodeIsItsImmediateNeighbour() {
        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        nodeA.connects(nodeB);
        assertEquals(1,nodeA.findMinDistance(nodeB));
    }

    @Test
    public void shouldReturnMinimumDistanceAs2WhenDestinationIsReachedInTwoHops() {
        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        Node nodeC = new Node("c", new ArrayList<>());
        nodeA.connects(nodeB);
        nodeB.connects(nodeC);

        assertEquals(2,nodeA.findMinDistance(nodeC));
    }

    @Test
    public void shouldReturnMinimumDistanceAs2WhenDestinationIsReachedInTwoWays() {
        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        Node nodeC = new Node("c", new ArrayList<>());
        nodeA.connects(nodeB);
        nodeB.connects(nodeC);
        nodeA.connects(nodeC);
        assertEquals(1,nodeA.findMinDistance(nodeC));
    }

    @Test
    public void shouldReturnMinimumDistanceAs2WhenSeveralPathsExist() {
        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        Node nodeC = new Node("c", new ArrayList<>());
        Node nodeD = new Node("c", new ArrayList<>());
        nodeA.connects(nodeB);
        nodeB.connects(nodeC);
        nodeC.connects(nodeD);
        nodeA.connects(nodeC);
        assertEquals(2,nodeA.findMinDistance(nodeD));
    }

    @Test
    public void shouldReturnIntegerMaxValueWhenNoPathExist() {
        Node nodeA = new Node("a", new ArrayList<>());
        Node nodeB = new Node("b", new ArrayList<>());
        Node nodeC = new Node("c", new ArrayList<>());
        Node nodeD = new Node("c", new ArrayList<>());
        nodeA.connects(nodeB);
        nodeB.connects(nodeC);
        assertEquals(Integer.MAX_VALUE,nodeA.findMinDistance(nodeD));
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

        nodeA.connects(nodeB);

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
        nodeA.connects(nodeB);
        nodeB.connects(nodeC);
        nodeC.connects(nodeD);
        nodeA.connects(nodeC);

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
        nodeA.connects(nodeB);
        nodeB.connects(nodeA);
        nodeB.connects(nodeC);

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

        nodeA.connects(nodeB);
        nodeB.connects(nodeC);
        nodeC.connects(nodeD);
        nodeD.connects(nodeE);
        nodeE.connects(nodeF);
        nodeB.connects(nodeD);

        List<Node> shortestPath = nodeA.findShortestPathBasedOnHopCount(nodeF);

        assertTrue(shortestPath.contains(nodeA));
        assertTrue(shortestPath.contains(nodeB));
        assertTrue(shortestPath.contains(nodeE));
        assertTrue(shortestPath.contains(nodeF));

    }
}
