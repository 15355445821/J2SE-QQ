import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


import com.mysql.jdbc.Connection;

class ClientThread extends Thread {

	private Socket client = null;
	String id="Ⱥ ��";
	DataInputStream dis = null;
	static boolean flag=false;

	public ClientThread(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		
		try {
			dis = new DataInputStream(client.getInputStream());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 3 ��ÿͻ���������
		System.out.println("�ͻ���:" + client.getInetAddress() +" �˿�:"+ client.getPort()
				+ "����");
		
		while (true) {
			try {
		        
				String str = dis.readUTF();// 4 ��ȡ�ͻ�������
				System.out.println("���Կͻ���:" + client.getInetAddress() + " �˿�:"
						+ client.getPort() + ":\n" + str);
                StringTokenizer tokenizer=new StringTokenizer(str,"^");
				
                if(tokenizer.hasMoreTokens()&&tokenizer.nextToken().equals("@#$%"))  //����ָ��
				{
					
					id=tokenizer.nextToken();
					
				}
                else if(id.equals("Ⱥ ��"))
                {
                	// ����Ϣ���͸����и��������������ӵĿͻ���
                	for (Socket s : Tcp������_���̷߳���.clientList) {
    					DataOutputStream dos= new DataOutputStream(s
    							.getOutputStream());
    					dos.writeUTF(str);
    				}
                }
                else  
                {
                	
					try {
						Connection connection=(Connection) DriverManager.getConnection
						("jdbc:mysql://127.0.0.1:3306/qq","root","root");
						Statement statement=connection.createStatement();
						String command="select ip from table2 where id="
							+id;
					    ResultSet resultSet=statement.executeQuery(command);
					    resultSet.next();
					   
					    for(Socket s:Tcp������_���̷߳���.clientList)
					    {
					    	if(s.getInetAddress().toString().equals(resultSet.getString(1)))
					    	{
					    DataOutputStream dos=new DataOutputStream(s.getOutputStream());
					    dos.writeUTF(str);
					    break;
					          }
					    }
					    resultSet.close();
					    statement.close();
					    connection.close();
					   
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
			    
			            
                
			    }              	               	               	               	          	
                
    	} catch (Exception e) {
    				synchronized (Tcp������_���̷߳���.clientList) {
    					//��client�˹ر�����ʱ�������������쳣����catch���Ƴ�client
    					Tcp������_���̷߳���.clientList.remove(client);
    					System.out.println("��������������:"
    							+ Tcp������_���̷߳���.clientList.size());
    				}
    				try {
    					if (dis != null) {
    						dis.close();// 5 �ر�������
    					}
    				} catch (Exception ex) {
    					break;
    				}

    				break;// �ͻ��˹ر�����ѭ��
    			}
			                   	
			}
                	               	               	            				    															             
		}
	}

public class Tcp������_���̷߳��� {

	// ������и��������������ӵĿͻ���
	static List<Socket> clientList = new ArrayList<Socket>();
    //static Socket[] clients=new Socket[2];
	/**
	 * @param args
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
		Socket client = null;// �ͻ�������
		try {
			
			ServerSocket server = new ServerSocket(666);
			// 1 ����������׽��֣������׽��ֵĶ˿ں�
            
			System.out.println("�������ѿ�����");

			while (true) {
				client = server.accept();// 2 ��ÿͻ�������

				synchronized (clientList) {
					clientList.add(client);
					System.out.println("��������������:" + clientList.size());
				}
				ClientThread ct = new ClientThread(client);
				ct.start();

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	   
	}

}