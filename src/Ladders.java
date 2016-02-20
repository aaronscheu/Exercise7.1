import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by amaridev on 19/02/16.
 * Package: PACKAGE_NAME for Exercise7.1.
 */
public class Ladders {

    private ArrayList<String> wordlist;
    private Graph graph;

    public Ladders(String filename)
    {
        Path path = Paths.get(filename);

        try (Stream<String> lines = Files.lines(path))
        {
            wordlist = lines.collect(Collectors.toCollection(ArrayList<String>::new));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        graph = word2graph();
    }

    private Graph word2graph()
    {
        Graph graph = new Graph(wordlist.size());
        wordlist.stream().forEach(word ->
        {
            graph.addLabel(wordlist.indexOf(word), word);
            wordlist.stream().forEach(line ->
            {
                if (differBy1(word, line))
                    graph.addEdge(wordlist.indexOf(word), wordlist.indexOf(line), 1);
            });
        });

        return graph;
    }

    public int getRandomVertex()
    {
        return new Random().nextInt(wordlist.size());
    }

    public void getLadder(String start, String end)
    {
        int iStart = graph.label2Index(start);
        int iEnd = graph.label2Index(end);

        if (iStart == -1 || iEnd == 1)
        {
            System.out.println("[Method: getLadder][Error: parameter not found in graph]");
            return;
        }

        getLadder(iStart, iEnd);
    }

    public void getLadder(int start, int end)
    {
        Dijkstra pathfinder = new Dijkstra(graph, start, end);
        pathfinder.findShortestPath(true);

        int[] path = pathfinder.getPath();

        System.out.println();
        System.out.printf("Path from \"%s\" to \"%s\":\n", graph.getLabel(start), graph.getLabel(end));
        System.out.printf("%s ", graph.getLabel(start));

        for (int i = 1; i < path.length; i++)
        {
            if (path[i] == Integer.MAX_VALUE) return;
            System.out.printf("--> %s ", graph.getLabel(path[i]));
        }
    }

    private boolean differBy1(String a, String b)
    {
        int counter = 0;

        for (int i = 0; i < a.length(); i++)
            if (!(a.charAt(i) == b.charAt(i))) counter++;

        return counter == 1;
    }
}
