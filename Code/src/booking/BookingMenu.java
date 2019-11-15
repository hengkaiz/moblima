package booking;

import moblima.MenuTemplate;
import user.*;
import cinema.*;

import java.util.Scanner;

public class BookingMenu extends MenuTemplate {
    private MenuTemplate nextMenu;

    public BookingMenu(MenuTemplate previousMenu) {
        super(previousMenu);
    }

    public MenuTemplate run(){
        //your booking code here
        Scanner sc = new Scanner(System.in);
        int sel = 0;
        
        System.out.println("---Booking Menu---");
        System.out.println("1. Get booking details");
        System.out.println("2. Search for movies and book");
        System.out.println("3. Return");
        System.out.print("Please enter your choice: ");
        boolean loop = true;
        do {
        try {
        	sel = sc.nextInt();
        	if (sel < 0 || sel > 3) {
        		throw new Exception();
        	}
        	loop = false;
        } catch (Exception e) {
        	System.out.println("Selection Invalid. Try Again.");
        }
        } while (loop);
        
        System.out.println();
        sc.nextLine(); //clear the buffer

        BookingApp bookingApp = new BookingApp(super.getUsername());

        nextMenu = this;
        switch (sel) {
        case 1: //get booking details
        	bookingApp.getBooking();
        	break;

        case 2: //add new booking
        	nextMenu = new UserSearchMenu(this);
        	break;

        case 3: //return
            super.returnPrevious();
        }
        return nextMenu.run();
    }
}