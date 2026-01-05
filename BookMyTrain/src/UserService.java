import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final Map<String,User> userMap=new HashMap<>();

    private User currentUser=null;

    public boolean registerUser(String userName,String password,String fullName,String contact)
    {
        if(userMap.containsKey(userName))
        {
            System.out.println("Username already taken, Please choose another ");
            return false;
        }
        User user=new User(userName,password,fullName,contact);
        userMap.put(userName,user);
        System.out.println("Registration successful!");
        return true;
    }
    public boolean loginUser(String userName,String password)
    {
        if(!userMap.containsKey(userName))
        {
          System.out.println("No user found with these username");
          return false;
        }
        User user=userMap.get(userName);
        if(!user.getPassword().equals(password))
        {
          System.out.println("Incorrect password");
          return false;
        }
        currentUser=user;
        System.out.println("welcome : "+currentUser.getFullName() + "!");
        return true;
   }

    public void logOutUser()
    {
      if(currentUser!=null)
      {
        System.out.println("Logged Out"+ currentUser.getFullName());
      }
    currentUser=null;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public boolean isLoggedIn(){
        return currentUser!=null;
    }

}
