import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Warehouse{
	
	private static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
	private final static String DB_UTL="jdbc:mysql://localhost/spider";
	
	private final static String USER="root";
	private final static String PASSWORD="sqlroot";
	
	private static final String sql="INSERT INTO spider6 VALUES(null,?,?,?)";
	private static Connection connection=null;
	private static PreparedStatement statement=null;
	
	static{
		try{
			Class.forName(JDBC_DRIVER);
			//System.out.println(Warehouse.sql);
			connection=DriverManager.getConnection(DB_UTL,USER,PASSWORD);
			statement=Warehouse.connection.prepareStatement(Warehouse.sql);

		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void insert(String fileName,String url,InputStream data){

		try {
			statement.setString(1, fileName);
			statement.setString(2, url);
			statement.setBinaryStream(3, data);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void finalize(){
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		try {
			String sql;
			sql="SELECT * FROM spider6 where ID=6";
			PreparedStatement statement=Warehouse.connection.prepareStatement(sql);
			ResultSet resultSet=statement.executeQuery();
		
		while(resultSet.next()){
			int id=resultSet.getInt(1);
			String name=resultSet.getString(2);
			String url=resultSet.getString(3);
			InputStream reader=resultSet.getBinaryStream(4);
			
			System.out.println(id +" "+ url +" " +name);
			try {
				FileOutputStream writer=new FileOutputStream(new File("D:\\workspace\\java\\spider6\\data\\file\\"+name));
				byte[] buf=new byte[1024];
				int len=0;
				while((len=reader.read(buf))!=-1)writer.write(buf,0,len);
				writer.close();
				reader.close();
			}catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}