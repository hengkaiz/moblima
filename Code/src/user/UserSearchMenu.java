package user;

import cinema.ShowTime;
import moblima.MenuTemplate;
import movies.ListTopFiveMenu;
//import movies.MovieDatabase;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * a menu that lets user choose to search by movie title, date or view top movies
 */
public class UserSearchMenu extends MenuTemplate {
    /**
     * the next menu to run
     */
    private MenuTemplate nextMenu;
    /**
     * menu constructor
     * @param previousMenu points to the previous menu
     */
    public UserSearchMenu(MenuTemplate previousMenu) {
        super(previousMenu);
    }
    /**
     * prints menu options and asks user to pick an option
     * the next menu will be run based on user input
     */
    public MenuTemplate run() {
        Scanner sc = new Scanner(System.in);
        ShowTime copyOfSelShowTime;
        int sel = 0;

        System.out.println("\n---Search Show times---");
        System.out.println("1. By Movie");
        System.out.println("2. By Date");
        System.out.println("3. Top 5 Movies");
        System.out.println("4. Return");
        System.out.print("Please enter your choice: ");
        try {
			sel = sc.nextInt();
			if (sel<0 || sel>4) {
				throw new Exception("Error, Input Choice Only From 1-4");
			}
		} catch (Exception e) {
			System.out.println("Invalid Choice. Try Again.");
		}
        
        UserSearchApp userSearchApp = new UserSearchApp(super.getCineplexNum());

        nextMenu = this;
        switch(sel){
            case 1: //calls search by movie
                ShowTime selST = userSearchApp.SearchByMovie();
                if (selST != null) nextMenu = new UserChooseSeatsMenu(this, selST);
                break;

            case 2: //calls search by date
                ShowTime selST2 = userSearchApp.SearchByDate();
                if (selST2 != null) nextMenu = new UserChooseSeatsMenu(this, selST2);
                break;

            case 3: //list top 5 movies
                /*MovieDatabase mdb = new MovieDatabase();
                mdb.printTop5();*/
            	nextMenu = new ListTopFiveMenu(this);
            	break;
            case 4: //returns
                super.returnPrevious();
        }

        nextMenu.setCineplexNum(super.getCineplexNum());
        nextMenu.setUsername(super.getUsername());
        return nextMenu.run();
    }
}
