
import java.awt.Container;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.DriverManager;
import java.sql.SQLException;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.jdbc.Connection;
import java.sql.*;

class Form{
	
	static JFrame jFrame=new JFrame("QQ群");
	static JButton infoButton=new JButton("个人资料");
	static JButton selectButton=new JButton("查找群成员资料");
	static JButton talkButton=new JButton("聊天");
	
    static Container container=jFrame.getContentPane();
    static Container container2=jFrame.getLayeredPane();
	static JLabel jLabel=new JLabel();
	static ImageIcon icon=new ImageIcon("images\\8.jpg");
	static Image image2=(new ImageIcon("images\\1.jpg")).getImage();
	public static void function1(){
		(new Client("QQ")).getSocket();
		jFrame.setBounds(400,100,500,500);
		jFrame.setResizable(false);
		jFrame.setIconImage(image2);
		
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container.setLayout(null);
		container2.setLayout(null);
		((JPanel)container).setOpaque(false);
		container2.add(jLabel,new Integer(Integer.MIN_VALUE));
		jLabel.setBounds(0,0,500,500);
		icon.setImage(icon.getImage().getScaledInstance(500,500,Image.SCALE_DEFAULT));
	    jLabel.setIcon(icon);
	    
	    infoButton.setBounds(150,100,200,50);
	    container.add(infoButton);
	    
	    infoButton.addMouseListener(new Listen2());
	    selectButton.setBounds(150,200,200,50);
	    container.add(selectButton);
	   
	    selectButton.addMouseListener(new Listen2());
	    talkButton.setBounds(150,300,200,50);
	    container.add(talkButton);
	   
	    talkButton.addMouseListener(new Listen2());
	    jFrame.setVisible(true);
	    
	    
	}
}
//首界面
class Listen2 implements MouseListener{

