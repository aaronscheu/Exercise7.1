import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by amaridev on 05/02/16.
 * Package: PACKAGE_NAME for Exercise7.1.
 */
public class Graph {

    private List<Pair<Integer, Integer>>[] adjacency;
    private String[] labels;

    private final int MAX_WEIGHT = 50;

    public Graph(int nrOfvertices) {
        this.adjacency = new LinkedList[nrOfvertices];
        this.labels = new String[nrOfvertices];

        for (int i = 0; i < adjacency.length; i++) {
            adjacency[i] = new LinkedList<>();
        }
    }

    public Graph (int vertices, int edges) {
        this(vertices);
        getRandomGraph(edges);
    }


    public void addLabel(String label, int vertex) {
        labels[vertex] = label;
    }

    public void addEdge(int from, int to, int weight) {
        adjacency[from].add(new Pair<>(to, weight));
    }

    public boolean removeEdge(int startVertex, Pair<Integer, Integer> edge) {
        return adjacency[startVertex].remove(edge);
    }


    /**
     * Get all Neighbours from vertex as sorted List.
     * @param vertex -> Origin Vertex
     * @param pos    -> Position of closes Neighbour; pos 0 for closest; pos 1 for second closest; ...
     * @return       -> Integer of Vertex Index
     */
    private int getNeighbour(int vertex, int pos) {

        ArrayList<Pair> sorted = new ArrayList<>(
                adjacency[vertex]
                .stream()
                .sorted( (e1, e2) -> Integer.compare(e1.getValue(), e2.getValue()))
                .collect(Collectors.toList())
        );

        return (int) sorted.get(pos).getKey();
    }

    private int getClosestNeighbour(int vertex) {
        return getNeighbour(vertex, 0);
    }

    private int getMinDistNotVisited(int[] dist, boolean[] visited) {
        // Initialize min value
        int min = Integer.MAX_VALUE;
        int min_index = -1;

        for (int i = 0; i < dist.length; i++)

            if (!visited[i] && dist[i] <= min) {
                min = dist[i];
                min_index = i;
            }

        return min_index;
    }

    private boolean containsEdge(int pos, int vertex) {
        boolean found = false;

        for (Pair edge : adjacency[pos])
            if (edge.getKey().equals(vertex))
                found = true;

        return found;
    }


    public void pathfinder(int start, int end) {

        int length = adjacency.length;
        int[] dist = new int[length];
        boolean[] visited = new boolean[length];
        //int[] previous = new int[adjacency.length];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(visited, false);
        //Arrays.fill(previous, -1);
        dist[start] = 0;

        // Find shortest path for all vertices
        for (int count = 0; count < length ; count++)
        {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to start in first
            // iteration.
            int u = getMinDistNotVisited(dist, visited);

            // Mark the picked vertex as processed
            visited[u] = true;

            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < length; v++)

                // Update dist[v] only if is not in visited, there is an
                // edge from u to v, and total weight of path from start to
                // v through u is smaller than current value of dist[v]
                if (containsEdge(v, u))
                    if (!visited[v]
                            && dist[u] != Integer.MAX_VALUE
                            && dist[u] + adjacency[v].get(u).getValue() < dist[v])
                        dist[v] = dist[u] + adjacency[v].get(u).getValue();
        }

        dist.toString();

    }


    private void getRandomGraph(int maxEdges) {
        ArrayList<Pair<Integer, Integer>> edge = IntStream.range(0, maxEdges)
                .mapToObj(element -> new Pair<>(
                new Random().nextInt(adjacency.length),
                new Random().nextInt(MAX_WEIGHT +1)))
                .collect(Collectors.toCollection(ArrayList<Pair<Integer, Integer>>::new));

        while (edge.size() > 0) {
            int index = new Random().nextInt(adjacency.length);
            if (index != edge.get(0).getKey())
                adjacency[index].add(edge.remove(0));
        }

    }

    public void printAdjacencyList() {
        int i = 0;

        for (List< Pair<Integer, Integer> > list : adjacency) {
            System.out.print("adjacencyList[" + i + "] -> ");

            for (Pair<Integer, Integer> edge : list) {
                System.out.print(edge.getKey() + "(" + edge.getValue() + ") ");
            }

            ++i;
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(10, 25);
        graph.printAdjacencyList();

        graph.pathfinder(0, 5);
    }

}
