import java.util.Scanner;
import java.util.List;
public class IRCTCAPP {

    private final Scanner scanner=new Scanner(System.in);

    private final UserService userService=new UserService();

    private final BookingService bookingService=new BookingService();

    public static void main(String[] args) {
        new IRCTCAPP().start();
    }

    public void start()
    {
       while(true)
       {
           System.out.println("\n------Welcome to IRCTC APP---------");
           if(!userService.isLoggedIn())
           {
               System.out.println("1. Register");
               System.out.println("2. Login");
               System.out.println("3. Exit");
               System.out.println("Enter choice: ");
               int choice=scanner.nextInt();

               switch(choice)
               {
                   case 1 -> register();
                   case 2 -> login();
                   case 3 -> exitApp();
                   default -> System.out.println("Invalid choice.");
               }
           }
           else{
               showUserMenu();
           }
       }
    }
    public void register()
    {
        System.out.println("Enter Username: ");
        String userName= scanner.next();
        System.out.println("Enter Password: ");
        String password=scanner.next();
        System.out.println("Enter Full Name: ");
        scanner.nextLine();
        String fullName=scanner.next();
        System.out.println("Enter Contact: ");
        String contact=scanner.next();

        userService.registerUser(userName,password,fullName,contact);

    }

    public void login()
    {
        System.out.println("Enter Username: ");
        String userName= scanner.next();
        System.out.println("Enter Password: ");
        String password=scanner.next();

        userService.loginUser(userName,password);
    }

    private void showUserMenu()
    {
        while(userService.isLoggedIn())
        {
            System.out.println("\n--------- User Menu---------");
            System.out.println("1. Search Trains: ");
            System.out.println("2. Book Tickets: ");
            System.out.println("3. View My tickets: ");
            System.out.println("4. Cancel tickets: ");
            System.out.println("5. View All trains: ");
            System.out.println("6. Logout: ");
            System.out.println("Enter choice");
            int choice=scanner.nextInt();
            switch(choice)
            {
                case 1 -> searchTrain();
                case 2 -> bookTicket();
                case 3 -> viewMyTicket();
                case 4 -> cancelTicket();
                case 5 -> bookingService.listAllTrains();
                case 6 -> userService.logOutUser();
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void searchTrain()
    {
        System.out.println("Enter source station: ");
        String source=scanner.next();
        System.out.println("Enter destination station: ");
        String destination=scanner.next();

        List<Train> trains=bookingService.searchTrain(source,destination);
        if(trains.isEmpty()){
            System.out.println("No trains found between "+source +" and " +destination);
            return;
        }
        System.out.println("Trains Found");
        for(Train train:trains){
            System.out.println("train");
        }
        System.out.println("Do you want to book ticket ? (yes/no): ");
        String choice=scanner.next();
        if(choice.equalsIgnoreCase("yes")) {
            System.out.println("Enter TrainId to book: ");
            int trainId = scanner.nextInt();
            System.out.println("Enter number of seats to book: ");
            int seats = scanner.nextInt();

            Ticket ticket= bookingService.bookTicket(userService.getCurrentUser(), trainId, seats);
            if (ticket != null) {
                System.out.println("booking successful");
                System.out.println(ticket);
            }
        }
            else {
                System.out.println("Returning to user menu....");
            }
    }

    private void bookTicket()
    {
        System.out.println("Enter source station: ");
        String source=scanner.next();
        System.out.println("Enter destination station: ");
        String destination=scanner.next();

        List<Train> trains=bookingService.searchTrain(source,destination);
        if(trains.isEmpty()){
            System.out.println("No trains available for booking ");
            return;
        }
        System.out.println("Available Trains");
        for(Train train:trains){
            System.out.println("train");
        }

        System.out.println("Enter TrainId to book: ");
        int trainId = scanner.nextInt();
        System.out.println("Enter number of seats to book: ");
        int seats = scanner.nextInt();

        Ticket ticket = bookingService.bookTicket(userService.getCurrentUser(), trainId, seats);
        if (ticket != null) {
            System.out.println("booking successful!");
            System.out.println(ticket);
        }
    }

    private void viewMyTicket()
    {
        List<Ticket> ticketByUser= bookingService.getTicketByUser(userService.getCurrentUser());
        if(ticketByUser.isEmpty())
        {
            System.out.println("No ticket booked yet");
        }
        else {
            System.out.println("your tickets: ");
            for(Ticket ticket:ticketByUser)
            {
                System.out.println(ticket);
            }
        }
    }

    private void cancelTicket()
    {
        System.out.println("Enter Ticket Id to cancel: ");
        int ticketId = scanner.nextInt();
        bookingService.cancelTicket(ticketId,userService.getCurrentUser());
    }

    void exitApp()
    {
        System.out.println("Thank you for using IRCTC App");
        System.exit(0);
    }
}
