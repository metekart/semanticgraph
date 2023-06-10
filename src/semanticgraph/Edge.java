package semanticgraph;

import java.util.List;

/**
 *
 * @author 
 */
public class Edge {
    private final Vertex vertex1, vertex2;
    private final double similarity;
    
    public Edge(Vertex v1, Vertex v2) {
        this.vertex1 = v1;
        this.vertex2 = v2;
        
        similarity = cosineSimilarity(v1, v2);
    }
    
    public static double cosineSimilarity(Vertex v1, Vertex v2) {
        List<Double> d1 = v1.getDimensions();
        List<Double> d2 = v2.getDimensions();
        
        int size = Math.min(d1.size(), d2.size());
        
        double dotProduct = 0;
        
        for (int i = 0; i < size; ++i) {
            dotProduct += d1.get(i) * d2.get(i);
        }
        
        return dotProduct / (v1.getMagnitude() * v2.getMagnitude());
    }
    
    public Vertex getVertex1() {
        return vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    public double getSimilarity() {
        return similarity;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        
        if (!(obj instanceof Edge))
            return false;

        return ((Edge) obj).vertex1.equals(vertex1) && ((Edge) obj).vertex2.equals(vertex2);
    }

    @Override
    public int hashCode() {
        int result = vertex1.hashCode();
        result = 31 * result + vertex2.hashCode();
        
        return result;
    }
}
