import java.util.Random;

public class Main {

    public static void main(String[] args)
    {
        //        Random rnd = new Random();
        //        Graph graph = new Graph(5, 10);
        //        graph.printGraph();
        //        graph.printAsMatrix();
        //
        //        int startNode = rnd.nextInt(graph.getAdjList().length);
        //        int endNode = rnd.nextInt(graph.getAdjList().length);
        //        Dijkstra d = new Dijkstra(graph, startNode, endNode);
        //
        //        System.out.println("Kuerzester Pfad:");
        //        d.findShortestPath(true); // true = unweighted, false = weighted
        //        System.out.println();
        //        System.out.println("Guenstigster Pfad:");
        //        d.findShortestPath(false);


        // Ladders Game:
        Ladders game = new Ladders("/Users/amaridev/Documents/IdeaProjects/Info2/Exercise7.1/src/sgb-words-2.txt");

        game.getLadder("alone", "along");

    }

}
