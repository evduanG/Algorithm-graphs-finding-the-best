package findBast;

import findBast.Vertex.Edge;

public class main {
	public static void main(String[] args) {
		int size = 5 ; 
		Vertex [] V = new Vertex[size];
		for (int i = 0; i < V.length; i++) {
			V[i]= new Vertex (i);
		}
		V[0].addEdge(V[1], 3);
		V[0].addEdge(V[3], 3);
		
		Edge list = V[0].get_adjacency_list();
		System.out.print("0 ==>");
		while(list !=null) {
			System.out.print(list.getDst().getId()+"  ==>");
			list= list.get_nextEdge();
		}
		System.out.println(V[0].getColor());
		Graph g = new Graph(V);
		System.out.println("mack new greph ");
		g.get_Vertex_arr();
		System.out.println(g.getNumOfVertex());
		g.resetVel();
		g.reset_dist_time();
		g.setNumOfEgde(size);
	}
	
}
