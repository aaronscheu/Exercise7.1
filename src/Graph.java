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

    public void getNeighbour() {

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
        Graph graph = new Graph(10, 15);
        graph.printAdjacencyList();
    }

}
