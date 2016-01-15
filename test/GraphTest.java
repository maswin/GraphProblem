import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class GraphTest {

    private Map<String, List<String>> edges;
    private Graph graph;

    @Before
    public void setUp() throws Exception {
        edges = new HashMap<>();
        edges.put("a", Arrays.asList("f"));
        edges.put("b", Arrays.asList("c", "a"));
        edges.put("c", Arrays.asList("d", "e", "e"));
        edges.put("d", Arrays.asList("e"));
        edges.put("e", Arrays.asList("b"));
        edges.put("f", Arrays.asList());
        edges.put("g", Arrays.asList());
        edges.put("h", Arrays.asList("b"));
        graph = new Graph(edges);
    }

    @Test
    public void shouldReturnTrueIfTheNodeIsInNeighbouringNodeListOfGivenNode() {
        assertTrue(graph.hasPath("b", "a"));
    }

    @Test
    public void shouldReturnTrueIfTheNodeCanBeReachedInTwoHopsFromAGivenNode() throws Exception {
        assertTrue(graph.hasPath("b", "d"));
    }

    @Test
    public void shouldReturnTrueIfTheNodeCanBeReachedInAnyNumberOfHopsFromAGivenNode() throws Exception {
        assertTrue(graph.hasPath("h", "d"));
    }

    @Test
    public void shouldReturnFalseIfNodeIsUnReachable() throws Exception {
        assertFalse(graph.hasPath("g", "a"));
    }

    @Test
    public void shouldAvoidCycleWhenFindingIfPathExist() throws Exception {
        assertFalse(graph.hasPath("h", "g"));
    }
}
