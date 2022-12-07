
import java.sql.*;


public class App {
    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String username = "postgres";
    private String password = "1989830";
    private Connection conn;

    public App(String url,String username,String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    //execute one line query
    public ResultSet executeSQL(String sql){
        Statement statement;
        ResultSet resultSet = null;
        try{
            conn = DriverManager.getConnection(url,username,password);
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
        }catch (SQLException s){
            System.out.println(s.getMessage());
        }
        return resultSet;
    }
    
}
