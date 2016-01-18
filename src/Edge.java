
public class Edge implements Comparable<Edge> {
    private final Node node;
    private final double weight;

    public Edge(Node node, double weight) {

        this.node = node;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge that) {

        return Double.compare(this.weight,that.weight);
    }

    public Node getNode() {
        return node;
    }

    public double getWeight() {
        return weight;
    }
}
