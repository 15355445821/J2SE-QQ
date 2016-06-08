
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class Main{
	static JFrame jFrame=new JFrame();
	static ImageIcon icon=new ImageIcon("images\\8.jpg");
	static Image image=icon.getImage();
	static Image image2=(new ImageIcon("images\\1.jpg")).getImage();
	static JTextField jTextField=new JTextField();
	static JPasswordField jPasswordField=new JPasswordField();
	static JButton jButton=new JButton("��¼");
	static JButton jButton2=new JButton("ע��");
	static JLabel jLabel=new JLabel("�˺�:");
	static JLabel jLabel2=new JLabel("����:");
	@SuppressWarnings("serial")
	static JLabel jLabel3=new JLabel(icon){
		public void paint(Graphics g){
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	};
	//static JLabel jLabel3=new JLabel(icon);
	public static void main(String args[]){
		jLabel.setBounds(50,100,100,50);
		jLabel.setFont(new Font("����", 1, 20));
		jLabel2.setBounds(50,200,100,50);
		jLabel2.setFont(new Font("����", 1, 20));
		jTextField.setBounds(100,100,300,50);
	    jTextField.setFont(new Font("����", 1, 40));
		jPasswordField.setBounds(100,200,300,50);
		jPasswordField.setFont(new Font("����", 1, 40));
		jPasswordField.setEchoChar('*');
		jButton.setBounds(100,350,100,50);
		jButton.setFont(new Font("����",1,30));
		jButton2.setBounds(300,350,100,50);
		jButton2.setFont(new Font("����",1,30));
		
		jFrame.setIconImage(image2);
		jFrame.setBounds(400,100,500,500);
		jFrame.setResizable(false);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container=jFrame.getContentPane();
		jLabel3.setBounds(0,0,jFrame.getWidth(),jFrame.getHeight());
		container.setLayout(null);
		container.add(jButton);
		container.add(jButton2);
		container.add(jTextField);
		container.add(jPasswordField);
		container.add(jLabel);
		container.add(jLabel2);
		container.add(jLabel3,new Integer(Integer.MIN_VALUE));
		Listen listen=new Listen();
		jButton.addMouseListener(listen);
		jButton2.addMouseListener(listen);
	}
}
class Listen implements MouseListener{
	@Override
	public void mouseClicked(MouseEvent e) {
		JButton jButton=(JButton)e.getSource();
		Sql sql=new Sql();
		if(jButton.equals(Main.jButton))   //login
		{
			try {
				sql.login();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(jButton.equals(Main.jButton2)) //register
		{
			try {
				sql.register();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
//�������ݿ⣺
class Sql{
	@SuppressWarnings("deprecation")
	
	void login() throws SQLException, InterruptedException{
		Connection connection=DriverManager.getConnection
				("jdbc:mysql://127.0.0.1:3306/qq","root","root");
		Statement statement=connection.createStatement();
		String command="select *from table2 where id="
				+Main.jTextField.getText();
		ResultSet resultSet=statement.executeQuery(command);
		if(!resultSet.next())
			{
			JOptionPane.showMessageDialog(null,"�˺Ż��������");
			}
		if(resultSet.getString(2).equals(Main.jPasswordField.getText()))
		{
			JOptionPane.showMessageDialog(null,"��¼�ɹ�!");
			Main.jFrame.hide();
			Form.function1();
		}
		else {
			JOptionPane.showMessageDialog(null,"�˺Ż��������");
		}
		resultSet.close();
	    statement.close();
	    connection.close();
	}
	@SuppressWarnings("deprecation")
	void register() throws SQLException{
		Connection connection=DriverManager.getConnection
				("jdbc:mysql://127.0.0.1:3306/qq","root","root");
		Statement statement=connection.createStatement();
		String command="select id from table2 where id="
				+Main.jTextField.getText();
		ResultSet resultSet=statement.executeQuery(command);
		if(resultSet.next())
			JOptionPane.showMessageDialog(null,"���������˺�!",
					"����",JOptionPane.WARNING_MESSAGE);
		else if(Main.jPasswordField.getText().equals(""))
				JOptionPane.showMessageDialog(null,"����������!",
						"��ʾ",JOptionPane.INFORMATION_MESSAGE);
		else
		{
		String command2="insert into table2 values("+Main.jTextField.getText()
				+","+Main.jPasswordField.getText()+","+"NULL"+","+"NULL"+","+"NULL"+","+"NULL"+","+"NULL"+")";
		int option=JOptionPane.showConfirmDialog(null,"ȷ��ע�᣿",
				"ע���",JOptionPane.YES_NO_OPTION);
		if(option==JOptionPane.YES_OPTION)
		statement.execute(command2);
		}
		resultSet.close();
	    statement.close();
	    connection.close();
	}
}
