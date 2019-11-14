package user;

import moblima.MenuTemplate;

import java.util.Scanner;

public class UserMainMenu extends MenuTemplate {
    private MenuTemplate nextMenu;

    public UserMainMenu(MenuTemplate previousMenu) {
        super(previousMenu);
    }

    public MenuTemplate run() {
        Scanner sc = new Scanner(System.in);
        int sel;

        System.out.println("---User Menu---");
        System.out.println("1. Login");
        System.out.println("2. Create Account");
        System.out.println("3. Return");
        System.out.print("Please enter your choice: ");
        sel = sc.nextInt();
        System.out.println();
        sc.nextLine(); //clear the buffer

        nextMenu = this;
        switch (sel) {
            case 1: //login menu
                break;
            case 2: //create account menu
                break;
            case 3: //return
                super.returnPrevious();
        }
        return nextMenu.run();
    }
}