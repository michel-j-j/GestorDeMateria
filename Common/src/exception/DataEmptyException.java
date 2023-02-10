package exception;

import javax.swing.JOptionPane;

public class DataEmptyException extends Exception{
	
	public DataEmptyException(String msj)
	{
		super();
		JOptionPane.showMessageDialog(null, msj);
	}
}
