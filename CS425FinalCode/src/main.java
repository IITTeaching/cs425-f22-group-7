import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        boolean control = true;
        /*My idea is created a connection class and call it when we need it
         * then we can focus more on the implement other function into our project*/
        System.out.println("Welcome to CS425-7 Final Project SQL");
        try{
            App app = new App();
            app.connect();
        }catch(Exception e){
            System.out.println(e.getMessage());

        }
    }
}
