package findBast;

import java.awt.Color;
import java.util.Iterator;

import findBast.Vertex.Edge;

public class Graph {
	
	private int[][] matrix ;
	private Vertex[] V ;
	private int numOfEgde;
	private int numOfVertex;
	
	/**
	 * empty Graph 
	 * @param numOfV
	 */
	public Graph (int numOfV) {
		this.V= new Vertex [numOfV];
		this.setNumOfVertex(numOfV) ;
		this.setNumOfEgde(0) ;
		for (int i = 0; i < V.length; i++) {
			 this.V[i]= new Vertex(i);
		}
	}
	
	public Graph (int [][] matrix ) {
		this.setNumOfEgde(0);
		this.numOfVertex=matrix.length;
		mtxrix_2_adjList(matrix );
		this.matrix= matrix;
	}

	public Graph (Vertex[] v) {
		this.V= v;
		this.numOfVertex=V.length; 
		this.setNumOfEgde(0);
		adjList_2_mtxrix(v);
	}
	
	/**
	 * func to Convert matrix to adjacency list
	 * @param matrix2
	 */
	private void mtxrix_2_adjList (int[][] matrix2) {
		this.V = new Vertex[matrix2.length-1];
		//s.1  Create an array of vertices 
		for (int v = 0 ; v<matrix2.length;v++){
			this.V[v]=new Vertex(v);
		}
		//s.2 Add to each vertex its edges
		for (int v = 0 ; v<matrix2.length;v++){
			for (int i = 0 ; i<matrix2.length;i++) {
				if (matrix2[v][i]!=0) {
					// have a edge 
					this.setNumOfEgde(this.getNumOfEgde() + 1);
					this.V[v].addEdge(this.V[i],matrix2[v][i]);		
				}
			}
		}
	}
	/**
	 * func to Convert matrix to adjacency list
	 * @param matrix2
	 */
	private void adjList_2_mtxrix (Vertex[] v) {
		this.matrix=new int [v.length-1][v.length-1];
		for (int i = 0; i < v.length; i++) {
			Edge e = this.V[i].get_adjacency_list();
			while (e != null) {
				int adj = e.getDst().getId();
				this.matrix[i][adj]=e.getVale();
				e=e.get_nextEdge();
				this.setNumOfEgde(this.getNumOfEgde() + 1);
			}
		}
	}

	/*
	 * =========================================================================
	 * 
	 * ========================================================================
	 */
	public void resetColor() {
		for (Vertex vertex : V) {
			vertex.setColor(Color.white);
		}
	}
	public void resetVel() {
		for (Vertex vertex : V) {
			vertex.resetValue(true);
		}
	}
	public void resetParent() {
		for (Vertex vertex : V) {
			vertex.setParent(null);
		}
	}

	public void reset_dist_time () {
		for (Vertex vertex : V) {
			vertex.setDist_time_end(0);
			vertex.setDist_time_start(0);
			}
	}
	/**
	 * givin an edge from G and add to G' this edge 
	 * @param e 
	 */
	public void add_Edge_From_originG (Edge e) {
		int id = e.getSrc().getId();
		this.numOfEgde++;
		this.V[id].addEdge(e.getDst(), e.getVale());
	}
	public Vertex getVertex			(int id )		 	{return  this.V[id];}
	
	public Vertex[] get_Vertex_arr 	() 					{return  this.V;}
	
	public int getNumOfEgde			() 					{return numOfEgde;}

	public void 	setNumOfEgde	(int numOfEgde) 	{this.numOfEgde = numOfEgde;}

	public int 		getNumOfVertex	() 					{return numOfVertex;}

	public void 	setNumOfVertex	(int numOfVertex) 	{this.numOfVertex = numOfVertex;}
}