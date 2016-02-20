import java.util.Random;

public class Dijkstra {

    private Graph graph;
    private int[] distance;
    private boolean[] visited;
    private int[] parents;
    private int startNode;
    private int endNode;

    public Dijkstra(Graph graph, int startNode, int endNode)
    {
        this.graph = graph;
        distance = new int[graph.getAdjList().length];
        visited = new boolean[graph.getAdjList().length];
        parents = new int[graph.getAdjList().length];
        this.startNode = startNode;
        this.endNode = endNode;

    }

    public void findShortestPath(boolean isUnweighted)
    {

        System.out.println("Startknoten : " + startNode);
        System.out.println("Endknoten :  " + endNode);
        int[][] adjList = graph.getAdjList();
        int[][] graphForPathFinding = new int[adjList.length][adjList.length];

        if (isUnweighted)
        {
            graphForPathFinding = convertGraphToUnweighted(graphForPathFinding); // Gewichte loswerden, alle Gewichte
            // auf 1
        } else graphForPathFinding = adjList;


        // Initializierung
        for (int i = 0; i < adjList.length; i++)
        {
            parents[i] = Integer.MAX_VALUE;
            visited[i] = false;
            distance[i] = Integer.MAX_VALUE;
        }
        distance[startNode] = 0;
        for (int i = 0; i < graphForPathFinding.length; i++)
        {


            int nextNode = getMinDistance();
            if (nextNode == -1)
            { // Kein Weg gefunden
                System.out.println("Es konnte kein Pfad zwischen " + startNode + " und " + endNode + " ermittelt " +
                        "werden.");
                return;
            }
            visited[nextNode] = true;
            parents[i] = nextNode;


            if (nextNode == endNode)
            {
                printResults();
                return; // Wenn Ziel erreicht, abbrechen
            }
            for (int j = 0; j < graphForPathFinding.length; j++)
            {

                if (!visited[j] && graphForPathFinding[nextNode][j] > 0 &&
                        distance[nextNode] != Integer.MAX_VALUE &&
                        distance[nextNode] + graphForPathFinding[nextNode][j] < distance[j])
                    distance[j] = distance[nextNode] + graphForPathFinding[nextNode][j];
            }


        }


    }

    public int[] getPath()
    {
        return parents;
    }


    private int getMinDistance()
    {
        int min = Integer.MAX_VALUE;
        int min_index = -1;

        for (int i = 0; i < graph.getAdjList().length; i++)
        {
            if (visited[i] == false && distance[i] <= min)
            {
                min = distance[i];
                min_index = i;
            }

        }
        return min_index;
    }

    private int[][] convertGraphToUnweighted(int[][] graphForConverting)
    {
        for (int i = 0; i < graph.getAdjList().length; i++)
        {
            for (int j = 0; j < graph.getAdjList()[i].length; j++)
            {
                if (graph.getAdjList()[i][j] > 0)
                {
                    graphForConverting[i][j] = 1;
                }
            }
        }
        return graphForConverting;
    }

    private void printResults()
    {
        int weight = 0;
        int steps = 0;
        System.out.println("Pfad: ");
        for (int i = parents.length - 1; i >= 0; i--)
        {
            if (parents[i] < Integer.MAX_VALUE)
            {

                System.out.print(parents[i] + "      ");
                steps++;
                weight += graph.getAdjList()[parents[i]][parents[i - 1]];
                if ((i - 1) == 0)
                {
                    System.out.print(parents[i - 1]);
                    i = -1;
                }
            }
        }


        System.out.println();
        System.out.println("Anzahl Knoten durchlaufen: " + steps);
        System.out.println("Berechnete Kosten " + weight);
    }
}
