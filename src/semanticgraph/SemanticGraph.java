package semanticgraph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

/**
 *
 * @author 
 */
public class SemanticGraph {
    private static void usage()
    {
        System.out.println("java -jar SemanticGraph.jar <dictionary file> <word pairs file> <output clusters file> <number of clusters>");
    }
    
    public static void main(String[] args) {
        
        try {
            if (args.length < 4) {
                usage();
                System.exit(-1);
            }
            
            String dictionaryFile = args[0];
            String wordPairFile = args[1];
            String clustersFile = args[2];
            int numberOfClusters = Integer.parseInt(args[3]);
            
            Map<String, Vertex> vertexMap = new HashMap<>();
            
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(dictionaryFile)))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split("\\s+");
                    
                    Vertex vertex = new Vertex(data[0].replace("\"", ""));
                    
                    for (int i = 1; i < data.length; ++i)
                        vertex.addDimension(Double.parseDouble(data[i]));
                    
                    vertexMap.put(vertex.getName(), vertex);
                }
            }
            
            Graph graph = new Graph();
            
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(wordPairFile)))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.trim().split("-");
                    
                    if (data[0].isEmpty())
                        continue;
                    
                    graph.addVertex(vertexMap.get(data[0]));
                    graph.addVertex(vertexMap.get(data[1]));
                    
                    graph.addEdge(data[0], data[1]);
                }
            }
            
            SortedSet<List<Vertex>> clusters = MST.createClusters(graph, numberOfClusters);
            
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(clustersFile))) {

                for (List<Vertex> vList : clusters) {
                    StringBuilder sb = new StringBuilder();
                    
                    for (Vertex v : vList) {
                        sb.append(v.getName());
                        sb.append(",");
                    }
                    
                    sb.deleteCharAt(sb.length()-1);
                    
                    bufferedWriter.write(sb.toString());
                    bufferedWriter.write("\r\n");
                    
                    System.out.println(sb.toString());
                }
            }
            
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
