package exception;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class DataErrorException extends RuntimeException{
	
	public DataErrorException(String msj)
	{   super();
     	JLabel label = new JLabel("<html>"+msj);
	    label.setHorizontalAlignment(SwingConstants.CENTER);
		JOptionPane.showMessageDialog(null, label,null,JOptionPane.ERROR_MESSAGE);
	}
}