	static JFrame jFrame=new JFrame();
	static JLabel jLabel=new JLabel();
	static JLabel jLabel1=new JLabel("个人资料");
	static JLabel jLabel2=new JLabel("账号：");
	static JLabel jLabel3=new JLabel("性别：");
	static JLabel jLabel4=new JLabel("年龄：");
	static JLabel jLabel5=new JLabel("个人爱好:");
	static JButton jButton2=new JButton("编辑资料");
	static JTextField textField2=new JTextField();
	static JTextField textField3=new JTextField();
	static JTextField textField4=new JTextField();
	static JTextField textField5=new JTextField();
	static Container container=jFrame.getContentPane();
	static Container layContainer=jFrame.getLayeredPane();
	static ImageIcon icon=new ImageIcon("images\\6.jpg");
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JButton jButton=(JButton)e.getSource();
		if(jButton.equals(Form.infoButton)){
			Form.icon.setImage(Form.icon.getImage().getScaledInstance(200, 500,Image.SCALE_DEFAULT));
			jFrame.setBounds(500,100,200,500);
			jFrame.setResizable(false);
			container.setLayout(null);
			((JPanel)container).setOpaque(false);
			layContainer.setLayout(null);
			layContainer.add(jLabel,new Integer(Integer.MIN_VALUE));
			jLabel.setBounds(0,0,200,500);
			jLabel.setIcon(icon);
                         //连接数据库，添加地址ip，和id；

			try {
				Connection connection=(Connection) DriverManager.getConnection
				("jdbc:mysql://127.0.0.1:3306/qq","root","root");
				Statement statement=connection.createStatement();
				String command="select* from table2 where id="+Main.jTextField.getText();
			    ResultSet resultSet=statement.executeQuery(command);
			    resultSet.next();
			    textField2.setText(resultSet.getString(1));
			    textField3.setText(resultSet.getString(3));
			    textField4.setText(resultSet.getString(4));
			    textField5.setText(resultSet.getString(5));
			    
			    resultSet.close();
			    statement.close();
			    connection.close();
			
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			container.add(jLabel1);
			jLabel1.setBounds(70,20,100,30);
			container.add(jLabel2);
			jLabel2.setBounds(0,100,100,30);
			container.add(textField2);
			textField2.setBounds(60,100,100,30);
			textField2.setEditable(false);
			container.add(jLabel3);
			jLabel3.setBounds(0,150,100,30);
			container.add(textField3);
			textField3.setBounds(60,150,100,30);
			textField3.setEditable(false);
			container.add(jLabel4);
			jLabel4.setBounds(0,200,100,30);
			container.add(textField4);
			textField4.setBounds(60,200,100,30);
			textField4.setEditable(false);
			container.add(jLabel5);
			jLabel5.setBounds(0,250,100,30);
			container.add(textField5);
			textField5.setBounds(60,250,100,30);
			textField5.setEditable(false);
			jButton2.setBounds(50,350,100,30);
			container.add(jButton2);
			jButton2.addMouseListener(new Listen3());
			jFrame.setVisible(true);
		}
		else if(jButton.equals(Form.selectButton))
			Select.f();
		else if(jButton.equals(Form.talkButton))
			Talk.f();
		
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

//个人资料
class Listen3 implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e){
		// TODO Auto-generated method stub
		if(Listen2.jButton2.getText().equals("编辑资料")){
		Listen2.jButton2.setText("保存");
		Listen2.textField3.setEditable(true);
		Listen2.textField4.setEditable(true);
		Listen2.textField5.setEditable(true);
		}
		else {
			Listen2.jButton2.setText("编辑资料");
			Listen2.textField3.setEditable(false);
			Listen2.textField4.setEditable(false);
			Listen2.textField5.setEditable(false);
			
				try {
					Connection connection = (Connection) DriverManager.getConnection
					("jdbc:mysql://localhost:3306/qq","root","root");
				
					Statement statement = connection.createStatement();
					
					String command="update table2 set sex='"
						+Listen2.textField3.getText()+"',age='"
						+Listen2.textField4.getText()+"',hobby='"
						+Listen2.textField5.getText()+"' where id="
						+Main.jTextField.getText();
					statement.execute(command);
					statement.close();
					connection.close();
				} catch (SQLException e1) {
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
class Select{
	static JFrame jFrame=new JFrame("查找");
	static JComboBox jComboBox=new JComboBox();
	static JButton jButton=new JButton("查找");
	static Container content=jFrame.getContentPane();
	static Container layered=jFrame.getLayeredPane();
	static JLabel jLabel3=new JLabel("性别：");
	static JLabel jLabel4=new JLabel("年龄：");
	static JLabel jLabel5=new JLabel("个人爱好:");
	static JTextField textField3=new JTextField();
	static JTextField textField4=new JTextField();
	static JTextField textField5=new JTextField();
	
	static void f(){
		Select.jComboBox.removeAllItems();
		layered.add(Form.jLabel);
		Listen2.icon.setImage(Listen2.icon.getImage().getScaledInstance(200, 500, Image.SCALE_DEFAULT));
	    Form.jLabel.setIcon(Listen2.icon);
	    Form.jLabel.setBounds(0,0,200,500);
	    layered.setLayout(null);
		jFrame.setBounds(550,100,200,500);
		jFrame.setResizable(false);
		content.setLayout(null);
		content.add(jComboBox);
		((JPanel)content).setOpaque(false);
		jComboBox.setBounds(0,0,200,20);
		content.add(jButton);
		jButton.setBounds(60,30,100,30);
		content.add(jLabel3);
		jLabel3.setBounds(0,100,100,30);
		content.add(textField3);
		textField3.setBounds(60,100,100,30);
		textField3.setEditable(false);
		content.add(jLabel4);
		jLabel4.setBounds(0,150,100,30);
		content.add(textField4);
		textField4.setBounds(60,150,100,30);
		textField4.setEditable(false);
		content.add(jLabel5);
		jLabel5.setBounds(0,200,100,30);
		content.add(textField5);
		textField5.setBounds(60,200,100,30);
		textField5.setEditable(false);
		jButton.setBounds(50,350,100,30);
		content.add(jButton);
		jButton.addMouseListener(new Listen4());
		jFrame.setVisible(true);
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
		
	}
	
}
//查找
class Listen4 implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		try {
			Connection connection=(Connection) DriverManager.getConnection
			("jdbc:mysql://127.0.0.1:3306/qq","root","root");
			Statement statement=connection.createStatement();
			String command="select* from table2 where id="+Select.jComboBox.getSelectedItem();
		    ResultSet resultSet=statement.executeQuery(command);
		    resultSet.next();
		    Select.textField3.setText(resultSet.getString(3));
		    Select.textField4.setText(resultSet.getString(4));
		    Select.textField5.setText(resultSet.getString(5));
		    
		    resultSet.close();
		    statement.close();
		    connection.close();
		
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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