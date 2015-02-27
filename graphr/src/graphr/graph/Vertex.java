package graphr.graph;

//import graphr.data.JsonArrayState;
//import graphr.data.JsonKeyValueState;
//import graphr.data.JsonReadableWritable;

import java.util.Collection;
import java.util.Hashtable;


public class Vertex<DV extends GraphData,DE extends GraphData> extends GraphElement<DV> {
	
	Hashtable<Long, Edge<DV,DE>> edges;
	
	public Vertex(DV data) {
		super(data);
		edges = new Hashtable<Long, Edge<DV,DE>>();
	}
 
	public void addEdge(Edge<DV,DE> e) {
		edges.put(new Long(e.id), e);
	}
	
	public Collection<Edge<DV, DE>> getEdges() {
		return edges.values();
	}

	public Edge<DV, DE> getEdge(long id) {
		return edges.get(id);
	}
	
	public String toString() {
		return "Vertex"; // to do ;)
	}
	
	/**
	 * Part of the visitor design pattern -accept method.
	 */
	@Override
	public void accept(GraphElementVisitor visitor) {
		visitor.visit(this);
	}

	

//	public String getAsJson() {
//		
//		JsonKeyValueState j = new JsonKeyValueState();
//		
//		j.add("type", "Vertex");
//		j.add("id", new Integer(id).toString());
//		j.add("data", data != null ? data.getAsJson() : "null");
//	
//		JsonArrayState edgesForJson = new JsonArrayState();
//		
//		for (Edge<DV,DE> e : edges.values()) {
//			edgesForJson.add(e.getAsJson());
//		}
//		
//		j.add("edges", edgesForJson.getAsJson());
//
//		return j.getAsJson();
//	}	
	
}
