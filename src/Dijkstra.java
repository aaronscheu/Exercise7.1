import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Dijkstra {

    private Graph graph;
    private int[] distance;
    private boolean[] visited;
    private int[] parents;
    private int startNode;
    private int endNode;

    public Dijkstra(Graph graph, int startNode, int endNode) {
        this.graph = graph;
        distance = new int[graph.getAdjList().length];
        visited = new boolean[graph.getAdjList().length];
        parents = new int[graph.getAdjList().length];
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public void findPath(boolean isUnweighted) {
        if (endNode == startNode) {
            System.out.println(
                    "Der Ausgangsknoten  " + startNode + "  und der Zielknoten " + endNode + " sind identisch.");
            return;
        }
        System.out.println("Startknoten : " + startNode);
        System.out.println("Endknoten :  " + endNode);
        int[][] adjList = graph.getAdjList();
        int[][] graphForPathFinding = new int[adjList.length][adjList.length];

        if (isUnweighted) {
            // Alle Gewichte auf 1
            graphForPathFinding = convertGraphToUnweighted(graphForPathFinding);
        } else
            graphForPathFinding = adjList;
        // Initializierung
        for (int i = 0; i < adjList.length; i++) {
            parents[i] = -1;
            visited[i] = false;
            distance[i] = Integer.MAX_VALUE;
        }

        distance[startNode] = 0;
        for (int i = 0; i < graphForPathFinding.length; i++) {

            int nextNode = getMinDistance();
            if (nextNode == -1) { // Kein Weg gefunden
                System.out.println(
                        "Es konnte kein Pfad zwischen " + startNode + " und " + endNode + " ermittelt werden.");
                return;
            }
            visited[nextNode] = true;


            if (nextNode == endNode) {
                //parents[i] = nextNode;
                printResults();
                return; // Wenn Ziel erreicht, abbrechen
            }
            int[] neighbors = graph.getNeighbors(nextNode);
            for (int j = 0; j < neighbors.length; j++) {

                //				 if (!visited[j] && neighbors[j] > 0 && distance[nextNode] !=
                //				 Integer.MAX_VALUE
                //				 && distance[nextNode] + neighbors[j] < distance[j]){
                //
                //
                //
                //				 distance[j] = distance[nextNode] + neighbors[j];
                //				 parents[i] = nextNode;
                //				 }

                //				}

                // IN neighbors sind nur ECHTE Nachbarn drin, also keine
                // pruefung auf -1 noetig
                // Ist der gerade verarbeitete Pfad günstiger?
                if (distance[neighbors[j]] > (distance[nextNode] + adjList[nextNode][neighbors[j]])) {
                    // Wenn ja...
                    // In die Pfadliste eintragen
                    parents[neighbors[j]] = nextNode;
                    // Distanzliste aktualisieren
                    distance[neighbors[j]] = distance[nextNode] + graphForPathFinding[nextNode][neighbors[j]];
                }
            }

        }

    }

    private int getMinDistance() {
        int min = Integer.MAX_VALUE;
        int min_index = -1;

        for (int i = 0; i < graph.getAdjList().length; i++) {
            if (visited[i] == false && distance[i] <= min) {
                min = distance[i];
                min_index = i;
            }

        }
        return min_index;
    }

    private int[][] convertGraphToUnweighted(int[][] graphForConverting) {
        for (int i = 0; i < graph.getAdjList().length; i++) {
            for (int j = 0; j < graph.getAdjList()[i].length; j++) {

                graphForConverting[i][j] = 1;


            }
        }
        return graphForConverting;
    }

    private void printResults() {
        int weight = 0;
        int steps = 0;
        System.out.println("Pfad: ");
        int node = endNode;
        System.out.print("Pfad: " + node);

        while(node != startNode)
        {
            if(parents[node] >= 0)
            {
                System.out.print(" -> " + parents[node]);
                weight += graph.getAdjList()[node][parents[node]];
                node = parents[node];

                ++steps;
            }
            else
            {
                // dient als Abbruchbedingung
                node = startNode;
            }
        }

        System.out.println();
        System.out.println("Anzahl Knoten durchlaufen: " + steps);
        System.out.println("Berechnete Kosten " + weight);
    }

    public LinkedList<String> getPath() {
        int node = endNode;
        LinkedList<String> output = new LinkedList<>();

        while(node != startNode)
        {
            if(!(parents[node] == -1))
            {
                output.add(graph.getLabel(parents[node]));
                node = parents[node];
            }
            else
            {
                // dient als Abbruchbedingung
                node = startNode;
            }
        }
        return output;
    }

}
