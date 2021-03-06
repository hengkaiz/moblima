package admin;

import java.util.Scanner;
import java.util.ArrayList;

import moblima.SaveAndLoadDB;
import movies.*;

/**
 * Contains the methods to edit movie listings in a specified cineplex.
 * Specific cineplex number is needed load the correct Movies, Cinemas, and ShowTimes for that cineplex.
 */
public class AdminMovieEditor {
	
	/**
	 * Allows saving and loading of the actual database to and from a local copy.
	 */
	private SaveAndLoadDB saveAndLoadDB = new SaveAndLoadDB();
	
	/**
	 * Specified cineplex number
	 */
	private int cineplexNumber;
	
	/**
	 * Object reference to store local copy of MovieDatabase.
	 */
	private MovieDatabase mdb;
	
	/**
	 * Object reference to point to temporary Movie object used in methods.
	 */
	private Movie selectedMovie;
	
	/**
	 * Constructs AdminMovieEditor object.
	 * Relevant databases are loaded and stored in a local copy for method use.
	 * Relevant databases needed: Movie Database (mdb)
	 * @param cineplexNumber Specified cineplex number.
	 */
	public AdminMovieEditor(int cineplexNumber) {
		this.cineplexNumber = cineplexNumber;
		this.mdb = saveAndLoadDB.loadMovieDB();
	}
	
	/**
	 * Creates a new movie listing.
	 * Saves created movie listing into the actual movie database.
	 */
	public void createMovieListing() { //creating a new Movie object
		mdb.addMovieToDB();
		saveAndLoadDB.saveMovieDB(mdb);
	}
	
