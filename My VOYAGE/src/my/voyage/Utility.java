/*
implemented by: Razan al-refaey
documented by: Razan al-refaey
-------------------------
this class contains collection of methods the deals with the database.
there is only one method does not relate (sceneChanger).

this class has the listed method:
1- isExist : return boolean if the user exist in the database.
2- getUserIdByEmail : return int by search in the database by user's email.
3- getUser : return User object  by search in the database by user's email.
4- insertNewUser : takes User object and insert it in the database.
5- updateUser :  takes User object and update it in the database.

6- getTodoListByEmail : return list of TodoList object which are the user's tasks.
7- insertNewTask : takes TodoList object and insert it in the database.
8- updateTask: takes TodoList object and update it in the database.
9- clearTasks: takes user object to get his/her email to search the database of user's tasks to delete them.

10- getCityNamesByEmail: takes user's email and return list of string which are the user's cities.
11- insertNewCity: takse City object and save it in the database.
12- updateCity: takes City object and update it in the database.
13- getCityIdByEmail: takes user's email and return list of City which are the user's cities.

14-getLocationListByEmail:returns list of Location which are the user's location.
15- insertNewLocation: takse Location object and save it in the database.
16- updateLocation: takes Location object and update it in the database.
*/
//==================================================================
package my.voyage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Utility {
    //==================================================================
    //this method will be called with every scene change 
    //Utility.sceneChanger(getClass(), event, "fxmlPath");
    public static void sceneChanger (Class aClass, Event anEvent, String nextSceneFxml) throws IOException{
        Parent root = FXMLLoader.load(aClass.getResource(nextSceneFxml));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) anEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();}
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // ----------- USER SECTIOIN -----------
    public static boolean isExist(String enteredEmail){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<String> usersEmailList = null;
        String queryStr = "select u.email from User u"; 
        org.hibernate.Query query = session.createQuery(queryStr);
        usersEmailList = query.list();    
        session.close();    
        for(String s: usersEmailList)
            if(enteredEmail.equals(s))
                return true;
    return false;}
    //==================================================================
    public static int getUserIdByEmail(String userEmail){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Integer> userList = null;
        String queryStr = "select u.userId from User u where email=:userEmail"; 
        
        org.hibernate.Query query = session.createQuery(queryStr);
        query.setParameter("userEmail", userEmail);
        userList = query.list();    
        session.close();
        int id = userList.get(0);
        System.out.println("first index: "+id);
        for(Integer s: userList)
            System.out.println(s);
        System.out.println("FIND User by email: DONE!");
        return id;}  
    //==================================================================
    public static User getUser (String email){
        int id = Utility.getUserIdByEmail(email);
        Session session = HibernateUtil.getSessionFactory().openSession();
        User selectedUser = (User) session.get(User.class, id);
        session.close();
        System.out.println("GET user is DONE");
        return selectedUser;}
    //==================================================================
    public static int insertNewUser(User user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        int id = (Integer) session.save(user);
        tx.commit();
        session.close();
        user.setUserId(id);
        System.out.println("insert User: "+user.getUserName()+" is DONE");
        return id;}
        //==================================================================
    public static void updateUser(User user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(user);
        tx.commit();
        session.close();
        System.out.println("Update is DONE!");}
     //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
     // ----------- TASKS SECTIOIN ----------- 
    public static List<TodoList> getTodoListByEmail(String email){
        int id = Utility.getUserIdByEmail(email);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<TodoList> desList = null;
        String queryStr = "from TodoList u where userId="+id;        
        org.hibernate.Query query = session.createQuery(queryStr);
        desList = query.list();
        session.close();
        for(TodoList s: desList)
            System.out.println("the user name is:"+getUser(email).getUserName()+" and has task:"+s.getDescription());
        return desList;}
    //==================================================================
    public static int insertNewTask(TodoList task){      
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        int taskId = (Integer) session.save(task);
        tx.commit();
        session.close();
        task.setTaskId(taskId);
        System.out.println("insert task: "+task.getDescription()+ " is DONE!");
        return taskId;} 
    //==================================================================
    public static void updateTask(TodoList task){      
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(task);
        tx.commit();
        session.close();
        System.out.println("Update is DONE!");
    }
    //==================================================================
    public static void clearTasks(User user){
        int id = Utility.getUserIdByEmail(user.getEmail());
        System.out.println(id);
        int result = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String queryStr = "delete from TodoList where userId=:id";        
        org.hibernate.Query query = session.createQuery(queryStr);
        query.setParameter("id", id);
        result = query.executeUpdate();
        tx.commit();
        session.close();
        System.out.println("the # of deleted tasks is: "+result);}
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // ----------- CITY SECTIOIN -----------
    public static List<String> getCityNamesByEmail(String email){
        int id = Utility.getUserIdByEmail(email);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<String> cityList = null;
        String queryStr = "select u.cityName from City u where userId="+id;        
        org.hibernate.Query query = session.createQuery(queryStr);
        cityList = query.list();
        session.close();
        for(String s: cityList)
            System.out.println("the user name is:"+getUser(email).getUserName()+" and has city: "+s);
        return cityList;}
    //==================================================================
    public static int insertNewCity(City city){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        int cityId = (Integer) session.save(city);
        tx.commit();
        session.close();
        city.setCityId(cityId);
        System.out.println("insert city: "+city.getCityName()+" is DONE!");
        return cityId;}  
    //==================================================================
    public static void updateCity(City city){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(city);
        tx.commit();
        session.close();
        System.out.println("Update is DONE!");}
    //==================================================================
    public static List<City> getCityIdByEmail(String email){
        int id = Utility.getUserIdByEmail(email);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<City> cityIdList = null;
        String queryStr = "from City c where userId="+id;        
        org.hibernate.Query query = session.createQuery(queryStr);
        cityIdList = query.list();
        session.close();
        for(City s: cityIdList)
            System.out.println("the user name is:"+getUser(email).getUserName()+" and has city:"+ s.getCityName());
        return cityIdList;}
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // ----------- LOCATION SECTIOIN -----------
    public static int insertNewLocation(Location location){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        int locationId = (Integer) session.save(location);
        tx.commit();
        session.close();
        location.setLocationId(locationId);
        System.out.println("insert location: "+location.getLocationName()+" is DONE!");
        return locationId;} 
    //==================================================================
    public static List<Location> getLocationListByEmail(String email){
        // get all locations in database
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Location> locations = null;
        String queryStr = "from Location"; 
        org.hibernate.Query query = session.createQuery(queryStr);
        locations = query.list();    
        session.close();
        System.out.println("location size " + locations.size());
        
        // filter locations by only user's cities
        //get all cities for this user
        List<City> citiesId = getCityIdByEmail(email);
        System.out.println("# of cities for this user: "+citiesId.size());
        
        List<Location> finalLocations = new ArrayList<Location> ();
        int pkCityId , fkCityId = 0;
        //insert the match in final list to return
        if (citiesId.size() > 1){
            for (int i = 0; i<citiesId.size(); i++){
                pkCityId = citiesId.get(i).getCityId();
                for (int j = 0; j<locations.size(); j++){
                   fkCityId = locations.get(j).getCityId().getCityId();
                   if(fkCityId == pkCityId){
                       System.out.println("i'm in");
                       finalLocations.add(locations.get(j));} } //end of if & inner for
            }// end of outer for
        }
        else {finalLocations.addAll(locations);}
        
            System.out.println("size of finalLocations: "+finalLocations.size());
            return finalLocations; }
    //==================================================================
    public static void updateLocation(Location location){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(location);
        tx.commit();
        session.close();
        System.out.println("Update is DONE!");
    }
}
