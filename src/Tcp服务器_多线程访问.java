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
	String id="群 聊";
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
		// 3 获得客户端输入流
		System.out.println("客户端:" + client.getInetAddress() +" 端口:"+ client.getPort()
				+ "连接");
		
		while (true) {
			try {
		        
				String str = dis.readUTF();// 4 读取客户端数据
				System.out.println("来自客户端:" + client.getInetAddress() + " 端口:"
						+ client.getPort() + ":\n" + str);
                StringTokenizer tokenizer=new StringTokenizer(str,"^");
				
                if(tokenizer.hasMoreTokens()&&tokenizer.nextToken().equals("@#$%"))  //密文指令
				{
					
					id=tokenizer.nextToken();
					
				}
                else if(id.equals("群 聊"))
                {
                	// 把消息推送给所有跟服务器建立连接的客户端
                	for (Socket s : Tcp服务器_多线程访问.clientList) {
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
					   
					    for(Socket s:Tcp服务器_多线程访问.clientList)
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
    				synchronized (Tcp服务器_多线程访问.clientList) {
    					//当client端关闭连接时，服务器捕获到异常，在catch块移除client
    					Tcp服务器_多线程访问.clientList.remove(client);
    					System.out.println("服务器在线人数:"
    							+ Tcp服务器_多线程访问.clientList.size());
    				}
    				try {
    					if (dis != null) {
    						dis.close();// 5 关闭输入流
    					}
    				} catch (Exception ex) {
    					break;
    				}

    				break;// 客户端关闭跳出循环
    			}
			                   	
			}
                	               	               	            				    															             
		}
	}

public class Tcp服务器_多线程访问 {

	// 存放所有跟服务器建立连接的客户端
	static List<Socket> clientList = new ArrayList<Socket>();
    //static Socket[] clients=new Socket[2];
	/**
	 * @param args
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
		Socket client = null;// 客户端连接
		try {
			
			ServerSocket server = new ServerSocket(666);
			// 1 建立服务端套接字，定义套接字的端口号
            
			System.out.println("服务器已开启！");

			while (true) {
				client = server.accept();// 2 获得客户端连接

				synchronized (clientList) {
					clientList.add(client);
					System.out.println("服务器在线人数:" + clientList.size());
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