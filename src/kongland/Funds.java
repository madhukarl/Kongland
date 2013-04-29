/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kongland;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author Madhukar
 */
public class Funds {

    public static Vector<String> getAvailableLoanFunds() {
        Vector<String> availableFunds = new Vector<String>();
        String sql = "Select distinct fund_id as fundId from loan_account where fund_id is not null";
        Connection connection = DataRetrieve_Remote.getDBConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String fundid = resultSet.getString("fundId");
                availableFunds.add(fundid);
            }
            //Collections.sort(availableFunds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return availableFunds;
    }

    public static Vector<String> getAvailableSavingFunds() {
        Vector<String> availableFunds = new Vector<String>();
        String sql = "Select distinct response as fundId from question_group_instance a, question_group_response b where question_group_id=20 and a.id = b.question_group_instance_id";
        Connection connection = DataRetrieve_Remote.getDBConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String fundid = resultSet.getString("fundId");
                availableFunds.add(fundid);
            }
            //Collections.sort(availableFunds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return availableFunds;
    }
    
 public static Vector<String> getAvailableOtherFunds() {
        Vector<String> availableFunds = new Vector<String>();
        String sql = "Select distinct response as fundId from question_group_instance a, question_group_response b where question_group_id=19 and a.id = b.question_group_instance_id";
        Connection connection = DataRetrieve_Remote.getDBConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String fundid = resultSet.getString("fundId");
                availableFunds.add(fundid);
            }
            //Collections.sort(availableFunds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return availableFunds;
    }    
}
