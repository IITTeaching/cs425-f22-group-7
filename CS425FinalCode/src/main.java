import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String username = null;
        String password = null;

        System.out.println("Welcome to CS425-7 Final Project SQL");
        System.out.println("Please enter the username: ");
        username = scan.nextLine();
        System.out.println("Please enter the password: ");
        password = scan.nextLine();

        //sql part, send username and password to database and check if there is a user in list and return the type
        //type might be {customer,teller,manager}

        //if type is customer, get and display all the account that user want to access currently

        String[] customer_menu = {"Current balance","Transfer","Withdraw","Deposit","Transaction"};
        String[] teller_menu = {"Withdrawal", "Deposit", "transfer", "External Transfer"};
        String[] manager_menu = {"Withdrawal", "Deposit", "transfer", "External Transfer", "Check balance of user", "Transactions of User"};

        

    }
}
