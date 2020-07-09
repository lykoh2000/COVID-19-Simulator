import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


// this is the implementation of undirected graph

public class Graph<T, E> {
	// We use Hashmap to store the edges in the graph 
    private Map<T, List<Pair<T, E>>> map = new HashMap<>(); 
  
    // This function adds a new vertex to the graph 
    public void addVertice(T s) { 
        map.put(s, new LinkedList<Pair<T, E>>()); 
    } 
  
    // This function adds the edge 
    // between source to destination 
    public void addEdge(T source, T destination, boolean bidirectional, E weight) { 
    	if (source.equals(destination)) {
    		throw new IllegalArgumentException();
    	}
        if (!map.containsKey(source)) 
            addVertice(source); 
  
        if (!map.containsKey(destination)) 
            addVertice(destination); 
        
        Pair<T, E> temp = new Pair<>();
        temp.put(destination, weight);
        map.get(source).add(temp); 
        if (bidirectional == true) { 

        	Pair<T, E> pair = new Pair<>();
        	pair.put(source, weight);
            map.get(destination).add(pair); 
            

        } 
    } 
  
    // This function gives the count of vertices 
    public void getVertexCount() { 
        System.out.println("The graph has " + map.keySet().size() + " vertex"); 
    } 
  
    // This function gives the count of edges 
    public void getEdgesCount(boolean bidirection) { 
        int count = 0; 
        for (T v : map.keySet()) { 
            count += map.get(v).size(); 
        } 
        if (bidirection == true) { 
            count = count / 2; 
        } 
        System.out.println("The graph has " + count + " edges."); 
    } 
  
    // This function gives whether 
    // a vertex is present or not. 
    public boolean hasVertex(T s) { 
        if (map.containsKey(s)) { 
            return true;
        } 
        else { 
            return false;
        } 
    } 
  
    // This function gives whether an edge is present or not. 
    public boolean hasEdge(T s, T d) { 
        if (map.get(s).contains(d)) { 
            return true;
        } 
        else { 
            return false;
        } 
    } 
  
    // Prints the adjancency list of each vertex. 
    @Override
    public String toString() { 
        StringBuilder builder = new StringBuilder(); 
  
        for (T v : map.keySet()) { 
            builder.append(v.toString() + ": "); 
            for (Pair<T, E> pair : map.get(v)) { 
                builder.append(pair.toString() + " "); 
            } 
            builder.append("\n"); 
        } 
  
        return (builder.toString()); 
    } 
    
    public List<Pair<T, E>> getAdjacentList(T target) {
    	if (map.containsKey(target))
    		return map.get(target);
    	return null;
    }
    
    public void deleteEdge(T from, T to) {
    	
    }
    
    public void deleteVertice(T vertice) {
    	
    }
}
