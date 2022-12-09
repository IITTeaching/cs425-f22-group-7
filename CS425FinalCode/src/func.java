/*
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.SQLException;

public class func {
        public static void main(String[] args) {
            boolean loginFlag = true;
            boolean accountFlag = true;
            boolean menuFlag = true;
            boolean checkInput = true;

            Scanner input = new Scanner(System.in);
            int selectRole = 0;
            //user information
            String userRole = "";
            String userName = "";
            String passCode = "";

            String customer_name = "";
            boolean isCustomer = false;
            int current_account_number = 0;
            ArrayList<Integer> customer_account = new ArrayList<>();
            int target_account_number = 0;
            boolean external=false;
            int modify_values=0;
            int current_account_balance = 0;
            int delete_account_id = 0;
            String account_type = "";
            ArrayList<String> transaction;

            int menu_selected = 0;
            boolean menu_ret;

            String[] customer_menu = {"1. Withdrawal","2. Deposit","3. Transfer","4. Current balance","5. Current Month Transaction","9. switch account","0. logout"};
            String[] teller_menu = {"1.Withdrawal", "2. Deposit", "3. Transfer","9. switch account","0. logout"};
            String[] manager_menu = {"1. Withdrawal", "2. Deposit", "3. Transfer", "4. Check balance", "5. Current Month Transaction","6.Create account","7.Delete account","9. switch account","0. logout"};

            //end user information
            App app = new App();
            //login part
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
        System.out.println("Login in Successful!");


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
            System.out.println("Here is your Menu!");



            //menu part
            while(menuFlag){
                String temp;
                if (userRole == "customer"){
                    for (int i=0;i<customer_menu.length;i++){
                        System.out.println(customer_menu[i]);
                    }
                    //1,2,3,4,5,9,0
                    System.out.println("Please select one to continue: ");
                    menu_selected = Integer.parseInt(input.nextLine());
                    switch (menu_selected){
                        case 1:
                            //withdrawal
                            System.out.println("Please enter the amount that you want to withdrawal");
                            temp = input.nextLine();
                            if (temp.length() !=0){
                                modify_values = Integer.parseInt(temp);
                            } else {
                                System.out.println("Input invalid!");
                                break;
                            }
                            menu_ret = app.withdrawal(current_account_number,modify_values);
                            if (menu_ret){
                                System.out.println("Success!");
                            } else{
                                System.out.println("Fail!");
                            }
                            break;
                        case 2:
                            System.out.println("Please enter the amount that you want to deposit");
                            temp = input.nextLine();
                            if (temp.length() !=0){
                                modify_values = Integer.parseInt(temp);
                            } else {
                                System.out.println("Input invalid!");
                                break;
                            }
                            menu_ret = app.deposit(current_account_number,modify_values);
                            if (menu_ret){
                                System.out.println("Success!");
                            } else{
                                System.out.println("Fail!");
                            }
                            break;
                        case 3:
                            //target account id
                            System.out.println("Please enter the account you want to transfer to:");
                            target_account_number = Integer.parseInt(input.nextLine());
                            //external
                            System.out.println("Is "+target_account_number+" is external bank account? y/n");
                            String temp_y = "y";
                            String temp_n = "n";
                            if (temp_y.equalsIgnoreCase(input.nextLine())){
                                external = true;
                            } else if(temp_n.equalsIgnoreCase(input.nextLine())){
                                external = false;
                            }else{
                                System.out.println("Invalid input!!!");
                                break;
                            }
                            //value
                            System.out.println("Please enter the amount that you want to transfer to "+target_account_number+": ");
                            temp = input.nextLine();
                            if (temp.length() !=0){
                                modify_values = Integer.parseInt(temp);
                            } else {
                                System.out.println("Input invalid!");
                                break;
                            }

                            menu_ret = app.transfer(current_account_number,target_account_number,modify_values,external);
                            if (menu_ret){
                                System.out.println("Success!");
                            } else{
                                System.out.println("Fail!");
                            }
                            break;
                        case 4:
                            //check balance
                            current_account_balance = app.checkBalance(current_account_number);
                            System.out.println("Current account: "+current_account_number+" Balance: "+current_account_balance);
                            break;
                        case 5:
                            //transaction
                            transaction = app.checkTransaction(current_account_number);
                            System.out.println("======================================");
                            for (int i=0;i<transaction.size();i++){
                                System.out.println(transaction.get(i).toString());
                            }
                            System.out.println("======================================");
                            break;
                        case 9:
                            //switch account
                            customer_account = app.checkAccount(customer_name);
                            for (int i=0;i<customer_account.size();i++){
                                int tempi = i+1;
                                System.out.println(tempi+". "+customer_account.get(i));
                            }
                            System.out.println("Please enter the number to continue: ");
                            temp = input.nextLine();
                            if (temp.length()==0){
                                System.out.println("Invalid input");
                                break;
                            }
                            current_account_number = customer_account.get(Integer.parseInt(temp)-1);
                            break;
                        case 0:
                            //end
                            System.out.println("Thank you for using!\n Good bye!");
                            System.exit(0);
                        default:
                            System.out.println("Invalid input!!!");
                    }
                }else if (userRole == "teller"){
                    for (int i=0;i<teller_menu.length;i++){
                        System.out.println(teller_menu[i]);
                    }
                    //1,2,3,9,0
                    System.out.println("Please Select one to continue");
                    menu_selected = Integer.parseInt(input.nextLine());
                    switch (menu_selected) {
                        case 1:
                            //withdrawal
                            System.out.println("Please enter the amount that you want to withdrawal");
                            temp = input.nextLine();
                            if (temp.length() !=0){
                                modify_values = Integer.parseInt(temp);
                            } else {
                                System.out.println("Input invalid!");
                                break;
                            }
                            menu_ret = app.withdrawal(current_account_number,modify_values);
                            if (menu_ret){
                                System.out.println("Success!");
                            } else{
                                System.out.println("Fail!");
                            }
                            break;
                        case 2:
                            System.out.println("Please enter the amount that you want to deposit:");
                            temp = input.nextLine();
                            if (temp.length() !=0){
                                modify_values = Integer.parseInt(temp);
                            } else {
                                System.out.println("Input invalid!");
                                break;
                            }
                            menu_ret = app.deposit(current_account_number,modify_values);
                            if (menu_ret){
                                System.out.println("Success!");
                            } else{
                                System.out.println("Fail!");
                            }
                            break;
                        case 3:
                            //Transfer
                            System.out.println("Please enter the account you want to transfer to:");
                            target_account_number = Integer.parseInt(input.nextLine());
                            System.out.println("Is "+target_account_number+" is external bank account? y/n");
                            String temp_y = "y";
                            String temp_n = "n";
                            if (temp_y.equalsIgnoreCase(input.nextLine())){
                                external = true;
                            } else if(temp_n.equalsIgnoreCase(input.nextLine())){
                                external = false;
                            }else{
                                System.out.println("Invalid input!!!");
                                break;
                            }
                            System.out.println("Please enter the amount that you want to transfer to "+target_account_number);
                            modify_values = Integer.parseInt(input.nextLine());
                            menu_ret = app.transfer(current_account_number,target_account_number,modify_values,external);
                            if (menu_ret){
                                System.out.println("Success!");
                            } else{
                                System.out.println("Fail!");
                            }
                            break;
                        case 9:
                            //switch account
                            customer_account = app.checkAccount(customer_name);
                            for (int i=0;i<customer_account.size();i++){
                                int tempi = i+1;
                                System.out.println(tempi+". "+customer_account.get(i));
                            }
                            System.out.println("Please enter the number to continue: ");
                            temp = input.nextLine();
                            if (temp.length()==0){
                                System.out.println("Invalid input");
                                break;
                            }
                            current_account_number = customer_account.get(Integer.parseInt(temp)-1);
                            break;
                        case 0:
                            //end
                            System.out.println("Thank you for using!\n Good bye!");
                            System.exit(0);
                        default:
                            System.out.println("Invalid input!!!");
                    }
                }else if(userRole == "manager"){
                    for (int i=0;i<manager_menu.length;i++){
                        System.out.println(manager_menu[i]);
                    }
                    //1,2,3,4,5,6,7,9,0
                    System.out.println("Please select one to continue");
                    menu_selected = Integer.parseInt(input.nextLine());
                    switch (menu_selected){
                        case 1:
                            System.out.println("Please enter the amount that you want to withdrawal");
                            temp = input.nextLine();
                            if (temp.length() !=0){
                                modify_values = Integer.parseInt(temp);
                            } else {
                                System.out.println("Input invalid!");
                                break;
                            }
                            menu_ret = app.withdrawal(current_account_number,modify_values);
                            if (menu_ret){
                                System.out.println("Success!");
                            } else{
                                System.out.println("Fail!");
                            }
                            break;
                        case 2:
                            System.out.println("Please enter the amount that you want to deposit");
                            temp = input.nextLine();
                            if (temp.length() !=0){
                                modify_values = Integer.parseInt(temp);
                            } else {
                                System.out.println("Input invalid!");
                                break;
                            }
                            menu_ret = app.deposit(current_account_number,modify_values);
                            if (menu_ret){
                                System.out.println("Success!");
                            } else{
                                System.out.println("Fail!");
                            }
                            break;
                        case 3:
                            System.out.println("Please enter the account you want to transfer to:");
                            target_account_number = Integer.parseInt(input.nextLine());
                            System.out.println("Is "+target_account_number+" is external bank account? y/n");
                            String temp_y = "y";
                            String temp_n = "n";
                            if (temp_y.equalsIgnoreCase(input.nextLine())){
                                external = true;
                            } else if(temp_n.equalsIgnoreCase(input.nextLine())){
                                external = false;
                            }else{
                                System.out.println("Invalid input!!!");
                                break;
                            }
                            System.out.println("Please enter the amount that you want to transfer to "+target_account_number);
                            modify_values = Integer.parseInt(input.nextLine());
                            menu_ret = app.transfer(current_account_number,target_account_number,modify_values,external);
                            if (menu_ret){
                                System.out.println("Success!");
                            } else{
                                System.out.println("Fail!");
                            }
                            break;
                        case 4:
                            current_account_balance = app.checkBalance(current_account_number);
                            System.out.println("Current account: "+current_account_number+" Balance: "+current_account_balance);
                            break;
                        case 5:
                            transaction = app.checkTransaction(current_account_number);
                            System.out.println("======================================");
                            for (int i=0;i<transaction.size();i++){
                                System.out.println(transaction.get(i).toString());
                            }
                            System.out.println("======================================");
                            break;
                        case 6:
                            //create account
                            System.out.println("1. Credit\n"+
                                                    "2.Checking\n"+
                                    "Please select the account type: ");
                            temp = input.nextLine();
                            if (temp.length()==0){
                                System.out.println("invalid input");
                                break;
                            }
                            if(Integer.parseInt(temp) == 1){
                                account_type = "Credit";
                            } else if(Integer.parseInt(temp)==2){
                                account_type = "Checking";
                            }else {
                                System.out.println("Invalid input");
                                break;
                            }
                            menu_ret = app.createAccount(customer_name, account_type);
                            if (menu_ret){
                                System.out.println("Success!");
                            } else{
                                System.out.println("Fail!");
                            }
                            break;
                        case 7:
                            customer_account = app.checkAccount(customer_name);
                            for (int i=0;i<customer_account.size();i++){
                                System.out.println("=> "+customer_account.get(i));
                            }
                            while(checkInput){
                                System.out.println("Please enter the account number you need to delete: ");
                                temp = input.nextLine();
                                if (temp.length()==0){
                                    System.out.println("Invalid input");
                                    continue;
                                }
                                delete_account_id = Integer.parseInt(temp);
                                for (int i=0;i<customer_account.size();i++){
                                    if (delete_account_id == customer_account.get(i)){
                                        checkInput = false;
                                        break;
                                    }
                                }
                                System.out.println("Invalid input number, please re-enter with correct number!");
                            }
                            menu_ret = app.deleteAccount(delete_account_id);
                            if (menu_ret){
                                System.out.println("Success!");
                            } else{
                                System.out.println("Fail!");
                            }
                            break;
                        case 9:
                            customer_account = app.checkAccount(customer_name);
                            for (int i=0;i<customer_account.size();i++){
                                int tempi = i+1;
                                System.out.println(tempi+". "+customer_account.get(i));
                            }
                            System.out.println("Please enter the number to continue: ");
                            temp = input.nextLine();
                            if (temp.length()==0){
                                System.out.println("Invalid input");
                                break;
                            }
                            current_account_number = customer_account.get(Integer.parseInt(temp)-1);
                            break;
                        case 0:
                            //end
                            System.out.println("Thank you for using!\n Good bye!");
                            System.exit(0);
                        default:
                            System.out.println("Invalid input!!!");
                    }
                }else{
                    System.out.println("Invalid user role, program end by prevent attack.");
                    System.exit(0);
                }
            }

        }
}

*/
