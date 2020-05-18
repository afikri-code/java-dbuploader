package de.ksirisoft.tools.db.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Utils {
	/**
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static String readFileToString(String filename) throws IOException {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder ret = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            ret.append(line+"\n");
        }
        reader.close();
        return ret.toString();
	}
	
	/**
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] convertFileContentToBlob(String filePath) throws IOException {
		   File file = new File(filePath);
		   byte[] fileContent = new byte[(int) file.length()];
		   FileInputStream inputStream = null;
		   try {
			inputStream = new FileInputStream(file);
			inputStream.read(fileContent);
		   } catch (IOException e) {
			throw new IOException("Unable to convert file to byte array. " + 
		              e.getMessage());
		   } finally {
			if (inputStream != null) {
		           inputStream.close();
			}
		   }
		   return fileContent;
		}

	/**
	 * @param x
	 * @param con
	 * @throws Exception 
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void uploadFile(String filename,byte[] x,String uploadDir, Connection con) throws Exception {
		String plsql = Utils.readFileToString("plsql.sql");
		
		CallableStatement cs = null;
		try {
			cs = con.prepareCall(plsql);
			cs.setBytes(1, x);
			cs.setString(2, uploadDir);
			cs.setString(3, filename);

	        cs.execute();
	        cs.close();
		} catch (SQLException e) {
			throw new Exception(e);
		}finally {
			if(cs != null) {
				try {
					cs.close();
				}catch(SQLException e) {}
			}
		}
		
	}
	
	/**
	 * @return
	 */
	public static Connection  createDbConnection() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			if (con == null || con.isClosed()) {
				con = DriverManager.getConnection(Config.getProperty("db.url"),
						Config.getProperty("db.user"), Config.getProperty("db.password"));
				System.out.println("connected");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
