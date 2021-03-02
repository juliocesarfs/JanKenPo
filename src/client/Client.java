package client;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.main;

public class Client {
	
	private Socket socket;
	private JTextField txtIP;
	private JTextField txtPorta;
	private JTextField txtNome;
	
	public Client() throws IOException {
		
		JLabel lblMessage = new JLabel("Verificar!");
		txtIP = new JTextField("127.0.0.1");
		txtPorta = new JTextField("12346");
		txtNome = new JTextField("Cliente");
		Object[] texts = { lblMessage, txtIP, txtPorta, txtNome };
		JOptionPane.showMessageDialog(null, texts);
	}
	
	public void connect() throws IOException {
		
		socket = new Socket(txtIP.getText(), Integer.parseInt(txtPorta.getText()));
		
	}
	
	public static void main(String[] args) throws IOException {
		Client app = new Client();
		app.connect();
	}
}
