package in.lms.common;

public class HackError extends Exception {

	/**
	 * 
	 */
	
	private String message ;
	private static final long serialVersionUID = 1L;
	
	public HackError(String msg){
		super(msg);
		this.message = msg;
	}
	

}
