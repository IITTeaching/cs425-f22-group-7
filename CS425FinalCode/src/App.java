
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


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


    //this is an example that pass sql statement to sql server
    public void connect(){
        try{
            if (url == null && username == null && password == null){
                throw new Exception("Missing important information");
            }
            try{
                conn = DriverManager.getConnection(url,username,password);
                Statement statement = conn.createStatement();
                String sql = "";//sql language
                //<type> <var> = statement.execute(sql);
                //result as a set can use ResultSet to store

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
