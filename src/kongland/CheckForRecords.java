/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kongland;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Madhukar
 */
public class CheckForRecords {
    private static boolean isInserted = false;
    public static boolean checkAlreadyPopulatedRecords(int month, int year){
        
        String sql = "Select e.`month` as monthNumber, e.`year` as yearNumber from exporttofa e where e.`month`="+month+" and e.`year`="+year;
        Connection connection = DataRetrieve_Remote.getDBConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                int monthNumber = resultSet.getInt("monthNumber");
                int yearNumber = resultSet.getInt("yearNumber");
                
                if (month == monthNumber && year == yearNumber) {
                  isInserted = true;
                  break;
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return  isInserted;
    }
    
}
