package de.ksirisoft.tools.db;

import java.io.IOException;

import de.ksirisoft.tools.db.util.Utils;

public class Dummy {

	public static void main(String[] args) throws IOException {
		System.out.println(System.getenv("DBUPLOADER_RUNTIME_DIR") );
		
		System.out.println(Utils.readFileToString("plsql.sql"));

	}

}
