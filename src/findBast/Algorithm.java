package findBast;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Queue;


import java.util.PriorityQueue;
import findBast.Vertex.Edge;
public class Algorithm {
	/**
	 * This class contains the algorithmS,
	 *  each of the algorithm is a static method,
	 *  and is required to send to it the graph that wants to execute your algorithm,
	 *   and the rest of the data will be need for the algorithm
	 */

	

	private static Graph reset_G(Graph G ) {
		G.resetColor();
		G.resetVel();
		G.resetParent();
		return G ; 
	}
	
	/*==============================================================================================
	 * BFS 
	 * =============================================================================================
	 */
	/**
	 * 
	 * @param G
	 * @param v
	 * @param isReset <code><code>false</code></code> only if the is a need for the Greaph to no be reset 
	 * @SuppressWarnings("Does not guarantee a correct answer")
	 * @return 
	 * @throws Exception 
	 */
	public static Graph BFS (Graph G , Vertex s , boolean isReset ) throws Exception {
		if (isReset) {	reset_G(G);		}
		Queue<Vertex> Q = null ;
		// set the vauleS  for the Vertex s to the start init  
		s.setVale(0);	
		s.setColor(Color.gray);
		
		// add s to the Q 
		if (!Q.add(s)) 		// if the it return false the func 
		{
			throw new Exception("no add V ");
		}
		
		while (!Q.isEmpty()) {
			Vertex v = Q.remove();
			Edge e =v.get_adjacency_list();
			v.setColor(Color.gray);
			while (e != null) {
				Vertex u = e.getDst();
				if(u.getColor()==Color.white) {
					// New vertex
					u.setColor(Color.gray);
					u.setParent(v);
					
					if (!Q.add(u)) 		// if the it return false the func 
					{
						throw new Exception("no add V ");
					}
				}// end if 
				// go to the next adj in the list 
				e=e.get_nextEdge();
			}// end while e != null    > all the adj of v 
		// Finished whit v 
			v.setColor(Color.black);
		}// end while Q not empty   > all Arrivable V from s 
		return G; 
	}

	public static Graph BFS (Graph G , Vertex v ) throws Exception {
		 return BFS(G,v,true);
	}
	public static void Print_Path_BFS (Graph G,Vertex s,Vertex v ){
		if ( v == s) {
			System.out.println(s.getId());
		}else {
			if (v.getParnVertex() == null) {
				System.out.println("no path from s to v exists");
			}else {
				Print_Path_BFS ( G, s, v.getParnVertex());
				System.out.println(v);
			}			
		}
	}
	
	
	/*==============================================================================================
	 * DFS
	 * =============================================================================================
	 */
	public static Graph DFS_Wraps (Graph G , boolean isReset) {
		if (isReset) {
			reset_G(G);
		}
		G.reset_dist_time();
		int time = 0 ; 
		for (Vertex vertex : G.get_Vertex_arr()) {
			if (vertex.getColor()==Color.white) {
				// new Vertex thet the DFS nor arivd 
				time = DFS(vertex, time );
			}
		}
		return G;
		
	}
	
	
	private static int  DFS(Vertex u,int  time ) {
		u.setColor(Color.gray);
		u.setDist_time_start(++time);
		
		for (Edge e = u.get_adjacency_list(); e.hasNext();) {
			Vertex v = e.getDst();
			if (v.getColor()==Color.white) {
				v.setParent(u);
				time= DFS(v,time);
			}
		}
		u.setColor(Color.black);
		u.setDist_time_end(++time);
		return time;
	}

	public static Graph DFS_Wraps (Graph G ) {
		return DFS_Wraps(G, true);
	}
	
	/*==============================================================================================
	 * Kruskals mst 
	 * =============================================================================================
	 */
	
