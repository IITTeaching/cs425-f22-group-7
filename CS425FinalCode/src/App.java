
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

    public Boolean checkCustomer(String userName) { //checked
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

    public ArrayList<Integer> checkAccount(String userName){ //checked
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
        sql = "Insert into transaction (type, quantity, description, transaction_date, account_from, account_to, status) VALUES ('Withdrawal',"+amount+", 'withdrawal', current_timestamp(),"+account_id+", 100, '0'); Update account set balance_curr = balance_curr - "+amount+" where account_id ="+account_id+";";
        try{
            connection = DriverManager.getConnection(url,username,password);
            statement = connection.createStatement();
            connection.setAutoCommit(false);
//            resultSet = statement.executeQuery(sql);
            statement.addBatch("Insert into transaction (type, quantity, description, transaction_date, account_from, account_to, status) VALUES ('Withdrawal',"+amount+", 'withdrawal', clock_timestamp(),"+account_id+","+account_id+", '0');");
            statement.addBatch("Update account set balance_curr = balance_curr - "+amount+" where account_id ="+account_id+";");
            try{
                statement.executeBatch();
                connection.commit();
                return true;
            }catch (SQLException s){
                s.printStackTrace();
                connection.rollback();
            }
            return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deposit(int account_id,int amount){
        try{
            connection = DriverManager.getConnection(url,username,password);
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.addBatch("Update account set balance_curr = balance_curr +" + amount + "where account_id =" + account_id + ";");
            statement.addBatch("Insert into transaction (type, quantity, description, transaction_date, account_from, account_to, status) VALUES ('deposit'," + amount + ", 'deposit', clock_timestamp()," + account_id + "," +account_id+", '0');");
            try{
                statement.executeBatch();
                connection.commit();
                return true;
            }catch (SQLException s){
                s.printStackTrace();
                connection.rollback();
            }
            return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean transfer(int account_id, int target_id, int amount, boolean external){
        //if external = true, only create the transaction
        if(external == true) {

            try {
                connection = DriverManager.getConnection(url, username, password);
                statement = connection.createStatement();
                connection.setAutoCommit(false);
                statement.addBatch("Insert into transaction (type, quantity, description, transaction_date, account_from, account_to, status) VALUES ('transfer'," + amount + ", 'withdrawal', clock_timestamp()," + account_id + "," +target_id+", '0');");
                statement.addBatch("Update account set balance_curr = balance_curr -" + amount + "where account_id =" + account_id + ";");
                try{
                    statement.executeBatch();
                    connection.commit();
                    return true;
                }catch (SQLException s){
                    s.printStackTrace();
                    connection.rollback();
                }
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            //else update the other account value as well
        }else{
            try {
                connection = DriverManager.getConnection(url, username, password);
                statement = connection.createStatement();
                connection.setAutoCommit(false);
                statement.addBatch("Insert into transaction (type, quantity, description, transaction_date, account_from, account_to, status) VALUES ('transfer'," + amount + ", 'withdrawal', clock_timestamp()," + account_id + ","+target_id+ ", '0');");
                statement.addBatch("Update account set balance_curr = balance_curr -"+amount+"where account_id ="+account_id+";");
                statement.addBatch("Update account set balance_curr = balance_curr +"+amount+"where account_id ="+target_id+";");
                try{
                    statement.executeBatch();
                    connection.commit();
                    return true;
                }catch (SQLException s){
                    s.printStackTrace();
                    connection.rollback();
                }
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        }
    public Integer checkBalance(int account_id){
        //assume account_id is not null
        sql = "Select balance_curr from account where account_id ="+account_id+";";
        try {
            connection = DriverManager.getConnection(url,username,password);
            //need to point to the specific database and schema
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
                return accounts.get(0);
            }

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
        sql = "Select * From transaction where account_from='"+current_account_id+"';";
        try{
            connection = DriverManager.getConnection(url,username,password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData metadata = resultSet.getMetaData();
            int numberOfColumns = metadata.getColumnCount();
            ArrayList<String> accounts= new ArrayList<String>();
            while (resultSet.next()) {
                int i = 1;
                while (i <= numberOfColumns) {
                    accounts.add(resultSet.getString(i++));
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

    public Boolean createAccount(String userName, String account_type){
        sql = "Insert into account(customer_name, account_istype, account_id) VALUES('"+userName+"','"+account_type+"');";
        try{
            connection = DriverManager.getConnection(url,username,password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Member not in list!!!");
        }
        return false;
    }

    public Boolean deleteAccount(int account_id){
        sql = "delete from account where account_id="+account_id+";";
        try{
            connection = DriverManager.getConnection(url,username,password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Member not in list!!!");
        }
        return false;
    }

}