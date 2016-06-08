
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import com.mysql.jdbc.Connection;
@SuppressWarnings("serial")
public class Client extends JFrame implements Runnable{
	
	//private BufferedReader reader;
	//private PrintWriter writer;
	 static DataOutputStream dos;
	 Socket socket;
	 static boolean flag=false;
	 
	 DataInputStream dis1;
		
	
	public Client(String title){
		Talk.sendField.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
				if(!Talk.jComboBox.getSelectedItem().equals("群 聊"))
					Talk.msgArea.append(Main.jTextField.getText()+":"+Talk.sendField.getText()+"\n");
				// TODO Auto-generated method stub
				try {
					dos.writeUTF(Main.jTextField.getText()+":"+Talk.sendField.getText()+"\n");
				   
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Talk.sendField.setText("");
				
			}
		});
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				Talk.msgArea.append(dis1.readUTF());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public void getSocket(){
		try{
			
			socket = new Socket("127.0.0.1",666);//建立一个socket用户连接
			try {
				Connection connection=(Connection) DriverManager.getConnection
				("jdbc:mysql://127.0.0.1:3306/qq","root","root");
				Statement statement=connection.createStatement();
				String command="update table2 set ip='"+socket.getInetAddress()+"',port="+socket.getPort()+" where id="+Main.jTextField.getText();
				statement.execute(command);
			    statement.close();
			    connection.close();
			   
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			Talk.msgArea.append("已连接服务器!\n");
			dis1=new DataInputStream(socket.getInputStream());
			
			dos=new DataOutputStream(socket.getOutputStream());
			
			new Thread(this).start();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}

