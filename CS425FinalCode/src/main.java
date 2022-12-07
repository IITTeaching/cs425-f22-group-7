import java.sql.*;
import java.util.Scanner;
import java.sql.SQLException;
import java.util.ArrayList;


public class main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String username = null;
        String password = null;
        String usertype = null;
        String userrole = null;
        boolean loginFlag = true;
        int[] account_id = null;
        int acc_id = 0; //current account
        int action = -1;
        App app = new App();
        ResultSet result;
        String withdrawlTrans ;
        String alterAccount;
        int remove;
        int to_Remove_From;

        System.out.println("Welcome to CS425-7 Final Project SQL");
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
                String sql = "Select customer_name, passcode FROM customer where customer_name="+username+" and passcode="+password+";";
                result = app.executeSQL(sql);
                if (!((ResultSet) result).next()){
                    //if not customer check employee
                    sql = "Select employee_name, password IF(employee_name =" +username+", (employee_name, password), 0) FROM employee";
                    result = app.executeSQL(sql);
                    if (result.getString("employee_name")==null){
                        throw new SQLException("User does not exist");
                    }else{
                        userrole = result.getString("employee_type");
                        loginFlag = false;

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
        if (usertype == "customer"){

            //SQL
            //get all the current user account number and display

            int i = 0;
            while (true) {
                try {
                    if (!result.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    account_id[i] = Integer.parseInt(result.getString("account_id"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            for (int x = 0;x<account_id.length;x++){
                int temp = x+1;
                System.out.println(temp+". "+account_id[x]);
            }
            System.out.println("Select the account that you want to modify with: ");
            int userselect = scan.nextInt();
            acc_id = account_id[userselect-1];
            scan.nextLine();
        }


        switch (usertype){
            case "customer":
                String[] customer_menu = {"1. Withdrawal","2. Deposit","3. Transfer","4. Current balance","5. Current Month Transaction","9. switch account","0. logout"};
                for (int i=0;i<customer_menu.length;i++){
                    System.out.println(customer_menu[i]);
                }
                //scanner that read user input then do the action

                //todo: if user enter 0(logout), do System.exit(0);
                break;
            case "teller":
                String[] teller_menu = {"1.Withdrawal", "2. Deposit", "3. Transfer","9. switch account","0. logout"};
                for (int i=0;i<teller_menu.length;i++){
                    System.out.println(teller_menu[i]);
                }
            case "manager":
                String[] manager_menu = {"1. Withdrawal", "2. Deposit", "3. Transfer", "4. Check balance", "5. Current Month Transaction","9. switch account","0. logout"};
                for (int i=0;i<manager_menu.length;i++){
                    System.out.println(manager_menu[i]);
                }
        }

        //action
        //This section assumes that the program already has a verified account Id logged in with the user.
        //If a customer, then, you should already have been vetted to ensure its an account you really have access to




        while(true) {
            try {
                action = scan.nextInt();
                switch (action) {
                    //cases 1,2,3,4,5,9,0
                    case 1: //withdrawal
                        if (usertype == "customer") {
                            System.out.println("Please enter an ammount to withdrawl ");
                            remove = scan.nextInt();
                            withdrawlTrans = "insert into transaction (type, quantity, description, transaction_date, account_from, account_to, status) " +
                                    "Values ('Withdrawal', " + remove + ", 'Withdrawal of " + remove + " from " + acc_id + "', now(), " + acc_id + ", NULL, '0');";
                            alterAccount = "Update account" + "set balance_curr = balance_curr - " + remove + "where account_id = " + acc_id + ";";
                            //SQL to add a new entry to transaction list with the acc_Id and the amount in the correct columns
                            //additionally, alter the account table with this list to update the balance
                        }
                        else{
                            System.out.println("Please enter an account ID to withdrawl from ");
                            to_Remove_From = scan.nextInt();

                            System.out.println("Please enter an ammount to withdrawl ");
                            remove = scan.nextInt();

                            withdrawlTrans = "insert into transaction (type, quantity, description, transaction_date, account_from, account_to, status, Values ('Withdrawl', " + remove + ", 'Withdrawl of " + remove + " from " + to_Remove_From + "', now(), " + to_Remove_From + ", NULL, '0');";
                            alterAccount = "Update account" + "set balance_curr = balance_curr - " + remove + "" + "where account_id = " + to_Remove_From + ";";
                            //SQL to add a new entry to transaction list with the acc_Id and the ammount in the correct columns
                            //additionally, alter the account table with this list to update the balance
                        }
                        app.executeSQL(withdrawlTrans);
                        app.executeSQL(alterAccount);
                        break;
                    case 2:
                        //deposit
                        if (usertype == "customer") {
                            System.out.println("Please enter an ammount to deposit ");
                            remove = scan.nextInt();

                            withdrawlTrans = "insert into transaction (type, quantity, description, transaction_date, account_from, account_to, status, Values ('Deposit', " + remove + ", 'Deposit of " + remove + " from " + acc_id + "', now(), NULL, " + acc_id + ", '0');";
                            alterAccount = "Update account" + "set balance_curr = balance_curr + " + remove + "" + "where account_id = " + acc_id + ";";
                            //SQL to add a new entry to transaction list with the acc_Id and the amount in the correct columns
                            //additionally, alter the account table with this list to update the balance
                        }
                        else {
                            System.out.println("Please enter an account ID to deposit to ");
                            to_Remove_From = scan.nextInt();

                            System.out.println("Please enter an amount to deposit ");
                            remove = scan.nextInt();

                            withdrawlTrans = "insert into transaction (type, quantity, description, transaction_date, account_from, account_to, status, Values ('Deposit', " + remove + ", 'Deposit of " + remove + " from " + to_Remove_From + "', now(), NULL, " + acc_id + ", '0');";
                            alterAccount = "Update account" + "set balance_curr = balance_curr + " + remove + "" + "where account_id = " + to_Remove_From + ";";
                            //SQL to add a new entry to transaction list with the acc_Id and the ammount in the correct columns
                            //additionally, alter the account table with this list to update the balance
                        }
                        app.executeSQL(withdrawlTrans);
                        app.executeSQL(alterAccount);
                        break;

                        //SQL to add a new entry to transaction list with the acc_Id and the ammount in the correct columns
                        //additionally, alter the account table with this list to update the balance
                    case 3:
                        //transfer
                        //sql for account and update database
                        String transferTrans;
                        String alterAccount1;
                        String alterAccount2;
                        int transBal;
                        int transferFrom;
                        int transferTo;

                        if (usertype == "manager" || usertype == "teller"){

                            System.out.println("Please enter an account ID to transfer to: ");
                            transferFrom = scan.nextInt();


                            System.out.println("Please enter an account ID to transfer from: ");
                            transferTo = scan.nextInt();


                            System.out.println("Please enter a transfer ammount: ");
                            transBal = scan.nextInt();

                            transferTrans = "insert into transaction (type, quantity, description, transaction_date, account_from, account_to, status, Values ('Transfer', " + remove + ", 'Transfer from " + transferFrom + " to " + transferTo + " of quantity" + transBal + "', now(), "+transferFrom+", " + transferTo + ", '0');";
//data type is incorrect
                            //transferFrom = "Update account" + "set balance_curr = balance_curr - " + transBal + "" + "where account_id = " + transferFrom + ";";
                            //transferTo = "Update account" + "set balance_curr = balance_curr + " + transBal + "" + "where account_id = " + transferTo + ";";
                            app.executeSQL(transferTrans);
                            //app.executeSQL(transferFrom);
                            //app.executeSQL(transferTo);
                                //SQL to create the needed transaction entry, and change the account balances involved.

                        }
                        else {
                            System.out.println("Please enter an account ID to transfer to: ");
                            transferFrom = scan.nextInt();

                            System.out.println("Please enter a transfer amount: ");
                            transBal = scan.nextInt();

                            transferTrans = "insert into transaction (type, quantity, description, transaction_date, account_from, account_to, status, Values ('Transfer', " + remove + ", 'Transfer from " + acc_id + " to " + transferTo + " of quantity" + transBal + "', now(), "+acc_id+", " + transferTo + ", '0');";
//data type is incorrect
                            //transferFrom = "Update account" + "set balance_curr = balance_curr - " + transBal + "" + "where account_id = " + transferFrom + ";";
                            //transferTo = "Update account" + "set balance_curr = balance_curr + " + transBal + "" + "where account_id = " + transferTo + ";";
                            app.executeSQL(transferTrans);
                            //app.executeSQL(transferFrom);
                            //app.executeSQL(transferTo);
                                //SQL to create the needed transaction entry, and change the account balances involved.
                            //the account from is known by the login
                        }



                    case 4:
                        //check balance
                        //employee
                        if (usertype == "manager"){
                            System.out.println("Please enter an account ID: ");
                            int acc_Id = scan.nextInt();

                            ArrayList<Integer> ids = new ArrayList<Integer>();
                            if (ids.contains(acc_Id)) {
                                /*amount*/ System.out.println();
                            }

                        }
                        else if (usertype=="customer"){
                            //SQL to get balance for the account that is logged in
                            //print that result to screen
                        }
                        else {
                            System.out.println("Permission Denied");
                        }


                    case 5:
                        //Get Statement for an account
                        if (usertype == "manager"){
                            ArrayList<Integer> valid_Billing_Ids = new ArrayList<Integer>();
                            //SQL to obtain a list of acceptable account ID's, put as a list into valid_Ids

                            System.out.println("Please enter an account ID to get a Bank Statement for ");
                            int acc_to_Id = scan.nextInt();


                            if(!(valid_Billing_Ids.contains(acc_to_Id))) { //if the id given isnt a legit id, then break the switch
                                break;
                            }

                            //SQL to get the statement at the ID

                        }else if(usertype == "customer"){

                            //SQL to get the statement at the ID logged into as.

                        }
                        else {
                            System.out.println("Permission Denied");
                        }

                    case 9:
                        //Switch account
                        //employee part
                        if (usertype == "manager" || usertype == "teller"){

                        System.out.println("Please enter an account ID: ");
                        int acc_Id = scan.nextInt();

                        if(!(valid_Ids.contains(acc_to_Id)))  { //if the id given isnt a legit id, then break the switch
                        break;
                        }

                        System.out.println("Please enter an account ID to switch to ");
                        int acc_from_id = scan.nextInt();

                        } else if (usertype == "customer") {

                            while (true) {
                                try {
                                    for (int i = 0; i < acc; i++) {
                                        System.out.println(i + 1 + ". " + account_id[i]);
                                    }
                                    int userinput = scan.nextInt();
                                    scan.nextLine();
                                    if (i == 0 || i > account_id.length) {
                                        throw new Exception("Invalid input, Please try again");
                                    }
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }


                    case 0:
                        //logout
                        System.exit(0);
                    default:
                        throw new Exception("Invalid input, Please try again");
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }




        

    }
}
