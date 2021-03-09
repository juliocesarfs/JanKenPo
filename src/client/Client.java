package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import view.Screen;


public class Client extends JFrame {
	
	private Socket socket;
	private JTextField txtIP;
	private JTextField txtPorta;
	private JTextField txtNome;
	
	private JPanel mainPanel;
	
	private JLabel imagePlayer1;
	private JLabel namePlayer1;
	
	private JLabel imagePlayer2;
	private JLabel namePlayer2;
	
	private JLabel scoreBoard;
	
	private JRadioButton btnStone;
	private JRadioButton btnScissors;
	private JRadioButton btnPaper;
	private final ButtonGroup btnGroup = new ButtonGroup();
	
	private JButton confirmButton;
	
	private OutputStream output;
	private DataInputStream input;
	private OutputStreamWriter ouw;
	private BufferedWriter bfw;
	
	public Client() throws IOException {
		
		JLabel lblMessage = new JLabel("Verificar!");
		txtIP = new JTextField("127.0.0.1");
		txtPorta = new JTextField("12346");
		txtNome = new JTextField("Cliente");
		Object[] texts = { lblMessage, txtIP, txtPorta, txtNome };
		JOptionPane.showMessageDialog(null, texts);
		createScreen();
	}
	
	public void createScreen() {
		this.setTitle("JanKenPo");
        this.setDefaultCloseOperation(Screen.EXIT_ON_CLOSE);
        this.setSize(700, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setSize(700, 500);
        

        imagePlayer1 = new JLabel();

        imagePlayer1.setIcon(new ImageIcon(Screen.class.getResource("../images/Pedra.jpg")));
        imagePlayer1.setBounds(20, -10, 225, 193);
        
        imagePlayer2 = new JLabel();
        imagePlayer2.setForeground(Color.BLACK);        
        imagePlayer2.setIcon(new ImageIcon(Screen.class.getResource("../images/Pedra.jpg")));
        imagePlayer2.setBounds(510, -10, 225, 193);
        
        JLabel md3 = new JLabel("Melhor de Três!!");
        md3.setFont(md3.getFont().deriveFont(20f));
        md3.setBounds(260, 30, 190, 50);
        
        scoreBoard = new JLabel("0 x 0");
        scoreBoard.setFont(scoreBoard.getFont().deriveFont(20f));
        scoreBoard.setBounds(310, 100, 190, 50);
        
        namePlayer1 = new JLabel("Player 1");
        namePlayer1.setFont(scoreBoard.getFont().deriveFont(15f));
        namePlayer1.setBounds(70, 70, 225, 193);
        
        namePlayer2 = new JLabel("Player 1");
        namePlayer2.setFont(scoreBoard.getFont().deriveFont(15f));
        namePlayer2.setBounds(560, 70, 225, 193);
        
        btnStone = new JRadioButton("PEDRA");
        btnStone.setBounds(100, 240, 92, 23);
        btnGroup.add(btnStone);
        btnStone.setSelected(true);
        
        btnPaper = new JRadioButton("PAPEL");
        btnPaper.setBounds(300, 240, 92, 23);
        btnGroup.add(btnPaper);
        
        btnScissors = new JRadioButton("TESOURA");
        btnScissors.setBounds(500, 240, 92, 23);
        btnGroup.add(btnScissors);
        
        confirmButton = new JButton("Play");
        confirmButton.setBounds(245, 380, 92, 23);
        confirmButton.setSize(new Dimension(181, 44));
        
        confirmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					playAction();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
        
        mainPanel.add(imagePlayer1);
        mainPanel.add(imagePlayer2);
        mainPanel.add(md3);
        mainPanel.add(scoreBoard);
        
        mainPanel.add(namePlayer1);
        mainPanel.add(namePlayer2);
        
        mainPanel.add(btnStone);
        mainPanel.add(btnPaper);
        mainPanel.add(btnScissors);
        mainPanel.add(confirmButton);
        
        this.setContentPane(mainPanel);
        
    }
	
	public void setImagePlayer1(String imgName) {
		this.imagePlayer1.setIcon(new ImageIcon(Screen.class.getResource("../images/"+imgName+".jpg")));
	}
	
	public void setImagePlayer2(String imgName) {
		this.imagePlayer2.setIcon(new ImageIcon(Screen.class.getResource("../images/"+imgName+".jpg")));
	}
	
	public void setNamePlayer1(String name) {
		this.namePlayer1.setText(name);
	}
	
	public void setNamePlayer2(String name) {
		this.namePlayer2.setText(name);
	}
	
	public void connect() throws IOException {
		socket = new Socket(txtIP.getText(), Integer.parseInt(txtPorta.getText()));
		
		output = socket.getOutputStream();
		ouw = new OutputStreamWriter(output);
		bfw = new BufferedWriter(ouw);
		bfw.write(txtNome.getText() + "\r\n");
		bfw.flush();
	}
	
	private void playAction() throws IOException {
		for (Enumeration<AbstractButton> buttons = btnGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
            	bfw.write(button.getText() + "\r\n");
            	bfw.flush();
            }
          
        }
		
		this.confirmButton.setEnabled(false);
	}
	
	public void listen() throws IOException {
		
		InputStream in = socket.getInputStream();
		InputStreamReader inr = new InputStreamReader(in);
		BufferedReader bfr = new BufferedReader(inr);
		String msg = "";
		
		while (!"Sair".equalsIgnoreCase(msg)) {
			
			if (bfr.ready()) {
				msg = bfr.readLine();
				System.out.println(msg);
				String[] processMsg = msg.split(":");
				
				if (processMsg[1].equals("0")) {
					this.setImagePlayer1(processMsg[2]);
				} else {
					this.setImagePlayer2(processMsg[2]);
				}
				
				this.confirmButton.setEnabled(true);
			}
		
		}
	}
	
	public static void main(String[] args) throws IOException {
		Client app = new Client();
		app.connect();
		app.listen();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
