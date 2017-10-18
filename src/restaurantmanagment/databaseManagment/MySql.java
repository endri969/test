/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantmanagment.databaseManagment;

import restaurantmanagment.User.Waiter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import restaurantmanagment.Item.Category;
import restaurantmanagment.Item.Item;
import restaurantmanagment.Item.Order_details;
import restaurantmanagment.Item.Orders;

/**
 *
 * @author endri
 */
public class MySql implements IDBManager {

    private String DB_URL = "jdbc:mysql://localhost:3306/db_retaurant?zeroDateTimeBehavior=convertToNull";
    private String DB_USER = "root";
    private String DB_PASSWORD = "";
    private static MySql instance = null;

    public static MySql getInstance() {
        if (instance == null) {
            instance = new MySql();
        }
        return instance;
    }

    private MySql() {

    }

    @Override
    public int isWaiter(String username) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM `waiter` WHERE username= ? ";
        int isuser = 0;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                isuser++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isuser;
    }

    @Override
    public String getUserType(int roleid) {

        Connection conn = getConnection();

        String sql = "SELECT * FROM `role` WHERE role_id = ?";
        PreparedStatement ps = null;
        String userType = null;

        try {

            ps = conn.prepareStatement(sql);
            ps.setInt(1, roleid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                userType = rs.getString("description");

            }

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userType;
    }

    @Override
    public int login(String username, String password) {
        Connection conn = getConnection(DB_URL, DB_USER, DB_PASSWORD);

        String sql = "SELECT `username`, `password`, `role_id` FROM `user` WHERE username=? and password=?";
        PreparedStatement ps = null;
        int roleId = 0;

        try {

            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                roleId = rs.getInt("role_id");

            }

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roleId;
    }

    @Override
    public Connection getConnection(String DB_Url, String DB_User, String Db_Password) {
        Connection dbConnection = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(
                    DB_Url, DB_User, Db_Password);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;
    }

    @Override
    public Connection getConnection() {
        Connection dbConnection = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;
    }

    @Override
    public int addWaiter(Waiter waiter) {
        Connection conn = getConnection();
        String sql = "INSERT INTO `waiter`(`username`, `name`, `surname`, `phone`, `email`,`gender`) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = null;
        int row = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, waiter.getUsername());
            ps.setString(2, waiter.getName());
            ps.setString(3, waiter.getSurname());
            ps.setString(4, waiter.getPhone());
            ps.setString(5, waiter.getEmail());
            ps.setString(6, waiter.getGender());
            row = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;

    }
    @Override
    public int addUser(String username,String password,int role_id) {
        Connection conn = getConnection();
        String sql = "INSERT INTO `user`(`username`, `password`, `role_id`) VALUES (?,?,?)";
        PreparedStatement ps = null;
        int row = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, role_id);
            row = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;

    }
    public int addCategory(String category){
     Connection conn = getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO `category`(`category_description`) VALUES (?)";
        int row = 0;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, category);
            row  = ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }

        return row;
    }
    public int addItem(String item_name,int category_id,double price){
     Connection conn = getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO `item`( `item_name`, `category_id`, `item_price`) VALUES (?,?,?)";
        int row = 0;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, item_name);
            ps.setInt(2, category_id);
            ps.setDouble(3, price);
            row  = ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }

        return row;
    }
    @Override
    public int updateItem(int item_id,String item_name,int category_id,double price){
     Connection conn = getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE `item` SET `item_name`=?,`category_id`=?,`item_price`=? WHERE `item_id`=?";
        int row = 0;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, item_name);
            ps.setInt(2, category_id);
            ps.setDouble(3, price);
            ps.setInt(4, item_id);
            row  = ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }

        return row;
    }
    @Override
    public int deleteItem(int item_id){
     Connection conn = getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM `item` WHERE item_id=?";
        int row = 0;

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, item_id);
            row  = ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }

        return row;
    }
    @Override
    public int deleteOrder(int orders_id){
     Connection conn = getConnection();
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        String sql = "DELETE FROM `order_details` WHERE orders_id=?";
        String sql1 = "DELETE FROM `orders` WHERE orders_id=?";
        int row = 0;

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orders_id);
            row  = ps.executeUpdate();
            ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, orders_id);
            row  = ps1.executeUpdate();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }

        return row;
    }

    @Override
    public int addOrder(ArrayList<Order_details> orders, String username, double grandTotal) {
        String sql1 = "INSERT INTO `order_details`(`orders_id`, `item_id`, `quantity`) VALUES (?,?,?)";
        String sql2 = "INSERT INTO `orders`( `username`, `date_added`, `total`) VALUES (?,CURRENT_TIMESTAMP(),?)";
        Connection conn = getConnection();
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        int row = 0;
        if (username != "" && orders != null && grandTotal != 0) {

            try {
                ps = conn.prepareStatement(sql2);
                ps.setString(1, username);
                ps.setDouble(2, grandTotal);

                row = ps.executeUpdate();
                String sql3 = "SELECT * FROM `orders` ORDER BY orders_id DESC LIMIT 1 ";
                ps1 = conn.prepareStatement(sql3);
                ResultSet rs = ps1.executeQuery();
                int orders_id = 0;
                if (rs.next()) {
                    orders_id = rs.getInt("orders_id");
                }
                for (Order_details o : orders) {

                    ps2 = conn.prepareStatement(sql1);
                    ps2.setInt(1, orders_id);
                    ps2.setInt(2, o.getItem().getItemId());
                    ps2.setInt(3, o.getQuantity());
                    row = ps2.executeUpdate();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return row;

    }

    @Override
    public int updateWaiter(Waiter waiter) {
        Connection conn = getConnection();
        String sql = "UPDATE `waiter` SET `name`=?,`surname`=?,`phone`=?,`email`=?,`gender`=? WHERE username=?";
        PreparedStatement ps = null;
        int row = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, waiter.getName());
            ps.setString(2, waiter.getSurname());
            ps.setString(3, waiter.getPhone());
            ps.setString(4, waiter.getEmail());
            ps.setString(5, waiter.getGender());
            ps.setString(6, waiter.getUsername());
            row = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;

    }

    @Override
    public Waiter getWaiter(String username) {
        Connection conn = getConnection(DB_URL, DB_USER, DB_PASSWORD);
        Waiter waiter = Waiter.getInstance();
        String sql = "SELECT * FROM `waiter` WHERE username=? ";
        PreparedStatement ps = null;
        int roleId = 0;

        try {

            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                waiter.setUsername(rs.getString("username"));
                waiter.setName(rs.getString("name"));
                waiter.setSurname(rs.getString("surname"));
                waiter.setPhone(rs.getString("phone"));
                waiter.setEmail(rs.getString("email"));
                waiter.setGender(rs.getString("gender"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return waiter;
    }

    @Override
    public ArrayList<Category> getCategories() {
        Connection conn = getConnection(DB_URL, DB_USER, DB_PASSWORD);
        ArrayList<Category> categories = new ArrayList<>();

        String sql = "SELECT * FROM `category` WHERE 1";
        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category cat = new Category();
                cat.setCategory_id(rs.getInt("category_id"));
                cat.setDescription(rs.getString("category_description"));
                categories.add(cat);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    @Override
    public ArrayList<Orders> getOrdersByUsername(String username) {
        Connection conn = getConnection(DB_URL, DB_USER, DB_PASSWORD);
        ArrayList<Orders> orders = new ArrayList<>();

        String sql = "SELECT * FROM `orders` WHERE username=?";
        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Orders o = new Orders();
                o.setOrders_id(rs.getInt("orders_id"));
                o.setDate(rs.getDate("date_added"));
                o.setGrand_total(rs.getDouble("total"));
                
                orders.add(o);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }
    @Override
    public ArrayList<Orders> getAllOrders() {
        Connection conn = getConnection(DB_URL, DB_USER, DB_PASSWORD);
        ArrayList<Orders> orders = new ArrayList<>();

        String sql = "SELECT * FROM `orders` ";
        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Orders o = new Orders();
                o.setUsername(rs.getString("username"));
                o.setOrders_id(rs.getInt("orders_id"));
                o.setDate(rs.getDate("date_added"));
                o.setGrand_total(rs.getDouble("total"));
                
                orders.add(o);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }
    @Override
    public ArrayList<Order_details> getOrderDetails(int orders_id) {
        Connection conn = getConnection(DB_URL, DB_USER, DB_PASSWORD);
        ArrayList<Order_details> order_details = new ArrayList<>();

        String sql = "SELECT * FROM `order_details` WHERE orders_id=?";
        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement(sql);
            ps.setInt(1,orders_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order_details o = new Order_details();
                o.setId(rs.getInt("id"));
                o.setOrders_id(rs.getInt("orders_id"));
                o.setItem(getItem(rs.getInt("item_id")));
                o.setQuantity(rs.getInt("quantity"));
                order_details.add(o);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return order_details;
    }
    @Override
    public Item getItem(int itemId) {
        Connection conn = getConnection(DB_URL, DB_USER, DB_PASSWORD);
        Item item = new Item();

        String sql = "SELECT * FROM `item` WHERE item_id=?";
        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement(sql);
            ps.setInt(1, itemId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                item.setItemId(rs.getInt("item_id"));
                item.setCategryId(rs.getInt("category_id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemPrice(rs.getDouble("item_price"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    @Override
    public ArrayList<Item> getItems() {
        Connection conn = getConnection(DB_URL, DB_USER, DB_PASSWORD);
        ArrayList<Item> items = new ArrayList<>();

        String sql = "SELECT * FROM `item` WHERE 1";
        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getInt("item_id"));
                item.setCategryId(rs.getInt("category_id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemPrice(rs.getDouble("item_price"));
                items.add(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    @Override
    public ArrayList<Item> getCategoryItems(int category_id) {
        Connection conn = getConnection(DB_URL, DB_USER, DB_PASSWORD);
        ArrayList<Item> items = new ArrayList<>();

        String sql = "SELECT * FROM `item` WHERE category_id=?";
        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement(sql);
            ps.setInt(1, category_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getInt("item_id"));
                item.setCategryId(rs.getInt("category_id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemPrice(rs.getDouble("item_price"));
                items.add(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

}
