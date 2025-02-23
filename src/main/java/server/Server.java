package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		Spark.port(getHerokuAssignedPort());
		DebugScreen.enableDebugScreen();
		Bootstrap.init();
		Router.configure();
	}

	static int getHerokuAssignedPort() {
	    ProcessBuilder processBuilder = new ProcessBuilder();
	    if (processBuilder.environment().get("PORT") != null) {
	        return Integer.parseInt(processBuilder.environment().get("PORT"));
	    }
	    
	    return 9000; //return default port if heroku-port isn't set (i.e. on localhost)
	}
}