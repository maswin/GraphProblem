import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class EdgeTest {

    @Test
    public void shouldReturn1IfEdgeComparedToThatEdgeHasLesserWeight() {
        Node node1 = mock(Node.class);
        Edge edge1 = new Edge(node1, 10);

        Node node2 = mock(Node.class);
        Edge edge2 = new Edge(node2, 8);

        assertEquals(1, edge1.compareTo(edge2));
    }

    @Test
    public void shouldReturnMinus1IfEdgeComparedToThatEdgeHasHigherWeight() {
        Node node1 = mock(Node.class);
        Edge edge1 = new Edge(node1, 10);

        Node node2 = mock(Node.class);
        Edge edge2 = new Edge(node2, 20);

        assertEquals(-1, edge1.compareTo(edge2));
    }

    @Test
    public void shouldReturn0IfEdgeComparedToThatEdgeHasEqualWeight() {
        Node node1 = mock(Node.class);
        Edge edge1 = new Edge(node1, 10);

        Node node2 = mock(Node.class);
        Edge edge2 = new Edge(node2, 10);

        assertEquals(0, edge1.compareTo(edge2));
    }

}
