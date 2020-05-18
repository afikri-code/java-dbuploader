package de.ksirisoft.tools.db;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import de.ksirisoft.tools.db.util.Config;
import de.ksirisoft.tools.db.util.Utils;

/**
 * @author Ahmed Fikri
 *
 */
public class App 
{
	
	
	
	
    public static void main( String[] args ) 
    {
    	if(args.length < 2) {
    	  System.out.println("program args  filename database-dir");
    	  return;
    	}
    	
    	Path path = Paths.get(args[0]);
		if(!Files.exists(path)) {
			System.out.println("file doesn't exist");
			return;
		}
    	String filename = path.getFileName().toString() ;
    	String dir = args[1];
        try {
			Config.init();
		} catch (Exception e) {
		}
       
        Connection connection = Utils.createDbConnection();
        if(connection == null) {
        	System.out.println("connection can't be established");
        	return;
        }
        
        try {
			Utils.uploadFile(filename,Utils.convertFileContentToBlob(path.toString()),dir,connection);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch(SQLException ex) {}
			}
		}
        
    }
}
