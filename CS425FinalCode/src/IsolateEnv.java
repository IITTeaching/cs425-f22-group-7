/*
import java.sql.*;
import java.util.Scanner;
import java.sql.SQLException;

public class IsolateEnv {
    public static void main(String[] args) {
        boolean loginFlag = true;
        Scanner input = new Scanner(System.in);
        int selectRole = 0;
        //user information
        String userRole = "";
        String userName = "";
        String passCode = "";

        //end user information
        String sql = "";

        App app = new App();
        while(loginFlag) {
            System.out.println("Please select the role: \n" +
                    "1.customer\n" +
                    "2.teller\n"+
                    "3.manager");
            selectRole = Integer.parseInt(input.nextLine());
            if (selectRole == 1) {
                userRole = "customer";
            } else if (selectRole == 2) {
                userRole = "employee";
            }else if(selectRole == 3){
                userRole = "manager";
            }
            else {
                userRole = null;
            }

            System.out.println("Please enter your username: ");
            userName = input.nextLine();

            System.out.println("Please enter your password: ");
            passCode = input.nextLine();

            boolean login_status = app.login(userName,passCode,userRole);
            if (login_status == true){
                loginFlag = false;
            }else {
                System.out.println("Username or Password or Role select is incorrect, Please check and try again");
                System.exit(1);
            }
        }//while end

        //Account part
            while(accountFlag){
                if (userRole == "customer") {
                    //check account
                    customer_name = userName;
                    customer_account = app.checkAccount(customer_name);
                    if (customer_account.size()==0){
                        System.out.println("We cannot find any account from database!");
                        System.exit(1);
                    }
                    for (int i = 0; i < customer_account.size(); i++) {
                        int index = i+1;
                        System.out.println(index + ". "+customer_account.get(i));
                    }
                } else if (userRole == "teller" || userRole == "manager") {
                    //getting the customer name who need help
                    System.out.println("Please enter the customer name you are working with: ");
                    customer_name = input.nextLine();
                    //check if in db
                    isCustomer = app.checkCustomer(customer_name);
                    if (isCustomer == false) {
                        System.out.println("Customer are not in list!!!");
                        System.exit(1);
                    }
                    //check all the account under this customer
                    customer_account = app.checkAccount(customer_name);
                    if (customer_account.size()==0){
                        System.out.println("We cannot find any account from database!");
                        System.exit(1);
                    }
                    for (int i = 0; i < customer_account.size(); i++) {
                        System.out.println(customer_account.get(i));
                    }
                }
                System.out.println("Please select one of the account to continue: ");
                //pick the account
                String temp_input = input.nextLine();
                if (temp_input == null){
                    System.out.println("Please input the valid number!!!");
                    System.exit(1);
                }
                int temp = Integer.parseInt(temp_input);
                temp -= 1;
                current_account_number = Integer.parseInt(String.valueOf(customer_account.get(temp)));
                accountFlag =false;
                break;
            }

        //menu part

    }
}

*/
