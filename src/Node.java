
import java.util.*;

public class Node {
    public String value;
    private List<Edge> neighbours;
    public static final int SELF_HOP = 0;

    public Node(String value, List<Edge> neighbours) {
        this.value = value;
        this.neighbours = neighbours;
    }

    public boolean canReach(Node destination) {
        return !findShortestPathBasedOnHopCount(destination).isEmpty();
    }

    public void connects(Node neighbour, double weight) {
        neighbours.add(new Edge(neighbour, weight));
    }


    private List<Node> extractPath(Node destination, Map<Node, Node> predecessor) {
        List<Node> resultPath = new ArrayList<>();
        Node predecessorNode = destination;
        do {
            resultPath.add(predecessorNode);
            predecessorNode = predecessor.get(predecessorNode);
        } while (predecessorNode != null);
        return resultPath;
    }

    public List<Node> findShortestPathBasedOnHopCount(Node destination) {
        Map<Node, Node> predecessorNode = new HashMap<>();
        Map<Node, Integer> hopCount = new HashMap<>();
        LinkedList<Node> toProcessQueue = new LinkedList<>();

        toProcessQueue.add(this);
        predecessorNode.put(this, null);
        hopCount.put(this, SELF_HOP);

        Set<Node> visited = new HashSet<>();

        List<Node> resultPath = new ArrayList<>();
        Boolean found = false;

        while (!toProcessQueue.isEmpty()) {
            Node nextNode = toProcessQueue.pollFirst();
            found = nextNode.BreadthFirstSearch(destination, visited, predecessorNode, hopCount, toProcessQueue);
            if(found)
                break;
        }

        if(found) {
            resultPath = extractPath(destination, predecessorNode);
        }

        return resultPath;
    }

    private boolean BreadthFirstSearch(Node destination, Set<Node> visited,
                                       Map<Node, Node> predecessor, Map<Node, Integer> nodeHopCount,
                                       List<Node> toProcess) {

        if(visited.contains(this))
            return false;

        visited.add(this);

        if(this == destination)
            return true;

        for(Edge neighbourEdge : neighbours) {
            Node neighbourNode = neighbourEdge.getNode();
            int hopCount = nodeHopCount.get(this)+1;
            if(!nodeHopCount.containsKey(neighbourNode)) {
                neighbourNode.updateNodeData(predecessor, nodeHopCount, toProcess, this, hopCount);
            }
        }
        return false;
    }

    private void updateNodeData(Map<Node, Node> predecessor, Map<Node, Integer> nodeHopCount, List<Node> toProcess,
                                Node predecessorNode, int hopCount) {
        nodeHopCount.put(this, hopCount);
        predecessor.put(this, predecessorNode);
        toProcess.add(this);
    }

    public List<Node> findShortestPathBasedOnWeight(Node destination) {
        List<Node> resultPath = new ArrayList<>();
        resultPath.add(this);
        for (Edge neighbourEdge : neighbours) {
            if(neighbourEdge.getNode().equals(destination))
                resultPath.add(destination);
        }
        return resultPath;
    }
}
