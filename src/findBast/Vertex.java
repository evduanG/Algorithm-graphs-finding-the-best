package findBast;

import java.awt.Color;
import java.util.LinkedList;

public class Vertex {
	/* ===================================================================================================
	 * ===================================================================================================
	 * ===================================================================================================
	 */
	/**
	 * <code>public class Edge </code>  
	 * a class in {@link Vertex} use as a {@link LinkedList} 
	 * <h1>  <code>implements </code>  Comparable 
	 * @see compareTo(Vertex o) 
	 * @see compareTo(Edge o)
	 * @author eviatar
	 *
	 */
	public class Edge implements Comparable<Edge>{
		private Vertex src , dst;
		private int vale ;
		private Edge nextEdge ;
		
		public Edge( Vertex src ,Vertex  dst , int vale ) {
			this.setSrc(src);
			this.setDst(dst);
			this.setVale(vale);
			this.nextEdge=null ;
		}

		public Edge ( Vertex src ,Vertex  dst , int vale , Edge next  ) {
			this.setSrc(src);
			this.setDst(dst);
			this.setVale(vale);
			this.nextEdge=next ;
		}
		
		public Edge get_nextEdge () {		return nextEdge;	}
		
		public Vertex getDst() {			return dst;			}

		public void setDst(Vertex dst) {	this.dst = dst;		}

		public Vertex getSrc() {			return src;			}	

		public void setSrc(Vertex src) {	this.src = src;		}

		public int getVale() {				return vale;		}

		public void setVale(int vale) {		this.vale = vale;	}
		/**
		 * @param o
		 * @return <code>true</code> this {@link Edge} dst is equals to {@link Vertex}
		 * @return <code>false</code> this {@link Edge}  dst is not equals to {@link Vertex}
		 */
		public boolean compareTo(Vertex o) {	return (this.dst.equals(o));	}

		@Override

		public int compareTo(Edge o) {		return this.vale-o.vale;	}

		public boolean hasNext() {
			if (this.nextEdge==null) {			return false; 	}
			return true; 
		}
	}
	/* ===================================================================================================
	 * ===================================================================================================
	 * ===================================================================================================
	 */
	/**
	 * * <code>public class Value </code>  
	 * a class in {@link Vertex} use to get the value 
	 * have value and infi  ==> if this val != 0 ==> this hava a inifinty val 
	 * ==> if inif == 1 or -1  after algo ==> the algo dont rech this {@link Vertex}
	 * OR @see algo deff 
	 * @author eviatar
	 *
	 */
	public class Value implements Comparable<Vertex> {
		private int value ;
		private int infi ;
		
		/**
		 * @param flag <code>false</code>  => + infinity
		 * @param flag <code>true</code>  => - infinity
		 */
		public Value (boolean flag ) {
			if (flag ) {
				this.infi=1;
			}else {
				this.infi=-1;
			}
			this.value=0;
		}
		

		/**
		 * @throws infinityException 
		 * @return val 
		 */
		public int getValue() throws infinityException {
			if (infi==0) {
				return value;
			}else {
				throw new infinityException (this.infi);
			}		
		}

		public void setValue(int value) {
			this.value = value;
			this.infi=0;
		}

		@Override
		public int compareTo(Vertex o) {
			int x=0 ; 
			try {
				x = o.getVale().getValue();
			} catch (infinityException e) {
				return (this.infi==0)?0:1;
			}
			return this.value-x;
		}
	}
	/* ===================================================================================================
	 * ===================================================================================================
	 * ===================================================================================================
	 */
	private Edge adj_list;
	private int id  ;
	private Value vale ;
	private Color color ;
	private Vertex parent;
	private int dist_time_start ; 
	private int dist_time_end ; 

	
	public Vertex(int id  ) {
		this.id=id ;	
		this.vale =null ; 
		this.color=Color.white; 
		this.parent= null; 
	}
	
	public void addEdge ( Vertex dst ,int  vale ) {
		if (this.adj_list==null) {
			this.adj_list= new Edge(this, dst, vale);
		}
		else {
			this.adj_list=new Edge (this , dst , vale ,this.adj_list);
		}
	}

	public void removEdge( Vertex dst) {
		Edge e = this.adj_list;
		if (e.compareTo(dst)) {
			this.adj_list=e.nextEdge;
		}
		else {
			while (e.nextEdge!= null ) {
				if(e.nextEdge.compareTo(dst)) {
					e.nextEdge=e.nextEdge.nextEdge;
					break;
				}
				e=e.nextEdge;
			}
		}
	}
	
	public Value getVale() {
		return vale;
	}

	public void setVale(int i) {
		this.vale.setValue(i);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Edge get_adjacency_list() {
		return this.adj_list;
	}

	/*
	 * ========================================================================
	 * reset meted for new algo 
	 * ========================================================================
	 */

	public void setColor(Color color) {
		this.color= color;
	}
	
	public Color getColor () {
		return this.color ;
	}
	/** 
	 * @param bool : <code>true</code> this {@link Value} will start with + infinity 
	 * @param bool : <code>false</code> => this {@link Value} will start with - infinity 
	 */
	public void resetValue (boolean bool ) {
		this.vale= new Value(bool);
	}

	public void setParent(Vertex parent) {
		this.parent = parent;
		
	}
	public Vertex getParnVertex () {
		return this.parent;
	}

	public int getDist_time_start() {
		return dist_time_start;
	}

	public void setDist_time_start(int dist_time_start) {
		this.dist_time_start = dist_time_start;
	}

	public int getDist_time_end() {
		return dist_time_end;
	}

	public void setDist_time_end(int dist_time_end) {
		this.dist_time_end = dist_time_end;
	}


}
