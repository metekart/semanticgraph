package semanticgraph;

/**
 *
 * @author
 */
public class Union {
    private final int[] ids;
    
    public Union(int size) {
        ids = new int[size];
        
        for (int i = 0; i < size; ++i)
            ids[i] = i;
    }
    
    public int getRoot(int id) {
        while (id != ids[id]) {
            ids[id] = ids[ids[id]];
            id = ids[id];
        }
        
        return id;
    }
    
    public void join(int id1, int id2) {
        ids[getRoot(id1)] = ids[getRoot(id2)];
    }
    
    public boolean isJoined(int id1, int id2) {
        return getRoot(id1) == getRoot(id2);
    }
    
    public int[] getRoots()
    {
        int[] idRoots = new int[ids.length];
        
        for (int i = 0; i < ids.length; ++i)
            idRoots[i] = getRoot(i);
        
        return idRoots;
    }
}
