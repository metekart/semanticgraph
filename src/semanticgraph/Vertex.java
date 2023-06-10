package semanticgraph;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 
 */
public class Vertex implements Comparable<Vertex> {
    private String name;
    private List<Double> dimensions;
    private double magnitude;
    private boolean mustCalculateMagnitude;
    
    public Vertex(String name) {
        this.name = name.toLowerCase();
        this.dimensions = new ArrayList<>();
        this.magnitude = 0;
        this.mustCalculateMagnitude = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public List<Double> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<Double> dimensions) {
        this.dimensions = dimensions;
        mustCalculateMagnitude = true;
    }
    
    public void addDimension(double value) {
        dimensions.add(value);
        mustCalculateMagnitude = true;
    }
    
    public double getDimension(int index) {
        return dimensions.get(index);
    }
    
    public double getMagnitude() {
        if (mustCalculateMagnitude) {
            double total = 0;

            for (double value : dimensions) {
                total += Math.pow(value, 2);
            }

            magnitude = Math.sqrt(total);
            mustCalculateMagnitude = false;
        }
        
        return magnitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        
        if (!(obj instanceof Vertex))
            return false;
        
        return name.equalsIgnoreCase(((Vertex) obj).getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    @Override
    public int compareTo(Vertex vertex) {
        return name.compareTo(vertex.getName());
    }
}
