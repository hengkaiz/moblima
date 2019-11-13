package Database;

import Cinema.ShowTime;
import Movies. *;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class DataBase{
    private ArrayList<ShowTime> showtimelist = new ArrayList<ShowTime>();
    private ArrayList<ShowTime> copylist;

    public DataBase(){

    }

    private ArrayList<ShowTime> sortMovies(String title){ //return showtimes for chosen movie
        copylist = new ArrayList<ShowTime>();
        for (ShowTime st: showtimelist){
            if (st.getMovie() == title){
                copylist.add(st);
            }
        }
        return copylist;
    }

    private ArrayList<ShowTime> sortDate(String date){ //return showtimes for chosen dates
        copylist = new ArrayList<ShowTime>();
        for (ShowTime st: showtimelist){
            if (st.toStringGetDate() == date){
                copylist.add(st);
            }
        }
        return copylist;
    }

    public ArrayList<ShowTime> searchByMovie(String title){ //search movie by title
        return sortMovies(title);
    }

    public ArrayList<ShowTime> searchByDate(String date){//search movie by date in format dd/MM
        return sortDate(date);
    }

    public ArrayList<ShowTime> getShowTimes(){
    	return showtimelist;
    }
    
    public void addSTToDB(ShowTime st) {
    	showtimelist.add(st);
    }
    public void removeSTToDB(ShowTime st) {
    	if(showtimelist.remove(st)) {
    		st = null;			
    		System.out.println("ShowTime removed");
		}
		else {
			System.out.println("ShowTime does not exist");
		}
    }
    /*public void updateDB(ShowTime st) {
    	
    }*/
}
