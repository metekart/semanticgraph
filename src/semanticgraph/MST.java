package semanticgraph;

import java.util.ArrayList;
import java.util.Collections;
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
public class MST {

    public static List<Edge> createMST(Graph graph) {
        List<Edge> mstEdges = new ArrayList<>();
        
        Union union = new Union(graph.getVertices().size());
        
        for (Edge edge: graph.getEdges()) {
            int id1 = graph.getVertexIndex(edge.getVertex1());
            int id2 = graph.getVertexIndex(edge.getVertex2());
            
            if (!union.isJoined(id1, id2)) {
                mstEdges.add(edge);
                union.join(id1, id2);
            }
        }
        
        return mstEdges;
    }
    
    public static SortedSet<List<Vertex>> createClusters(Graph graph, int numberOfClusters) {
        final List<Edge> mstEdges = createMST(graph);
        
        Map<Integer, List<Vertex>> clusterMap = new HashMap<>();
        
        if (numberOfClusters <= 0)
            return null;
        
        Union union = new Union(graph.getVertices().size());
        
        for (int i = 0; i <= mstEdges.size() - numberOfClusters; ++i) {
            int id1 = graph.getVertexIndex(mstEdges.get(i).getVertex1());
            int id2 = graph.getVertexIndex(mstEdges.get(i).getVertex2());
            
            if (!union.isJoined(id1, id2))
                union.join(id1, id2);
        }
        
        int[] rootIds = union.getRoots();
        
        for (int i = 0; i < rootIds.length; ++i) {
            if (clusterMap.containsKey(rootIds[i])) {
                List<Vertex> vList = clusterMap.get(rootIds[i]);
                vList.add(graph.getVertex(i));
            } else {
                List<Vertex> vList = new ArrayList<>();
                vList.add(graph.getVertex(i));
                
                clusterMap.put(rootIds[i], vList);
            }
        }
        
        SortedSet<List<Vertex>> clusterSet = new TreeSet<>(new Comparator<List<Vertex>>() {
            @Override
            public int compare(List<Vertex> l1, List<Vertex> l2) {
                if (l1.size() == l2.size())
                    return -1;
                
                return l1.size() - l2.size();
            }  
        });
        
        for (List<Vertex> vList : clusterMap.values()) {
            Collections.sort(vList);
            clusterSet.add(vList); 
        }
        
        return clusterSet;
    }
}
