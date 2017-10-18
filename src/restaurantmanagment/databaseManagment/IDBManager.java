/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantmanagment.databaseManagment;

import restaurantmanagment.User.Waiter;
import java.sql.Connection;
import java.util.ArrayList;
import restaurantmanagment.Item.Category;
import restaurantmanagment.Item.Item;
import restaurantmanagment.Item.Order_details;
import restaurantmanagment.Item.Orders;

/**
 *
 * @author endri
 */
public interface IDBManager {
    
    
    public int login(String username, String password);
    public Connection getConnection(String DB_Url,String DB_User,String Db_Password);
    public Connection getConnection();
    public String getUserType(int roleid);
    public int isWaiter (String username);
    public int addWaiter(Waiter waiter);
    public int updateWaiter(Waiter waiter);
    public Waiter getWaiter(String username);
    public ArrayList<Category> getCategories();
    public ArrayList<Item> getItems();
    public ArrayList<Item> getCategoryItems(int category_id);
    public Item getItem(int itemId );
    public int addOrder(ArrayList<Order_details> orders,String username,double grandTotal);
    public ArrayList<Orders> getOrdersByUsername(String username);
    public ArrayList<Orders> getAllOrders();
    public ArrayList<Order_details> getOrderDetails(int orders_id);
    public int deleteOrder(int orders_id);
    public int addUser(String username,String password,int role_id);
    public int addCategory(String category);
    public int addItem(String item_name,int category_id,double price);
    public int updateItem(int item_id,String item_name,int category_id,double price);
    public int deleteItem(int item_id);
    
    
    
}

