package model;

public class RunServer {
	public static void main(String[] args) {
		ClientHandler solverHandler = new SolverClientHandler();
		int M = 4;
		MyServer server = new MyServer(6400, M);
		server.run(solverHandler);
		System.out.println("Im here");
	}

}
