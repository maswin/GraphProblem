import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<String, List<String>> edges;

    public Graph(Map<String, List<String>> edges) {
        this.edges = edges;
    }

    public boolean hasPath(String startNode, String endNode) {
        Map<String, Boolean> visited = new HashMap<>();
        for(String node : edges.keySet()) {
            visited.put(node, Boolean.FALSE);
        }
        return performDFS(startNode, endNode, visited);
    }

    private boolean performDFS(String root, String endNode, Map<String, Boolean> visited) {
        if(visited.get(root)) {
            return false;
        }

        if(root.equals(endNode)) {
            return true;
        }

        visited.put(root, true);

        List<String> neighbouringNodes = edges.get(root);
        for(String neighbouringNode : neighbouringNodes) {
            if(!visited.get(neighbouringNode)) {
                boolean found = performDFS(neighbouringNode, endNode, visited);
                if(found) {
                    return true;
                }
            }
        }
        return false;
    }
}
