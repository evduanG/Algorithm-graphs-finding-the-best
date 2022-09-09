package findBast;
import java.util.Comparator;
import java.util.Queue;

import findBast.Vertex.Edge;
public class Data_Structure {

	
	public static final Comparator<? super Edge> Iterator_Comparator_Edge_val = null;
	public Queue<?> Q;
	public class Iterator_Comparator_Edge_val implements  Comparator {

		@Override
		public int compare(Object o1, Object o2) {
			return 	((Edge)o1).compareTo((Edge)o2);
		}
		
	}
	
	
}
