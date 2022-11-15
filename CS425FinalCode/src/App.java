
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class App {
    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String username = "postgres";
    private String password = "1989830";

    public Connection connect(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url,username,password);
            System.out.println("Connection established!");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return conn;
    }


}
