package utilitary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CSV {
	
	public static String CrearArchivo(ArrayList<String []> datos,Integer cantCampos, String nombreRegistro) {
	    try {
	        String currentPath = Paths.get("").toAbsolutePath().normalize().toString();
	        String downloadFolder = "/ArchivosCSV";
	        String downloadPath = currentPath + downloadFolder;
	        File newFolder = new File(downloadPath);
	        boolean dirCreated = newFolder.mkdir();

	        // get current time
	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-M-dd_HH-mm-ss");
	        LocalDateTime now = LocalDateTime.now();
	        System.out.println(dtf.format(now));
	        String fileName = nombreRegistro + dtf.format(now)+ ".csv";

	        // Whatever the file path is.
	        File statText = new File(downloadPath + "/" + fileName);
	        FileOutputStream is = new FileOutputStream(statText);
	        OutputStreamWriter osw = new OutputStreamWriter(is);
	        Writer w = new BufferedWriter(osw);
	        
	        for(String [] cadena: datos)
	        {    String reg = "";
	            for(int i = 0; i < cantCampos; i++)
	            {
	              if(reg.equals(""))
	              {
	            	  reg = cadena[i];
	              }
	              else
	              {
	            	  reg = reg +", "+ cadena[i];
	              }
	            }

	            System.out.println(reg + "\n");
	            w.write(reg + "\n");
	        }
	        w.close();
	        return downloadPath + "/" + fileName;
	    } catch (IOException e) {
	        System.err.println("Problem writing to the file " + e);
	    }

	    return "error";
	}
}