	public static Graph Kruskals(Graph G) {
		ArrayList<Edge> listOfEdge = new ArrayList<>();
		// add all the edge to a arry 
		int vertices=0;
		int numOfE=0;
		for (Vertex v : G.get_Vertex_arr()) {
			Edge e = v.get_adjacency_list();
			while(e!=null ) {
				listOfEdge.add(e);
				e=e.get_nextEdge();
				++numOfE;
			}
			++vertices;
		}
		listOfEdge.sort(new Comparator<Edge>() {
			@Override
			public int compare(Edge e1, Edge e2) {
				return e1.compareTo(e2);
			}
		});
		Subset subsets[] = new Subset[vertices];
		for (int v = 0; v < vertices; ++v) {
		      subsets[v].parent = v;
		      subsets[v].rank = 0;
		 }
		int i = 0;
		int e =0;
		Graph mst = new Graph(vertices);
	    while (e < vertices - 1) {
	      Edge next_edge =listOfEdge.get(0);
	      int x = find(subsets , next_edge.getSrc().getId());
	      int y = find(subsets , next_edge.getDst().getId());
	      if (x != y) {
	        mst.add_Edge_From_originG(next_edge);
	        Union(subsets, x, y);
	      }
	    }	
	    return mst;
	}
	 // Union
	 public class Subset {
	    int parent, rank;
	  };

	  public static int find(Subset subsets[], int i) {
	    if (subsets[i].parent != i)
	      subsets[i].parent = find(subsets, subsets[i].parent);
	    return subsets[i].parent;
	  }

	 public static void Union(Subset subsets[], int x, int y) {
	    int xroot = find(subsets, x);
	    int yroot = find(subsets, y);

	    if (subsets[xroot].rank < subsets[yroot].rank)
	      subsets[xroot].parent = yroot;
	    else if (subsets[xroot].rank > subsets[yroot].rank)
	      subsets[yroot].parent = xroot;
	    else {
	      subsets[yroot].parent = xroot;
	      subsets[xroot].rank++;
	    }
	  }

	/*==============================================================================================
	* PRIM 
	* =============================================================================================
	*/
	 
	 public static Graph MST_PRIM(Graph G, Vertex s) {
		 G.resetVel();
		 G.resetColor();
		 PriorityQueue<Edge>  minHeap =new PriorityQueue<Edge>(G.getNumOfEgde(), new Comparator<Edge>() {
				@Override
				public int compare(Edge e1, Edge e2) {
					return e1.compareTo(e2);
				}
			});
		Graph mst = new Graph(G.getNumOfVertex());
		Edge e = s.get_adjacency_list();
		// add all the adjacenc to the PQ
		 while (e !=null ) {
			 minHeap.add(e);
			 e=e.get_nextEdge();
		 }
		 //set s to visit 
		 s.setColor(Color.black);
		 while ((!minHeap.isEmpty())||(mst.getNumOfEgde()<G.getNumOfVertex()-1)) {
			Edge edg =minHeap.poll();
			if(edg.getDst().getColor()==Color.white) {
				// this is a new vertex in mst
				Vertex u = edg.getDst();
				mst.add_Edge_From_originG(edg);
				// set the vertex to visit 
				u.setColor(Color.black);
				
				Edge list = u.get_adjacency_list();
				while (list!=null) {
					// add all the adjacenc to the PQ
					minHeap.add(list);
					list=list.get_nextEdge();
				}
			}
		}
		 return mst ; 
	 }
	 
	/*==============================================================================================
	* PRIM 
	* =============================================================================================
	*/
	 public static void Dijkstra (Graph G , Vertex s ) {
		 G.resetVel();
		 G.resetColor();
		 G.resetParent();
		 PriorityQueue<Vertex>  minHeap =new PriorityQueue<Vertex>(G.getNumOfEgde(), new Comparator<Vertex>() {
				@Override
				public int compare(Vertex v, Vertex u) {
					return v.getVale().compareTo(u);
				}
			});
		Edge e = s.get_adjacency_list();
		// add all the adjacenc to the PQ
		 while (e !=null ) {
			 minHeap.add(e.getDst());
			 e=e.get_nextEdge();
		 }
		 //set s to visit 
		 s.setColor(Color.black);
		 while (!minHeap.isEmpty()) {
			Vertex v =minHeap.poll();
//			if(edg.getDst().getColor()==Color.white) {
//				// this is a new vertex in mst
//				Vertex u = edg.getDst();
//				mst.add_Edge_From_originG(edg);
//				// set the vertex to visit 
//				u.setColor(Color.black);
//				
//				Edge list = u.get_adjacency_list();
//				while (list!=null) {
//					// add all the adjacenc to the PQ
//					minHeap.add(list);
//					list=list.get_nextEdge();
//				}
			}
		}
		 
