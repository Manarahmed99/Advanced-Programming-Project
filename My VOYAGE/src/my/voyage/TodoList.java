/*
implemented by: Razan al-refaey
documented by: Razan al-refaey
-------------------------
this is a POJO class for the (TodoList) entity/class.
it will be used to:
1- create an instance from this class. a (TodoList) object will be associated with the (User) object.
2- link the object to the database.

this class contains: constructor( full & empty & one without ID) - setters & getters
the constructor without ID is used when creating an instance , ID is generated Automatically in the database.
 */
//==================================================================
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
//==================================================================
@Entity
@Table(name="todolist")
public class TodoList implements Serializable {
    
    //attributes    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="taskId")
    private int taskId;
    
    @Column(name="describtion")
    private String description;
    
    @Column(name="finished")
    private boolean finished;
    
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;
//==================================================================
    //constructor
    public TodoList() {}

    public TodoList(int taskId, String description, boolean finished, User user) {
        this.taskId = taskId;
        this.description = description;
        this.finished = finished;
        this.user = user;}
    
    // use : new TodoList(fetchedUser, "tickets", false);
    public TodoList(User userId, String description, boolean finished){
        this.user = userId;
        this.description = description;
        this.finished = finished;}
//==================================================================   
    //getters
    public int getTaskId()          {   return taskId;      }
    public String getDescription()  {   return description; }
    public boolean isFinished()     {   return finished;    }
    public User getUser()           {   return user;        }
//==================================================================   
    //setters
    public void setTaskId(int taskId) {this.taskId = taskId;}
    public void setDescription(String description) {this.description = description;}
    public void setFinished(boolean finished) {this.finished = finished;}
    public void setUser(User user) {this.user = user;}
}
