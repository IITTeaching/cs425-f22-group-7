
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    private String url;
    private String username;
    private String password;
    private Connection conn;

    public App(){
        this.url = "jdbc:postgresql://localhost:5432/postgres";
        this.username = "postgres";
        this.password = "1989830";
        // change url and password to connect local sql server

    }
    public App(String url, String username,String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connect(){
        try{
            if (url == null && username == null && password == null){
                throw new Exception("Missing important information");
            }
            try{
                conn = DriverManager.getConnection(url,username,password);
            }catch (SQLException s){
                System.out.println(s.getMessage());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void closeConn() throws SQLException {
        conn.close();
    }
}
