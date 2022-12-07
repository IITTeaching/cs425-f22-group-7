import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String username = null;
        String password = null;
        String usertype = null;
        String userrole = null;
        boolean loginFlag = true;
        int action = -1;
        App app = new App();
        System.out.println("Welcome to CS425-7 Final Project SQL");
        //while?
        while(loginFlag) {
            try {
                System.out.println("Please enter the username: ");
                username = scan.nextLine();
                if(username.isEmpty()){
                    throw new Exception("Username is Empty");
                }
                System.out.println("Please enter the password: ");
                password = scan.nextLine();
                if (password.isEmpty()){
                    throw new Exception("Password is empty");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Please try again\n");
            }
            try{
                //String sql = "Select username, password IF customer_name ="+username+" password = "+password+"   FROM customer, employee";
                String sql = "Select customer_name, passcode FROM customer where customer_name="+username+" and passcode="+password+;
                ResultSet result = app.executeSQL(sql);
                if (!result.next()){
                    //if not customer check employee
                    String sql = "Select employee_name, password IF(employee_name =" +username+", (employee_name, password), 0) FROM employee"
                    ResultSet result = app.executeSQL(sql);
                    if (result.getString("employee_name")==null){
                        throw new SQLException("User does not exist");
                    }else{
                        userrole = result.getSting("employee_t")
                    }
                }else{
                    userrole = "customer";
                    loginFlag = false;
                }
            }catch (SQLException s){
                System.out.println(s.getMessage());
                System.out.println("Please try again\n");
            }



            //SELECT OrderID, Quantity, IF(Quantity>10, "MORE", "LESS")
            //FROM OrderDetails;
        }


        //coop with sql return
        //java part got the type and output the menu
        //type might be {customer,teller,manager}

        //if type is customer, get and display all the account that user want to access currently
        switch (usertype){
            case "customer":
                String[] customer_menu = {"1. Withdrawal","2. Deposit","3. Transfer","4. Current balance","5. Transaction","9. switch account","0. logout"};
                for (int i=0;i<customer_menu.length;i++){
                    System.out.println(i+". "+customer_menu[i]);
                }
                //scanner that read user input then do the action

                //todo: if user enter 0(logout), do System.exit(0);
                break;
            case "teller":
                String[] teller_menu = {"1.Withdrawal", "2. Deposit", "3. Transfer","9. switch account","0. logout"};
            case "manager":
                String[] manager_menu = {"1. Withdrawal", "2. Deposit", "3. Transfer", "4. Check balance", "5. Transaction","9. switch account","0. logout"};
                for (int i=0;i<manager_menu.length;i++){
                    System.out.println(manager_menu[i]);
                }
        }

        //action
        while(true) {
            try {
                action = scan.nextInt();
                switch (action) {
                    //cases 1,2,3,4,5,9,0
                    case 1:
                        //withdrawal
                        //sql for account and update database
                    case 2:
                        //deposit
                    case 3:
                        //transfer
                    case 4:
                        //check balance
                    case 5:
                        //
                    case 9:

                    case 0:

                    default:
                        throw new Exception("Invalid input, Please try again");
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }




        

    }
}
