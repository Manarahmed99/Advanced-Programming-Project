/*
implemented by: Razan al-refaey
-------------------------
this is a POJO class for the (Location) entity/class.
it will be used to:
1- create an instance from this class. a (Location) object is the main object in our application.
2- link the object to the database.

this class contains: constructor(full & empty & one without ID) - setters & getters
the constructor without ID is used when creating an instance , ID is generated Automatically in the database.
 */
package my.voyage;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
//==================================================================
@Entity
@Table(name="location")
public class Location implements Serializable {       
    //attributes
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="locationId")
    private int locationId;
    
    @Column(name="name")
    private String locationName;
    
    @ManyToOne
    @JoinColumn(name="cityId")
    private City cityId;
    
    @Column(name="vDate")
    private String visitDate;
    
    @Column(name="vTime")
    private String visitTime;
    
    @Column(name="budget")
    private int locationBudget;
    
    @Column(name="link")
    private String locationLink;
    
    @Column(name="photo")
    private String locationPhoto;
//==================================================================
    //constructor
    public Location() {}
    public Location(int locationId, String locationName, City cityId, String visitDate, String visitTime, int locationBudget, String locationLink, String locationPhoto) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.cityId = cityId;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.locationBudget = locationBudget;
        this.locationLink = locationLink;
        this.locationPhoto = locationPhoto;}

    // use: new Location ("dinner", c1,"15-9-2022", "4:30", 500, "https/:gmknjmflf.com", "image path" );
    public Location(String locationName, City cityId, String visitDate, String visitTime, int locationBudget, String locationLink, String locationPhoto) {
        this.locationName = locationName;
        this.cityId = cityId;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.locationBudget = locationBudget;
        this.locationLink = locationLink;
        this.locationPhoto = locationPhoto;
    }
//==================================================================
    //getters
    public int getLocationId()      {   return locationId;     }
    public String getLocationName() {   return locationName;   }
    public int getLocationBudget()  {   return locationBudget; }
    public String getVisitDate()    {   return visitDate;      }
    public String getVisitTime()    {   return visitTime;      }
    public String getLocationLink() {   return locationLink;   }
    public String getLocationPhoto(){   return locationPhoto;  }
    public City getCityId()         {   return cityId;         }
//==================================================================
    //setters
    public void setLocationId(int locationId) {this.locationId = locationId;}
    public void setLocationName(String locationName) {this.locationName = locationName;}
    public void setLocationBudget(int locationBudget) {this.locationBudget = locationBudget;}
    public void setVisitDate(String visitDate) {this.visitDate = visitDate;}
    public void setVisitTime(String visitTime) {this.visitTime = visitTime;}
    public void setLocationLink(String locationLink) {this.locationLink = locationLink;}
    public void setLocationPhoto(String locationPhoto) {this.locationPhoto = locationPhoto;}
    public void setCityId(City cityId) {this.cityId = cityId;}       
}
