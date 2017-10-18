/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantmanagment.User;

/**
 *
 * @author endri
 */
public class User {
    String username;
    private static User instance=null;
    
    public static  User getInstance(){
        if(instance==null){
            instance = new User();
        }
        return instance;
    }
    private User(){
        
    }
      

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
