import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class util_functions {

    private String username;
    private String type;

    public boolean isEmployeePermValid (String type){ //verify that an employee permission is valid to access

        //need to connect to SQL server or it will always return False.

        List<String> result = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","1989830");
            String selectPerms = "SELECT DISTINCT employee_type FROM employee";
            result = statement.executeQuery(selectPerms);
            //go ckeck the sql by username and type to return the permission
        }
        //if doesn't exist return false
        //else return true
        return result.contains(type);
    }

}