	// }

	public static boolean Relax(Edge e) {
		int newVel =0; 
		int currentVel=0 ;
		try {
			newVel =e.getSrc().getVale().getValue()+e.getVale();
		} catch (infinityException e1) {
			// if the src vel is infinty 
			return false;
		}
		try {
			currentVel = e.getDst().getVale().getValue();
		} catch (infinityException e1) {
			// the ddt vel is infinty 
			e.getDst().getVale().setValue(newVel);
			e.getDst().setParent(e.getSrc());
			return true; 
		}
		// the Compara 
		if (newVel<currentVel) {
			e.getDst().getVale().setValue(newVel);
			e.getDst().setParent(e.getSrc());
			return true; 
		}
		return false;
	}
}

















































//
//
////Kruskal's algorithm in Java
//
//import java.util.*;
//
//class Graph {
//class Edge implements Comparable<Edge> {
// int src, dest, weight;
//
// public int compareTo(Edge compareEdge) {
//   return this.weight - compareEdge.weight;
// }
//};
//
//// Union
//class subset {
// int parent, rank;
//};
//
//int vertices, edges;
//Edge edge[];
//
//// Graph creation
//Graph(int v, int e) {
// vertices = v;
// edges = e;
// edge = new Edge[edges];
// for (int i = 0; i < e; ++i)
//   edge[i] = new Edge();
//}
//
//int find(subset subsets[], int i) {
// if (subsets[i].parent != i)
//   subsets[i].parent = find(subsets, subsets[i].parent);
// return subsets[i].parent;
//}
//
//void Union(subset subsets[], int x, int y) {
// int xroot = find(subsets, x);
// int yroot = find(subsets, y);
//
// if (subsets[xroot].rank < subsets[yroot].rank)
//   subsets[xroot].parent = yroot;
// else if (subsets[xroot].rank > subsets[yroot].rank)
//   subsets[yroot].parent = xroot;
// else {
//   subsets[yroot].parent = xroot;
//   subsets[xroot].rank++;
// }
//}
//
//// Applying Krushkal Algorithm
//void KruskalAlgo() {
// Edge result[] = new Edge[vertices];
// int e = 0;
// int i = 0;
// for (i = 0; i < vertices; ++i)
//   result[i] = new Edge();
//
// // Sorting the edges
// Arrays.sort(edge);
// subset subsets[] = new subset[vertices];
// for (i = 0; i < vertices; ++i)
//   subsets[i] = new subset();
//
// for (int v = 0; v < vertices; ++v) {
//   subsets[v].parent = v;
//   subsets[v].rank = 0;
// }
// i = 0;
// while (e < vertices - 1) {
//   Edge next_edge = new Edge();
//   next_edge = edge[i++];
//   int x = find(subsets, next_edge.src);
//   int y = find(subsets, next_edge.dest);
//   if (x != y) {
//     result[e++] = next_edge;
//     Union(subsets, x, y);
//   }
// }
// for (i = 0; i < e; ++i)
//   System.out.println(result[i].src + " - " + result[i].dest + ": " + result[i].weight);
//}
//








//public static void main(String[] args) {
// int vertices = 6; // Number of vertices
// int edges = 8; // Number of edges
// Graph G = new Graph(vertices, edges);
//
// G.edge[0].src = 0;
// G.edge[0].dest = 1;
// G.edge[0].weight = 4;
//
// G.edge[1].src = 0;
// G.edge[1].dest = 2;
// G.edge[1].weight = 4;
//
// G.edge[2].src = 1;
// G.edge[2].dest = 2;
// G.edge[2].weight = 2;
//
// G.edge[3].src = 2;
// G.edge[3].dest = 3;
// G.edge[3].weight = 3;
//
// G.edge[4].src = 2;
// G.edge[4].dest = 5;
// G.edge[4].weight = 2;
//
// G.edge[5].src = 2;
// G.edge[5].dest = 4;
// G.edge[5].weight = 4;
//
// G.edge[6].src = 3;
// G.edge[6].dest = 4;
// G.edge[6].weight = 3;
//
// G.edge[7].src = 5;
// G.edge[7].dest = 4;
// G.edge[7].weight = 3;
// G.KruskalAlgo();
//}
//}