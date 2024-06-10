package modelo;

public class Main {
	public static void main(String[] args) {
		Ping ping=new Ping("localhost",5555);
		ping.start();
	}

}
