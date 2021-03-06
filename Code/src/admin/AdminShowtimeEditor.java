package admin;

import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import moblima.SaveAndLoadDB;
import cinema.*;
import movies.*;

/**
 * Contains the methods to edit showtimes in a specified cineplex.
 * Specific cineplex number is needed load the correct Movies, Cinemas, and ShowTimes for that cineplex.
 */
public class AdminShowtimeEditor {
	
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
	 * Object reference to store local copy of ShowTimeDatabase.
	 */
	private ShowTimeDatabase stdb;
	
	/**
	 * Object reference to store local copy of Cineplex
	 */
	private Cineplex cineplex;
	
	/**
	 * Object reference to point to temporary ShowTime object used in methods.
	 */
	private ShowTime st;
	
	/**
	 * Constructs AdminShowTimeEditor object.
	 * Relevant databases are loaded and stored in a local copy for method use.
	 * Relevant databases needed: Movie Database (mdb)
	 *                            ShowTime Database (stdb)
	 *                            Cineplex (cineplex)
	 * @param cineplexNumber Specified cineplex number.
	 */
	public AdminShowtimeEditor(int cineplexNumber){
		this.cineplexNumber = cineplexNumber;
		this.mdb = saveAndLoadDB.loadMovieDB();
		this.stdb = saveAndLoadDB.loadShowTimeDB(this.cineplexNumber);
		this.cineplex = saveAndLoadDB.loadCineplex(this.cineplexNumber);
	}
	
