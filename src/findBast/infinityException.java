package findBast;

public class infinityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6509670557468963730L;

	public boolean flag ;
	public infinityException(int val ) {
		super();
		
		if (val == 1 ) {
			// + infi
			this.flag=true ;
		}
		else 
		{
			//-inif
			this.flag=false ;
		}
	}
	
	
}
