package user;

import moblima.MenuTemplate;

import java.util.Scanner;

public class UserLoginMenu extends MenuTemplate {
    private MenuTemplate nextMenu;

    public UserLoginMenu(MenuTemplate previousMenu) {
        super(previousMenu);
    }

    public MenuTemplate run(){
        Scanner sc = new Scanner(System.in);
        UserLoginApp userLoginApp = new UserLoginApp();

        System.out.print("Enter ID: ");
        String ID = sc.nextLine();
        System.out.print("Enter password: ");
        String Password = sc.nextLine();

        nextMenu = this;

        if (userLoginApp.loginCheck(ID, Password)) {
            System.out.println("Welcome, " + ID + "\n");
            //nextMenu =
        }

        return nextMenu.run();
    }
}