	/**
	 * Creates a new show time available for the specified cinema number
	 * Saves created show time into the actual show time database.
	 * @param cinemaNum Specified cinema number.
	 */
	public void createShowtimes(int cinemaNum) {
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
		int sel=0, i=1;
		Movie m = null;
		ArrayList<String> movieTitles = mdb.getMovieTitlesList(); //only gets 'now showing' and 'preview' movies
		ArrayList<Movie> movieList = mdb.getMovies();
		
		//get timing
		System.out.println("Enter Timing (hhhh format):");
		int timing = sc.nextInt();
		
		//get movie
		System.out.println("Current movies showing: ");
		i=1;
		for(String title : movieTitles){
			System.out.printf("%d. %s\n", i, title);
			i++;
		}

        try {
    		System.out.println("Please enter your choice:"); 
    		//need exception in case they enter the movie string instead?
			sel = sc.nextInt();
			if (sel<1 || sel>i-1) { 
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid movie. Try Again.");
			return;
		}
        System.out.println();
		
		String movieTitle = movieTitles.get(sel-1);
		for(Movie movie : movieList){
			if(movieTitle.equals(movie.getMovieTitle())) {
				m = movie;
				break;
			}
		}
		
		//get number of days from current
		int numOfDaysFromCurrent=0;
		Calendar date = Calendar.getInstance();
		//prints out dates of next 5 days
		System.out.println("Select Date:");
		for(i=0; i<5; i++) {
			String output = sdf.format(date.getTime()); //converts it to dd/MM format
			System.out.printf("%d. %s\n", i+1, output);
			date.add(Calendar.DAY_OF_MONTH, 1);
		}

		try {
			System.out.print("Please enter your choice (-1 to return): ");
			numOfDaysFromCurrent = sc.nextInt();
			if (numOfDaysFromCurrent == -1) {
				return;
			}
			if(numOfDaysFromCurrent<1 || numOfDaysFromCurrent>5) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid Date. Try Again.");
			return;
		}
		System.out.println();
		
		//get movie format
		MovieFormat movieFormat = movieFormatSelection();
		if(movieFormat==null) {
			return;
		}
		
		//create new ST object
		this.st = new ShowTime(timing, m, numOfDaysFromCurrent-1, this.cineplexNumber, cinemaNum, movieFormat);
		stdb.addSTToDB(this.st);
		
		//update showtime database
		saveAndLoadDB.saveShowTimeDB(stdb, cineplexNumber);
	}

	/**
	 * Updates show times of selected movie.
	 * Allows for modifications of showtime attributes.
	 * Prints out options of attributes for admin to modify.
	 * Changes attributes based on admin selection.
	 * Saves updates into the actual show time database.
	 */
	public void updateByMovie() {
		Scanner sc = new Scanner(System.in);
		int i=0, sel=0;
		ArrayList<String> movieTitles = mdb.getMovieTitlesList();
		
		//select movie to show showtimes
		System.out.println("Current movies showing: ");
		i=1;
		for(String title : movieTitles){
			System.out.printf("%d. %s\n", i, title);
			i++;
		}

        try {
    		System.out.println("Please enter your choice:"); 
    		//need exception in case they enter the movie string instead?
			sel = sc.nextInt();
			if (sel<1 || sel>i-1) { 
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid movie. Try Again.");
			return;
		}
        System.out.println();
		
        //get selected movie title
		String movieTitle = movieTitles.get(sel-1);
		
		//get list of showtimes for the selected movie
		ArrayList<ShowTime> stByMovie = stdb.searchByMovie(movieTitle);

		//update main
		int chooseST = 0;
		do {
			if(stByMovie.size() == 0) {
				System.out.println("No showtimes available for " + movieTitle);
				break;
			}
			//print current showtimes for movie selected
			System.out.println("Current showtimes for " + movieTitle);
			i=1;
			for(ShowTime sT : stByMovie) {
				System.out.println(i + ". Showing at " + sT.timeToString() + " on " + sT.toStringGetDate() + " in " + sT.getMovieFormat().getName() + ". " + sT.getCinemaType());
				i++;
			}

	        try {
	        	System.out.println("Please enter your choice (enter -1 to return):"); 
				chooseST = sc.nextInt();
				if(chooseST == -1) {
					break;
				}
				if (chooseST<1 || chooseST>i-1) {
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("Invalid showtime. Try Again.");
				return;
			}
	        System.out.println();

	        //get ST to update
			st = stByMovie.get(chooseST-1);
			
			//choosing what to update
			int updateChoice = 0;
			do {
				System.out.println("---Update Menu---");
				System.out.println("1. Change Timing"); //same date new timing
				System.out.println("2. Change Cinema Number");
				System.out.println("3. Change Movie Format");
				System.out.println("4. Return");

		        try {
		        	System.out.println("Please enter your choice:"); 
		        	updateChoice = sc.nextInt();
					if (updateChoice<1 || updateChoice>4) { //check exceptions?
						throw new Exception();
					}
				} catch (Exception e) {
					System.out.println("Invalid choice. Try Again.");
					return;
				}
		        System.out.println();

				switch(updateChoice) {
				case 1: //change timing
					String oldTiming = st.timeToString();
					System.out.println("Enter new timing");
					//no error checking if another st have the same timing as newtiming
					st.setTiming(sc.nextInt());
					System.out.println(st.getMovie() + " now showing at "+ st.timeToString() + " instead of " + oldTiming);
					break;
				case 2: //change cinema number
					int cinemaNum = cinemaSelection();
					if(cinemaNum==-1) {
						return;
					}
					st.setAssignedCinema(cineplex.getCinemas().get(cinemaNum-1));
					System.out.println(st.getMovie() + " now showing at cinema Number " + st.getCinemaNum());
					break;
				case 3: //change movie format
					MovieFormat movieFormat = movieFormatSelection();
					if(movieFormat==null) {
						return;
					}
					st.setMovieFormat(movieFormat);
					System.out.println(st.getMovie() + " now showing in " + st.getMovieFormat().getName());
					break;
				case 4: //return
					break;
				default:
					break;
				}
			}while(updateChoice != 4);
			
		} while(chooseST != -1);
		//update showtime database
		saveAndLoadDB.saveShowTimeDB(stdb, cineplexNumber);
	}
	
	/**
	 * Updates show times of selected date.
	 * Allows for modifications of showtime attributes.
	 * Prints out options of attributes for admin to modify.
	 * Changes attributes based on admin selection.
	 * Saves updates into the actual show time database.
	 */
	public void updateByDate() {
		Scanner sc = new Scanner(System.in);
		int i=0;
		ArrayList<String> movieTitles = mdb.getMovieTitlesList();
		ArrayList<Movie> movieList = mdb.getMovies();
		
		System.out.println("Enter date (dd/mm): ");
		String date = sc.nextLine(); //exception handling?
		ArrayList<ShowTime> stByDate = stdb.searchByDate(date); //get list of all the ST at that date	
		
		//update main
		int chooseST = 0;
		do {
			if(stByDate.size() == 0) {
				System.out.println("No showtimes available for " + date);
				break;
			}
			//print current showtimes for movie selected
			System.out.println("Current showtimes showing on " + date);
			i=1;
			for(ShowTime sT : stByDate){
				System.out.println(i + ". " + sT.getMovie() + " showing at " + sT.timeToString() + " in " + sT.getMovieFormat().getName() + ". " + sT.getCinemaType());
				i++;
			}

	        try {
	        	System.out.println("Please enter your choice (enter -1 to return):"); 
				chooseST = sc.nextInt();
				if(chooseST == -1) {
					break;
				}
				if (chooseST<1 || chooseST>i-1) { 
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("Invalid showtime. Try Again.");
				return;
			}
	        System.out.println();
			
			st = stByDate.get(chooseST-1);
			
			//choosing what to update
			int updateChoice = 0;
			do {
				System.out.println("---Update Menu---");
				System.out.println("1. Change Timing"); //same date new timing
				System.out.println("2. Change Movie"); //same timing new movie
				System.out.println("3. Change Cinema Number");
				System.out.println("4. Change Movie Format");
				System.out.println("5. Return");

		        try {
		        	System.out.println("Please enter selection: "); 
		        	updateChoice = sc.nextInt();
					if (updateChoice<1 || updateChoice>5) { //check exceptions?
						throw new Exception();
					}
				} catch (Exception e) {
					System.out.println("Invalid choice. Try Again.");
					return;
				}
		        System.out.println();
				switch(updateChoice) {
				case 1: //change timing
					String oldTiming = st.timeToString();
					System.out.println("Enter new timing (hhhh format):");
					//no error checking if another st have the same timing as newtiming
					st.setTiming(sc.nextInt());
					System.out.println(st.getMovie() + " now showing at "+ st.timeToString() + " instead of " + oldTiming);
					break;
				case 2: //change movie
					i=1;
					int newMovieChoice=0;
					i=1;
					for(String title : movieTitles){
						System.out.println(i + ". " + title);
						i++;
					}

			        try {
						System.out.println("Please enter your choice: ");
			    		//need exception in case they enter the movie string instead?
						newMovieChoice = sc.nextInt();
						if (newMovieChoice<1 || newMovieChoice>i-1) {
							throw new Exception();
						}
					} catch (Exception e) {
						System.out.println("Invalid movie. Try Again.");
						return;
					}
			        System.out.println();

					String newMovieTitle = movieTitles.get(newMovieChoice -1);
					for(Movie movie : movieList) {
						if(newMovieTitle.equals(movie.getMovieTitle())) {
							st.setMovie(movie);
						}
					}
					System.out.println(st.getMovie() + " now showing at " + st.timeToString());
					break;
				case 3: //change cinema number
					int cinemaNum = cinemaSelection();
					if(cinemaNum==-1) {
						return;
					}
					st.setCinemaNum(cinemaNum);
					System.out.println(st.getMovie() + " now showing at Cinema Number " + st.getCinemaNum());
					break;
				case 4: //change movie format
					MovieFormat movieFormat = movieFormatSelection();
					if(movieFormat==null) {
						return;
					}
					st.setMovieFormat(movieFormat);
					System.out.println(st.getMovie() + " now showing in " + st.getMovieFormat().getName());
					break;
				case 5: //return
					break;
				default:
					break;
				}
			}while(updateChoice != 5);
		}while(chooseST != -1);
		
		//update showtime database
		saveAndLoadDB.saveShowTimeDB(stdb, cineplexNumber);
	}
	
	/**
	 * Remove a showtime.
	 * Permanently removes show time from database.
	 * Saves updated local copy to actual showtime database. 
	 */
	public void removeShowtimes() {
		Scanner sc = new Scanner(System.in);
		int i=0;
		ArrayList<ShowTime> stList = stdb.getShowTimes();
		
		int stChoice = 0;
		i=1;
		for(ShowTime st : stList) {
			System.out.println(i + ". " + st.getMovie() + " showing at " + st.timeToString() + " on " + st.toStringGetDate() + " in " + st.getMovieFormat().getName() + ". " + st.getCinemaType());
			i++;
		}

        try {
        	System.out.println("Please enter your choice (-1 to return):");
    		//need exception in case they enter the movie string instead?
        	stChoice = sc.nextInt();
    		if(stChoice == -1) {
    			return;
    		}
			if (stChoice<1 || stChoice>i-1) { 
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid showtime. Try Again.");
			return;
		}
        System.out.println();

		st = stList.get(stChoice-1);
		
		String movieTitle = st.getMovie();
		String timing = st.timeToString();
		stdb.removeSTToDB(st);
		System.out.println(movieTitle + " at " + timing + " removed!");
		saveAndLoadDB.saveShowTimeDB(stdb, cineplexNumber);
	}
	
	/**
	 * Selects cinema.
	 * Prints out all the cinemas in the specified cineplex with the cinema type.
	 * Allows admin to select cinema.
	 * @return Specified cinema number
	 */
	public int cinemaSelection() {
		Scanner sc = new Scanner(System.in);
		ArrayList<Cinema> cinemaList = cineplex.getCinemas();
		int sel=0,i=1;
		
		System.out.println("---Select Cinema---");
		i=1;
		for(Cinema cinema : cinemaList) {
			System.out.printf("%d. Cinema %d: %s\n", i, cinema.getCinemaNumber(), cinema.getType());
			i++;
		}

        try {
        	System.out.println("Please enter your choice:");
			sel = sc.nextInt();
			if (sel<1 || sel>i-1) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid cinema number. Try Again.");
			return -1;
		}
        System.out.println();
		
        return (sel);
	}
	
	/**
	 * Selects movie format.
	 * Prints out all the movie formats.
	 * Allows admin to select movie format.
	 * @return Specified movie format
	 */
	public MovieFormat movieFormatSelection() {
		Scanner sc = new Scanner(System.in);
		int sel=0, i=0;
		
		System.out.println("---Choose Movie Format--- ");
		i=1;
		for(MovieFormat mf : MovieFormat.values()) {
			System.out.println(i + ". " + mf.getName());
			i++;
		}

        try {
    		System.out.println("Please enter your choice:"); 
    		sel = sc.nextInt();
			if (sel<1 || sel>i-1) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Invalid format. Try Again.");
			return null;
		}
        System.out.println();
		
        MovieFormat movieFormat = null;
		for(MovieFormat mf : MovieFormat.values()) { //go through array until find the one equal to user input
			if(mf.ordinal() == sel-1){
				movieFormat = mf;
				break;
			}
		}
		return movieFormat;
	}
}
