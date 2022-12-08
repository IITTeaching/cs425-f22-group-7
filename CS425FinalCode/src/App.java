
import java.util.*;
import java.sql.*;
class App{
    String url = "jdbc:postgresql://localhost:5432/final?currentSchema=final";
    String username = "postgres";
    String password = "1989830";
    String sql = "";
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public Boolean login(String userName, String passCode,String userRole){
        if (userName == null||passCode==null||userRole==null){
            return false;
        }

        if (userRole == "teller" || userRole == "manager"){
            sql = "Select employee_name, passcode FROM employee where employee_name ='" + userName + "' and passcode='" + passCode + "';";
            //Select employee_name, passcode FROM employee where employee_name ='userName' and passcode='passCode';
        } else if(userRole == "customer"){
            sql = "Select customer_name, passcode FROM customer where customer_name='" + userName + "' and passcode='" + passCode + "';";
        }else{
            return false;
        }
        try {
            connection = DriverManager.getConnection(url,username,password);
            //need to point to the specific database and schema
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Denied!!!");
        } finally {
            try{
                if (resultSet!=null){
                    resultSet.close();
                }
                if (statement!=null){
                    statement.close();
                }
                if (connection!=null){
                    connection.close();
                }
            }catch (SQLException e1){
                e1.printStackTrace();
                System.out.println("Occur error while releasing connection/statement/resultSet!!!");
            }

        }
        return false;
    }
    //I already test the login, it's work, don't touch inside anything  -- JC

    public Boolean checkCustomer(String userName) {
        if (userName == null){
            return false;
        }
        sql = "Select customer_name FROM customer where customer_name ='" + userName + "';";
        try{
            connection = DriverManager.getConnection(url,username,password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            return resultSet.next();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Member not in list!!!");
        }
        return false;
    }

    public ArrayList<Integer> checkAccount(String userName){
        // assume userName exist
        sql = "Select account_id From account where customer_name ='"+userName+"';"; //sql return all the account number into one String[]
        try{
            connection = DriverManager.getConnection(url,username,password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData metadata = resultSet.getMetaData();
            int numberOfColumns = metadata.getColumnCount();
            ArrayList<Integer> accounts= new ArrayList<Integer>();
            while (resultSet.next()) {
                int i = 1;
                while (i <= numberOfColumns) {
                    accounts.add(resultSet.getInt(i++));
                }
            }
            return accounts;
        }
            catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Member not in list!!!");
            }
        return new ArrayList<>();
    }

    public Boolean withdrawal(int account_id,int amount){
        sql = "Insert into transaction (type, quantity, description, transaction_date, account_from, account_to, status) VALUES ('Withdrawal',"+amount+", 'withdrawal', now(),"+account_id+", 100, '0'); Update account set balance_curr = balance_curr - "+amount+" nwhere account_id ="+account_id+";";
        try{
            connection = DriverManager.getConnection(url,username,password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deposit(int account_id,int amount){
        return false;
    }

    public Boolean transfer(int account_id, int target_id, int values, boolean external){
        //if external = true, only create the transaction
        //else update the other account value as well


        return false;
    }
    public Integer checkBalance(int account_id){
        //assume account_id is not null
        sql = "";
        try {
            connection = DriverManager.getConnection(url,username,password);
            //need to point to the specific database and schema
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Denied!!!");
        } finally {
            try{
                if (resultSet!=null){
                    resultSet.close();
                }
                if (statement!=null){
                    statement.close();
                }
                if (connection!=null){
                    connection.close();
                }
            }catch (SQLException e1){
                e1.printStackTrace();
                System.out.println("Occur error while releasing connection/statement/resultSet!!!");
            }

        }
        return 0;
    }


    public ArrayList<String> checkTransaction(int current_account_id){
        //you need to put every retrieve information in one string to return it
        return new ArrayList<>();
    }

    public Boolean createAccount(String userName, String account_type){

        return false;
    }

    public Boolean deleteAccount(int account_id){

        return false;
    }

}