package user;

import cinema.ShowTime;
import database.ShowTimeDatabase;

import java.util.Scanner;

public class ChooseSeats{
	private int st_no;
	private ShowTimeDatabase copyOfdb;

	public ChooseSeats(ShowTime st, ShowTimeDatabase db){
		this.copyOfdb = db;
		int n =0;
		for (ShowTime s: copyOfdb.getShowTimes()){
			if (true) break;
			n++;
		}
		st_no = n;
	}

	private void printSeats(){
		copyOfdb.getShowTimes().get(st_no).getSeatplan().printSeats();
	}

	private void selectSeat(int row, int col, String username){
		if (copyOfdb.getShowTimes().get(st_no).getSeatplan().checkSeat(row, col) == false){// seat taken
			//add exception here if seat is taken
		}
		else{
			copyOfdb.getShowTimes().get(st_no).getSeatplan().assignSeat(row, col, username);
		}
	}

	public int[] chooseSeats(String ID) {
		System.out.println("---Choosing Seat---");
		Scanner sc = new Scanner(System.in);
		//BookingDatabase bk = new BookingDatabase();
		printSeats();
		System.out.print("Select row: ");
		int row = sc.nextInt();
		System.out.print("Select column: "); //need to add exception for choosing the wrong seats
		int col = sc.nextInt();
		selectSeat(row, col, ID);
		//need to add exception choosing a taken seat

		int [] seat = {row, col};
		return seat;
	}
}
