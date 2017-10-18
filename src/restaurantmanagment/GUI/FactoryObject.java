/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantmanagment.GUI;

/**
 *
 * @author endri
 */
public class FactoryObject {
    public static Object newInstance(String className)
    {
        Class cls = null;
        try
        {
            cls=Class.forName(className);
            
        }
        catch (ClassNotFoundException cnfe)
                    {
                        System.out.println("Cannot find class"+className);
                    
                    }
        Object newObject = null;
        if(cls!=null)
        {
            try{newObject= cls.newInstance();}
            catch (InstantiationException ie)
            {
                System.out.println("cannot intatiate class"+className);
            }
            catch(IllegalAccessException iae)
            {
                System.out.println("cannot access class"+className);
            }
            
        }
        return newObject;
    }
    
}

