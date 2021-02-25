package main;

import java.awt.List;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import server.Server;

public class main {
	
	private static ServerSocket server;
	private static ArrayList<Server> clients;

	
	public static void main(String[] args) {
		
		try {
			// Cria os objetos necessário para instânciar o servidor
			JLabel lblMessage = new JLabel("Porta do Servidor:");
			JTextField txtDoor = new JTextField("12345");
			Object[] texts = { lblMessage, txtDoor };
			JOptionPane.showMessageDialog(null, texts);
			server = new ServerSocket(Integer.parseInt(txtDoor.getText()));
			
			clients = new ArrayList<Server>();
			
			JOptionPane.showMessageDialog(null, "Servidor ativo na porta: " + txtDoor.getText());

			while (true) {
				System.out.println("Aguardando conexão...");
				Socket con = server.accept();// bloquea o programa e aguarda conexão
				System.out.println("Cliente conectado...");
				Thread t = new Server(con);
				t.start();
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
