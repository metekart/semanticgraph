package semanticgraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author 
 */
public class Graph {
    private final List<Vertex> vertices;
    private final SortedSet<Edge> edges;
    private final Map<Vertex, Integer> vertexIndexMap;
    private final Map<String, Vertex> nameVertexMap;
    private int currentIndex;

    public Graph() {
        vertices = new ArrayList<>();
        edges = new TreeSet<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                if (e1.getSimilarity() < e2.getSimilarity())
                    return 1;
                else if ((e1.getSimilarity() > e2.getSimilarity()))
                    return -1;
                else
                    return 0;
            }
        });
        vertexIndexMap = new HashMap<>();
        nameVertexMap = new HashMap<>();
        currentIndex = 0;
    }

    public boolean addVertex(Vertex vertex) {
        if (nameVertexMap.containsKey(vertex.getName()))
            return false;
        
        if (vertices.add(vertex)) {
            vertexIndexMap.put(vertex, currentIndex++);
            nameVertexMap.put(vertex.getName(), vertex);
            return true;
        }
        
        return false;
    }
    
    public boolean addVertex(String vertexName) {
        return addVertex(new Vertex(vertexName.toLowerCase()));
    }

    public boolean addVertices(Collection<Vertex> vertices) {
        boolean success = true;
        
        for (Vertex vertex : vertices)
            success &= addVertex(vertex);
        
        return success;
    }

    public boolean addEdge(Edge edge) {
        return edges.add(edge);
    }

    public boolean addEdge(String vertexName1, String vertexName2) {
        vertexName1 = vertexName1.toLowerCase();
        vertexName2 = vertexName2.toLowerCase();
        
        if (!nameVertexMap.containsKey(vertexName1) || !nameVertexMap.containsKey(vertexName2))
            return false;
        
        return edges.add(new Edge(nameVertexMap.get(vertexName1), nameVertexMap.get(vertexName2)));
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public SortedSet<Edge> getEdges() {
        return edges;
    }
    
    public Vertex getVertex(String vertexName) {
        return nameVertexMap.get(vertexName.toLowerCase());
    }
    
    public Vertex getVertex(int index) {
        return vertices.get(index);
    }
    
    public int getVertexIndex(Vertex vertex) {
        if (vertex == null)
            return -1;
        
        return vertexIndexMap.get(vertex);
    }
    
    public int getVertexIndex(String vertexName) {
        return getVertexIndex(nameVertexMap.get(vertexName.toLowerCase()));
    }
}
