package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Server extends Thread {
	
	private String name;
	private static ServerSocket server;
	private Socket con;
	private InputStream in;
	private InputStreamReader inr;
	private BufferedReader bfr;
	private BufferedWriter bfw;
	private static ArrayList<Server> clients;
	
	
	public Server(Socket con, Integer door) {
		this.con = con;
		
		try {
			in = con.getInputStream();
			inr = new InputStreamReader(in);
			bfr = new BufferedReader(inr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void addClient(Server client) {
		clients.add(client);
	}
	
	private synchronized void removeClient(Server client) {
		clients.remove(client);
	}
	
	public void run() {
		this.addClient(this);
		System.out.println(clients);
	}
	
	public static void main(String[] args) {
		
		try {
			// Cria os objetos necessário para instânciar o servidor
			JLabel lblMessage = new JLabel("Porta do Servidor:");
			JTextField txtDoor = new JTextField("12346");
			Object[] texts = { lblMessage, txtDoor };
			JOptionPane.showMessageDialog(null, texts);
			server = new ServerSocket(Integer.parseInt(txtDoor.getText()));
			clients = new ArrayList<Server>();
			
			JOptionPane.showMessageDialog(null, "Servidor ativo na porta: " + txtDoor.getText());

			while (clients.size() < 1) {
				System.out.println("Aguardando conexão...");
				Socket con = server.accept();
				System.out.println("Cliente conectado...");
				Thread t = new Server(con, Integer.parseInt(txtDoor.getText()));
				t.start();
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
		
}