	/**
	 * Updates movie listings.
	 * Allows for modifications of attibutes.
	 * Prints out options of attributes for admin to change.
	 * Changes attributes based on admin selection.
	 * Saves updates into the actual movie database.
	 */
	public void updateMovieListing() {
		Scanner sc = new Scanner(System.in);
		int i=0;
		
		ArrayList<Movie> movieList = mdb.getMovies();
		
		//getting movie to update
		int updateMovieChoice = 0;
		System.out.println("Current movies in the database: ");
		i=1;
		for(Movie m : movieList) {
			System.out.println(i + ". " + m.getMovieTitle());
			i++;
		}

        try {
    		System.out.println("Please enter your choice (-1 to exit): ");
    		//need exception in case they enter the movie string instead?
    		updateMovieChoice = sc.nextInt();
    		if(updateMovieChoice == -1) {
    			return;
    		}
    		if (updateMovieChoice<1 || updateMovieChoice>i-1) { 
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid movie. Try Again.");
			return;
		}
        System.out.println();
		selectedMovie = movieList.get(updateMovieChoice-1);
		
		int updateChoice = 0;
		String exit = "-1";
		do {
			//updating of movie title not allowed
			System.out.println("What would you like to update for " + selectedMovie.getMovieTitle());
			System.out.println("1. Update Movie Synopsis");
			System.out.println("2. Update Movie Cast");
			System.out.println("3. Update Movie Director");
			System.out.println("4. Update Movie Overall Rating");
			System.out.println("5. Update Movie Status");
			System.out.println("6. Return");

	        try {
	    		System.out.println("Please enter your choice:"); 
	    		//need exception in case they enter the movie string instead?
	    		updateChoice = sc.nextInt();
				if (updateChoice<1 || updateChoice>6) { //check exceptions?
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("Invalid choice. Try Again.");
				return;
			}
			System.out.println();
			switch(updateChoice) {
			case 1: //update movie synopsis
				sc.nextLine(); //clear buffer
				System.out.println("Enter new Movie Synopsis: ");
				selectedMovie.setMovieSynopsis(sc.nextLine());
				System.out.println(selectedMovie.getMovieTitle() + " Synopsis updated");
				break;
			case 2: //update movie cast
				int castChoice = 0;
				String cast = null; 
				
				System.out.println("Current Cast List:");
				System.out.println(selectedMovie.toStringMovieCast()); //prints the current cast list
				System.out.println("1. Add Cast Member");
				System.out.println("2. Remove Cast Member");
				System.out.println("3. Return");

		        try {
		    		System.out.println("Please enter your choice:"); 
		    		//need exception in case they enter the movie string instead?
		    		castChoice = sc.nextInt();
					if (castChoice<1 || castChoice>3) { //check exceptions?
						throw new Exception();
					}
				} catch (Exception e) {
					System.out.println("Invalid choice. Try Again.");
					return;
				}
				System.out.println();
				switch(castChoice) {
				case 1: //add cast member
					sc.nextLine(); //dummy
					do {
						System.out.println("Enter Cast Member to add: (-1 to exit)");
						cast = sc.nextLine();
						if(cast.contentEquals(exit)) {
							break;
						}
						selectedMovie.addMovieCast(cast);
						System.out.println("Current cast list: " + selectedMovie.toStringMovieCast());
					} while(!cast.contentEquals(exit));
					break;
				case 2: //remove cast member
					sc.nextLine(); //dummy
					do {
						System.out.println("Enter Cast Member to remove: (-1 to exit)");
						cast = sc.nextLine();
						if(cast.contentEquals(exit)){
							break;
						}
						selectedMovie.removeMovieCast(cast);
						System.out.println("Current cast list: " + selectedMovie.toStringMovieCast());
					} while(!cast.contentEquals(exit));
					break;
				default:
					break;
				}
				break;
			case 3: //update movie director
				int directorChoice = 0;
				String director = null;
				
				System.out.println("Current Director List:");
				System.out.println(selectedMovie.toStringMovieDirector()); //prints the current director list
				System.out.println("1. Add Director");
				System.out.println("2. Remove Director");
				System.out.println("3. Return");
		        try {
		    		System.out.println("Please enter your choice:"); 
		    		//need exception in case they enter the movie string instead?
		    		directorChoice = sc.nextInt();
					if (directorChoice<1 || directorChoice>3) { //check exceptions?
						throw new Exception();
					}
				} catch (Exception e) {
					System.out.println("Invalid choice. Try Again.");
					return;
				}
				System.out.println();
				switch(directorChoice) {
				case 1: //add director
					sc.nextLine(); //dummy
					do {
						System.out.println("Enter Director to add: (-1 to exit)");
						director = sc.nextLine();
						if(director.contentEquals(exit)) {
							break;
						}
						selectedMovie.addMovieDirector(director);
						System.out.println("Current director list: " + selectedMovie.toStringMovieDirector());
					} while(!director.contentEquals(exit));
					break;
				case 2: //remove director
					sc.nextLine(); //dummy
					do {
						System.out.println("Enter Director to remove: (-1 to exit)");
						director = sc.nextLine();
						if(director.contentEquals(exit)) {
							break;
						}
						selectedMovie.removeMovieDirector(director);
						System.out.println("Current director list: " + selectedMovie.toStringMovieDirector());
					} while(!director.contentEquals(exit));
					break;
				default:
					break;
				}
				break;
			case 4: //update movie overall rating
				System.out.println("Enter new Movie Overall Rating: ");
				selectedMovie.setMovieOverallRating(sc.nextFloat());
				System.out.println(selectedMovie.getMovieTitle() + " Overall Rating updated");
				break;
			case 5: //update movie status
				int statusChoice=0;
				System.out.println("Choose new status for " + selectedMovie.getMovieTitle() + " :");
				i=1;
				for(MovieStatus status : MovieStatus.values()) {
					System.out.println(i + ". " + status.getName());
					i++;
				}
		        try {
		    		System.out.println("Please enter your choice:"); 
		    		//need exception in case they enter the movie string instead?
		    		statusChoice = sc.nextInt();
					if (statusChoice<1 || statusChoice>i-1) { 
						throw new Exception();
					}
				} catch (Exception e) {
					System.out.println("Invalid choice. Try Again.");
					return;
				}
		        System.out.println();
				for(MovieStatus status : MovieStatus.values()) {				//go through array until find the one equal to user input
					if(status.ordinal()==statusChoice-1){
						selectedMovie.setStatus(status);
					}
				}
				System.out.println("Movie Status updated");
				break;
			case 6: //exit
				break;
			default: //while loop again
				break;
			}
		} while(updateChoice != 6);
		saveAndLoadDB.saveMovieDB(mdb);
	}
	
	/**
	 * Remove a movie listing.
	 * Allow from removing permanently from the database and removing from user view.
	 * Saves updated local copy to actual movie database.
	 */
	public void removeMovieListing() { 		
		Scanner sc = new Scanner(System.in);
		int i=0;
		
		ArrayList<String> movieTitles = mdb.getMovieTitlesList();
		ArrayList<Movie> movieList = mdb.getMovies();
		
		int choice = 0;
		System.out.println("Do you want to delete from database or remove from user view?");
		System.out.println("1. Delete from database ");
		System.out.println("2. Remove from user view (marking movie as \"End of Showing\")");
		System.out.println("3. Return");

        try {
    		System.out.println("Please enter your choice:"); 
    		//need exception in case they enter the movie string instead?
    		choice = sc.nextInt();
			if (choice<1 || choice>3) { //check exceptions?
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid choice. Try Again.");
			return;
		}
        System.out.println();
		
		if(choice == 1) { //remove from database
			mdb.removeMovieFromDB();
			System.out.println("Movie deleted from database");
		}
		if(choice == 2) {
			int updateMovieChoice = 0;
			//choosing movie to remove
			System.out.println("Movies currently in user view:");
			i=1;
			for(String title : movieTitles) {
				System.out.println(i + ". " + title);
				i++;
			}
			System.out.println("Which Movie do you want to remove?");

	        try {
	    		System.out.println("Please enter your choice:"); 
	    		//need exception in case they enter the movie string instead?
	    		updateMovieChoice = sc.nextInt();
				if (updateMovieChoice<1 || updateMovieChoice>i-1) { 
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("Invalid choice. Try Again.");
				return;
			}
	        System.out.println();
			
			String title = movieTitles.get(updateMovieChoice-1);
			for(Movie m : movieList) {
				if(title.equals(m.getMovieTitle())) {
					selectedMovie = m;
				}
			}
			//set status of selectedMovie to "END_OF_SHOWING"
			selectedMovie.setStatus(MovieStatus.END_OF_SHOWING);
			System.out.println("Movie removed from user view");
		}
		if(choice == 3) {
			return;
		}
		saveAndLoadDB.saveMovieDB(mdb);
	}
	
	/**
	 * Shows movie details of all movie listing objects stored in database.
	 */
	public void seeMovie() {
		try {
			mdb.printMovieDetails();
		} catch (Exception e) {
			System.out.println("Invalid Choice. Try Again.");
			return;
		}
	}
}
