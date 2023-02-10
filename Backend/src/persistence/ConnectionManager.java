package persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConnectionManager {
   
	//Obtiene los parametros de conn.properties.  
	public static Properties getProperties() throws RuntimeException { 
		Properties prop = new Properties();
		try {
			ResourceBundle infoDataBase = ResourceBundle.getBundle("database");
			
			prop.setProperty("connection", infoDataBase.getString("db.url"));
			prop.setProperty("username", infoDataBase.getString("db.user"));
			prop.setProperty("password", infoDataBase.getString("db.password"));

		} catch (Exception e1) {
			throw new RuntimeException("Ocurrio un error al leer la configuraci�n desde el archivo" + e1);
			// TODO: disparar Exception propia
		}
		return prop;

	}


}