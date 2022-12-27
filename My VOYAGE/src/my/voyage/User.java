/*
implemented by: Razan al-refaey
documented by : Razan al-refaey
-------------------------
this is a POJO class for the (User) entity/class.
it will be used to:
1- create an instance from this class. a (User) object is the main object in our application.
   it won't be any other instance of other classes if we don't have an User instance.
2- link the object to the database.

this class contains: constructor(full & empty & one without ID) - setters & getters
the constructor without ID is used when creating an instance , ID is generated Automatically in the database.
 */
//==================================================================
package my.voyage;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//==================================================================
@Entity
@Table(name="user")
public class User implements Serializable {
    // user attributes
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="userId")
    private int userId;
    
    @Column(name="userName")
    private String userName;
    
    @Column(name="gender")
    private String gender;
    
    @Column(name="phone")
    private String phoneNumber;
    
    @Column(name="email")
    private String email;
    
    @Column(name="password")
    private String password;
      
    @OneToMany (mappedBy = "userId", targetEntity = City.class)
    private List <City> cities;

    @OneToMany (mappedBy = "user", targetEntity = TodoList.class)
    private List <TodoList> tasks;
    //==================================================================
    //constructors
    public User() {}
    public User(int userId, String userName, String phoneNumber, String email, String password, String gender) {
        this.userId = userId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.gender = gender;}
    
    //use it to create an instance can be passed to the DB without an (id) because it generates automatically in the DB
    //use : new User("razan", "0523698785","email", "password", "gender"); 
    public User(String userName, String phoneNumber, String email, String password, String gender) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.gender = gender;}
    //==================================================================
    //getters
    public int getUserId()          {   return userId;      }
    public String getUserName()     {   return userName;    }
    public String getPhoneNumber()  {   return phoneNumber; }
    public String getEmail()        {   return email;       }
    public String getPassword()     {   return password;    }
    public String getGender()       {   return gender;      }
    public List<City>getCities()    {   return cities;      }
    public List<TodoList> getTasks(){   return tasks;       }
    //==================================================================
    //setters
    public void setUserId(int userId) {this.userId = userId;}
    public void setUserName(String userName) {this.userName = userName;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    public void setGender(String gender) {this.gender = gender;} 
    
    public void setCities(List<City> cities) {this.cities = cities;}
    public void setTasks(List<TodoList> tasks) {this.tasks = tasks;}    
}
