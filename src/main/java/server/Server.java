package server;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
	public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		//Spark.port(9000);
		DebugScreen.enableDebugScreen();
		Bootstrap.init();
		Router.configure();
	}

}