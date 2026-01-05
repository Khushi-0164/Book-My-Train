import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookingService {
    private List<Train> trainList = new ArrayList<>();

    private List<Ticket> ticketList = new ArrayList<>();

    public BookingService() {
        trainList.add(new Train(101, 100, "Delhi", "Rajdhani Express", "Nagpur"));
        trainList.add(new Train(102, 70, "Delhi", "Shtabdi Express", "Mumbai"));
        trainList.add(new Train(103, 60, "Agra", "Vande Bharat Express", "Delhi"));
        trainList.add(new Train(104, 100, "Delhi", "Intercity Express", "Goa"));
        trainList.add(new Train(105, 90, "Kolkata", "Khushi Express", "Manali"));
        trainList.add(new Train(106, 80, "Delhi", "bharat Express", "Bengluru"));
    }

    public List<Train> searchTrain(String source, String Destination)
    {
        List<Train> res = new ArrayList<>();
        for (Train train : trainList) {
            if (train.getSource().equalsIgnoreCase(source) && train.getDestination().equalsIgnoreCase(Destination))
            {
                res.add(train);
            }
        }
        return res;
    }

    public Ticket bookTicket(User user, int trainId, int seatCount)
    {
        for (Train train : trainList) {
            if (train.getTrainId() == trainId) {
                if (train.bookSeats(seatCount)) {
                    Ticket ticket = new Ticket(seatCount, train, user);
                    ticketList.add(ticket);
                    return ticket;
                } else {
                    System.out.println("Not enough seat available");
                    return null;
                }
            }
        }
        System.out.println("Train Id not found");
        return null;
    }

    public List<Ticket> getTicketByUser(User user)
    {
        List<Ticket> res=new ArrayList<>();
        for(Ticket ticket:ticketList)
        {
            if(ticket.getUser().getUserName().equalsIgnoreCase(user.getUserName()))
            {
                res.add(ticket);
            }
        }
        return res;
    }

    public boolean cancelTicket(int ticketId,User user)
    {
        Iterator<Ticket> iterator=ticketList.listIterator();
        while(iterator.hasNext())
        {
          Ticket ticket=iterator.next();
          if(ticket.getTicketId()==ticketId && ticket.getUser().getUserName().equalsIgnoreCase(user.getUserName()))
          {
              Train train=ticket.getTrain();
              train.cancelSeats(ticket.getSeatBooked());
              iterator.remove();
              System.out.println("ticket "+ticketId +" Cancelled Successfully");
              return true;
          }
        }
        System.out.println("Ticket not found or does not belong to current user");
        return false;
    }

    public void listAllTrains()
    {
        System.out.println("List of all trains");
        for(Train train:trainList)
        {
            System.out.println(train);
        }
    }
}
