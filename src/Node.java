import java.util.*;

public class Node {
    public String value;
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
            if (canReachFromThisNeighbour(neighbour, destination, visited))
                return true;
        }
        return false;
    }

    private boolean canReachFromThisNeighbour(Node neighbour, Node destination, Set<Node> visited) {
        if(!visited.contains(neighbour)) {
            if (neighbour.canReach(destination, visited))
                return true;
        }
        return false;
    }

    public void connects(Node neighbour) {
        neighbours.add(neighbour);
    }

    public int findMinDistance(Node destination) {
        return findMinDistance(destination, new HashSet<>());
    }

    private int findMinDistance(Node destination, Set<Node> visited) {
        if(this==destination)
            return 0;

        visited.add(this);

        int minDistance = getInfinity();

        for(Node neighbour : neighbours) {
            if(!visited.contains(neighbour)) {
                Set<Node> newVisitedSet = new HashSet<>(visited);

                int distance = neighbour.findMinDistance(destination, newVisitedSet);
                if(distance != getInfinity()) {
                    distance++;
                    if(distance<minDistance) {
                        minDistance = distance;
                    }
                }

            }
        }

        return minDistance;
    }

    private int getInfinity(){
        return Integer.MAX_VALUE;
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
        hopCount.put(this, 0);

        Set<Node> visited = new HashSet<>();

        List<Node> resultPath = new ArrayList<>();
        Boolean found = false;

        while (!toProcessQueue.isEmpty()) {
            found = toProcessQueue.pollFirst().findShortestPathUtil(destination, visited, predecessorNode, hopCount, toProcessQueue);
            if(found)
                break;
        }

        if(found) {
            resultPath = extractPath(destination, predecessorNode);
        }

        return resultPath;
    }

    private boolean findShortestPathUtil(Node destination, Set<Node> visited, Map<Node, Node> predecessor, Map<Node, Integer> nodeHopCount, List<Node> toProcess) {

        if(visited.contains(this))
            return false;

        visited.add(this);

        if(this == destination)
            return true;

        for(Node neighbour : neighbours) {
            int hopCount = nodeHopCount.get(this)+1;
            if(!nodeHopCount.containsKey(neighbour)) {
                nodeHopCount.put(neighbour, hopCount);
                predecessor.put(neighbour, this);
                toProcess.add(neighbour);
            }
        }

        return false;
    }
}
