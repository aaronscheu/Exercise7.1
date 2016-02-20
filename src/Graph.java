import java.util.Random;

public class Graph {

    private int[][] adjList;
    private String[] labels;

    public Graph(int dimension) {
        adjList = new int[dimension][dimension]; // Felder die Verlinkung haben
        // KÖNNTEN = -1
        for (int i = 0; i < adjList.length; i++) { // Felder die keine haben
            // DÜRFEN = 0
            for (int j = 0; j < adjList[i].length; j++) {
                if (i != j) {
                    adjList[i][j] = -1;
                }
            }
        }

        labels = new String[dimension];
    }

    public int[][] getAdjList() {
        return this.adjList;
    }

    public void addEdge(int nodeStart, int nodeEnd, int weight) {
        adjList[nodeStart][nodeEnd] = weight;
        adjList[nodeEnd][nodeStart] = weight;
    }

    public void printGraph() {
        for (int i = 0; i < adjList.length; i++) {
            System.out.println("Knoten " + i + " ist mit folgenden Knoten verbunden:");
            for (int j = 0; j < adjList[i].length; j++) {
                if (adjList[i][j] > 0) {
                    System.out.print("Knoten " + j + " mit der Gewichtung " + adjList[i][j]);
                    System.out.println();
                }
            }
        }
    }

    public void printAsMatrix() {
        for (int i = 0; i < adjList.length; i++) {
            for (int j = 0; j < adjList[i].length; j++) {
                System.out.print(adjList[i][j]);
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    private void createGraph(int edgeCount) {
        Random rnd = new Random();
        for (int i = 0; i < edgeCount; i++) {
            int nodeStart = rnd.nextInt(adjList.length);
            int nodeEnd = rnd.nextInt(adjList.length);
            int weight = rnd.nextInt(20) + 1;
            while (adjList[nodeStart][nodeEnd] >= 0) {
                nodeEnd = rnd.nextInt(adjList.length);
                nodeStart = rnd.nextInt(adjList.length);
            }
            addEdge(nodeStart, nodeEnd, weight);
        }
    }

    public int[] getNeighbors(int node){
        int neighborCount = 0;
        for(int i = 0; i < adjList[node].length; ++i)
            if(adjList[node][i] > 0)
                ++neighborCount;
        int[] neighbours = new int[neighborCount];
        neighborCount = 0;
        for(int i = 0; i < adjList[node].length; ++i)
            if(adjList[node][i] > 0)
                neighbours[neighborCount++] = i;

        return neighbours;
    }

    public void addLabel(int vertex, String name)
    {
        labels[vertex] = name;
    }

    public String getLabel(int vertex)
    {
        return labels[vertex];
    }

    public int label2Index(String label)
    {
        for (int i = 0; i < labels.length; i++)
        {
            if (labels[i].equals(label)) return i;
        }
        return -1;
    }
}
