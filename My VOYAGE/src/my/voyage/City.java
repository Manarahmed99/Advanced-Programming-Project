/*
implemented by: Razan al-refaey
-------------------------
this is a POJO class for the (City) entity/class.
it will be used to:
1- create an instance from this class. a (City) object will be associated with the (User) object.
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//==================================================================
@Entity
@Table(name="city")
public class City implements Serializable {
    //attributes
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="cityId")
    private int cityId;
    
    @Column(name="name")
    private String cityName;
    
    @Column(name="hotel")
    private String hotel;
    
    @ManyToOne
    @JoinColumn(name="userId")
    private User userId;
    
    @Column(name="aDate")
    private String arrivalDate;
    
    @Column(name="rDate")
    private String returnDate;
    
    @Column(name="budget")
    private int cityBudget;
    
    @Column(name="members")
    private int members;
    
    @Column(name="photo")
    private String cityPhotoURL;
    
    @OneToMany (mappedBy = "cityId")
    private List <Location> locations;
    //==================================================================
    //coustructor
    public City() {}
    // use: new City("damam","hotel", UserObject,"15-3-2022", "20-3-2022", 4000, 3,"photopath",);    
    public City(String cityName, String hotel, User userId, String arrivalDate, String returnDate, int cityBudget, int members, String cityPhotoURL) {
        this.cityName = cityName;
        this.hotel = hotel;
        this.userId = userId;
        this.arrivalDate = arrivalDate;
        this.returnDate = returnDate;
        this.cityBudget = cityBudget;
        this.members = members;
        this.cityPhotoURL = cityPhotoURL;    }

    public City(int cityId, String cityName, String hotel, User userId, String arrivalDate, String returnDate, int cityBudget, int members, String cityPhotoURL, List<Location> locations) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.hotel = hotel;
        this.userId = userId;
        this.arrivalDate = arrivalDate;
        this.returnDate = returnDate;
        this.cityBudget = cityBudget;
        this.members = members;
        this.cityPhotoURL = cityPhotoURL;
        this.locations = locations;
    }
    //==================================================================
    //getters
    public String getCityName()         {   return cityName;        }
    public int getCityBudget()          {   return cityBudget;      }
    public int getMembers()             {   return members;         }
    public String getCityPhotoURL()     {   return cityPhotoURL;    }
    public String getArrivalDate()      {   return arrivalDate;     }
    public String getReturnDate()       {   return returnDate;      }
    public int getCityId()              {   return cityId;          }
    public List<Location> getLocations(){   return locations;       }
    public User getUser()               {   return userId;          }
    public String getHotel()            {   return hotel;           }
    //==================================================================
    //setters
    public void setCityName(String cityName) {this.cityName = cityName;}
    public void setHotel(String hotel) {this.hotel = hotel;}
    public void setCityBudget(int cityBudget) {this.cityBudget = cityBudget;}
    public void setMembers(int members) {this.members = members;}
    public void setCityPhotoURL(String cityPhotoURL) {this.cityPhotoURL = cityPhotoURL;}
    public void setArrivalDate(String arrivalDate) {this.arrivalDate = arrivalDate;}
    public void setReturnDate(String returnDate) {this.returnDate = returnDate;}
    public void setCityId(int cityId) {this.cityId = cityId;}
    public void setUser(User user) {this.userId = user;}
    public void setLocations(List<Location> locations) {this.locations = locations;}

    
    
    
    
    
}
