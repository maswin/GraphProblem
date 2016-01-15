import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {
    private String value;
    private List<Node> neighbours;

    public Node(String value, List<Node> neighbours) {
        this.value = value;
        this.neighbours = neighbours;
    }

    public boolean canReach(Node destination) {
        return canReach(destination, new HashSet<>());
    }

    private boolean canReach(Node destination, Set<Node> visited) {
        visited.add(this);

        if(this==destination)
            return true;

        for(Node neighbour : neighbours) {
            if(!visited.contains(neighbour)) {
                if (neighbour.canReach(destination, visited))
                    return true;
            }
        }
        return false;
    }

    public void connects(Node neighbour) {
        neighbours.add(neighbour);
    }
}
