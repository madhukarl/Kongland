/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kongland;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author Madhukar
 */
public class DataRetrieve_Remote {
    
    static Connection conn;
    private static String dbName;
    private static String driver;
    private static String url;
    private static String userName;
    private static String password;
    
    public static Connection getDBConnection() {
        FileInputStream fis = null;
        
        try {
            File file=new File("./database.properties");
            if(file.exists()){
//                System.out.println(".Properties File found");
            Properties prop=new Properties();
            fis = new FileInputStream(file);
            prop.load(fis);
            
            url=prop.getProperty("Export.Url");
            dbName=prop.getProperty("Export.DBName");
            driver=prop.getProperty("Export.Driver");
            userName=prop.getProperty("Export.Username");
            password=prop.getProperty("Export.Password");
            
            System.out.println("From properties file.."+url+"\t"+dbName+"\t"+driver+"\t"+userName+"\t"+password);
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            else{
                System.out.println(".Properties File not found");
            }
        } catch (Exception ex) {
            ex.getMessage();
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
        return conn;
    }
    
}
