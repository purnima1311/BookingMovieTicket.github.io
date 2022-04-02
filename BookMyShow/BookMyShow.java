import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BookMyShow {
    ArrayList<Theater> theaters;
    static HashMap<String, ArrayList<Show>> movieMap;

    private void generateMovieMap() {
        for (Theater theater : theaters) {
            ArrayList<Show> showArray = theater.getShows();
            for (Show show : showArray) {
                if (show != null) {
                    if (movieMap.containsKey(show.getMovie().getName())) {
                        movieMap.get(show.getMovie().getName()).add(show);
                    } else {
                        ArrayList<Show> movieShowList = new ArrayList<>();
                        movieShowList.add(show);
                        movieMap.put(show.getMovie().getName(), movieShowList);
                    }
                }
            }
        }
    }

    public BookMyShow(ArrayList<Theater> theaters) {
        this.theaters = theaters;
        this.movieMap = new HashMap<>();
        generateMovieMap();
        System.out.println(movieMap);
    }

    public static ArrayList<Show> searchShows(String movieName) {
        if (movieMap.containsKey(movieName)) {
            return movieMap.get(movieName);
        } else
            return null;
    }

    public static void main(String[] args) {
        /* --------Data generation code ----START ----------------- */

        // Creating Guest User --> Purnima
        GuestUser purnima = new GuestUser("Purnima Sharma");

        // Creating Registered User --> Purnima
        RegisteredUser pritika = new RegisteredUser("Pritika Sharma");

        // Creating Registered User --> Tanu
        RegisteredUser tanu = new RegisteredUser("Tanu Sharma");

        // Creating Movie object --> Iron Man
        Movie ironMan = new Movie("Iron Man", Language.ENGLISH, Genre.ACTION);

        // Creating Movie object --> Avengers: End Game
        Movie avengers = new Movie("Avengers: End Game", Language.ENGLISH, Genre.ACTION);

        // Creating Movie object --> The Walk To Remember
        Movie walkToRemember = new Movie("The Walk To Remember", Language.ENGLISH, Genre.ROMANCE);

        // Creating Movie object --> HouseFull2
        Movie housefull = new Movie("HouseFull 2", Language.HINDI, Genre.COMEDY);

        // Creating Theater --> PVR @ GIP Noida with capacity 30
        Theater pvr_gip = new Theater("PVR", "GIP Noida", 30);

        // Creating Another Theater --> BIG Cinema @ Noida Sector 137 with capacity 40
        Theater big_cinema = new Theater("Big Cinema", "Sector 137 Noida", 40);

        // Creating four shows for movies
        Show show1 = null, show2 = null, show3 = null, show4 = null;
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");

        try {
            // Creating Show for Movie Iron Man on 7 Jun 2020 @ 9:00 AM in PVR
            String dateInString = "Friday, Jun 7, 2020 09:00:00 AM";
            Date date = formatter.parse(dateInString);
            show1 = new Show(date, ironMan, pvr_gip);

            // Creating Show for Movie HOUSEFULL on 7 Jun 2020 @ 12:00 PM in PVR
            dateInString = "Friday, Jun 7, 2020 12:00:00 PM";
            date = formatter.parse(dateInString);
            show2 = new Show(date, housefull, pvr_gip);

            // Creating Show for Movie WALK TO REMEMBER on 7 Jun 2020 @ 09:00 AM in
            // BIG-CINEMA
            dateInString = "Friday, Jun 7, 2020 09:00:00 AM";
            date = formatter.parse(dateInString);
            show3 = new Show(date, walkToRemember, big_cinema);

            // Creating Show for Movie WALK TO REMEMBER on 7 Jun 2020 @ 12:00 PM in
            // BIG-CINEMA
            dateInString = "Friday, Jun 7, 2020 12:00:00 PM";
            date = formatter.parse(dateInString);
            show4 = new Show(date, walkToRemember, big_cinema);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        /* --------Data generation code ---- END ----------------- */

        // Book My Show app
        ArrayList<Theater> theaterArrayList = new ArrayList<>();
        theaterArrayList.add(pvr_gip);
        theaterArrayList.add(big_cinema);
        BookMyShow bookMyShow = new BookMyShow(theaterArrayList);

        // Searching Book My Show for all the shows of movie WALK TO REMEMBER
        ArrayList<Show> searchedShow = BookMyShow.searchShows("The Walk To Remember");

        Show bookingShow = searchedShow.get(0);

        // Ticket Booking Thread for the request made by Tanu for 10 Seats
        TicketBookingThread t1 = new TicketBookingThread(bookingShow, tanu, 10);

        // Ticket Booking Thread for the request made by Pritika for 10 Seats
        TicketBookingThread t2 = new TicketBookingThread(bookingShow, pritika, 10);

        // Start both the Ticket Booking Threads for execution
        t1.start();
        t2.start();

        // Waiting till both the thread completes the execution
        try {
            t1.join();
            t2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // After execution t1 will carry AYUSH ticket and t2 will carry SAURABH ticket
        Ticket tanu_ticket = t1.getTicket();
        Ticket pritika_ticket = t2.getTicket();

        // Printing their tickets
        System.out.println(t1.getTicket());
        System.out.println(t2.getTicket());

        // Now, 20 seats are booked for this show and 20 seats are available,
        // Suppose AYUSH wants another 15 seats and SAURABH wants another 10 seats to be
        // booked

        // Ticket Booking Thread for the request made by AYUSH for another 15 Seats
        TicketBookingThread t3 = new TicketBookingThread(bookingShow, tanu, 15);

        // Ticket Booking Thread for the request made by SAURABH for another 10 Seats
        TicketBookingThread t4 = new TicketBookingThread(bookingShow, pritika, 10);

        // Start both the Ticket Booking Threads for execution
        t3.start();
        t4.start();

        // Waiting till both the thread completes the execution
        try {

            t4.join();
            t3.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // After execution t3 will carry AYUSH ticket and t4 will carry SAURABH ticket
        Ticket tanuNewTicket = t3.getTicket();
        Ticket pritikaNewTicket = t4.getTicket();

        System.out.println(tanuNewTicket);
        System.out.println(pritikaNewTicket);

    }
}