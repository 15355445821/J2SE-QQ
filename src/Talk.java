
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import com.mysql.jdbc.Connection;

class Talk{
	static JFrame jFrame=new JFrame("QQ");
	static JComboBox jComboBox=new JComboBox();
	static JButton jButton=new JButton("聊 天");
	static JLabel jLabel=new JLabel("群 聊");
	static JPanel jpanel = new JPanel();
    static JTextArea msgArea = new JTextArea();
	static JTextField sendField = new JTextField();
	static JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
	static Container layered=jFrame.getLayeredPane();
	static ImageIcon imageIcon=new ImageIcon("images\\10.jpg");
	static JLabel iconJLabel=new JLabel();
	static void f(){
		jFrame.setBounds(500,100,500,400);
		jFrame.setResizable(false);
		jFrame.add(jpanel);
		
		layered.setLayout(null);
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(500, 400, Image.SCALE_DEFAULT));
		layered.add(iconJLabel,new Integer(Integer.MIN_VALUE));
		iconJLabel.setIcon(imageIcon);
		iconJLabel.setBounds(0,0,500,400);
		((JPanel)jFrame.getContentPane()).setOpaque(false);
		jpanel.setOpaque(false);
		jpanel.add(jComboBox);
		jpanel.add(jButton);
		jpanel.setLayout(null);
		jpanel.add(jLabel,new Integer(Integer.MIN_VALUE));
		jLabel.setBounds(200,25,100,50);
		jLabel.setForeground(Color.RED);
		msgArea.setEditable(false);
		jpanel.add(sendField);
		sendField.setBounds(10,330,450,21);
		msgArea.setColumns(20);
		msgArea.setRows(5);
		msgArea.setBackground(Color.pink);
		jScrollPane1.setViewportView(msgArea);
		jpanel.add(jScrollPane1);
		jScrollPane1.setBounds(10,60,450,250);
		jComboBox.removeAllItems();
		jComboBox.addItem("群 聊");
		jComboBox.setBounds(0,0,300,20);
		jButton.setBounds(350,0,100,20);
		
		jButton.addMouseListener(new Listen5());
		try {
			Connection connection=(Connection) DriverManager.getConnection
			("jdbc:mysql://127.0.0.1:3306/qq","root","root");
			Statement statement=connection.createStatement();
			String command="select id from table2";
		    ResultSet resultSet=statement.executeQuery(command);
		    while(resultSet.next())
		    	jComboBox.addItem(resultSet.getString(1));
		        jComboBox.removeItem(Main.jTextField.getText());
		    resultSet.close();
		    statement.close();
		    connection.close();
		
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		jFrame.setVisible(true);
		
	}
}
class Listen5 implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JButton jButton=(JButton)e.getSource();
		if(jButton.equals(Talk.jButton))
		{
			Talk.jLabel.setText(Talk.jComboBox.getSelectedItem().toString());
			
				Talk.msgArea.append("已连接服务器!\n");
				try {
					Client.dos.writeUTF("@#$%^"+Talk.jLabel.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}																	
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}