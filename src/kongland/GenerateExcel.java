/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kongland;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Madhukar
 */
public class GenerateExcel {

    static void generate(String schema, int monthName, int yearValue, String funderId) throws SQLException {
        
        Connection connection = DataRetrieve_Remote.getDBConnection();
        Statement statement = connection.createStatement();
//        String query = " Select 9 as 'Month',Date_format(a.action_date, '%Y-%m-%d') as 'Transaction date','G' as 'XXX',b.glcodevalue as 'debit account', "
//                + " CASE a.account_action_id WHEN 1 THEN 'LoanRepa' WHEN 9 THEN 'Adjustme' WHEN 10 THEN 'Disbursa' WHEN 19 THEN 'Disrburs' "
//                + " ELSE a.account_action_id END as 'description', "
//                + " Concat(a.global_account_num,'Loan') as 'description1',a.posted_amount as 'amount',0,0,a.glcodevalue as 'Credit account',Null,Null "
//                + " from ( "
//                + " Select atrxn.account_trxn_id,posted_amount,fintrxn.action_date,debit_credit_flag,glcode_value as 'glcodevalue',coa_name,fintrxn.fin_action_id,a.global_account_num,atrxn.account_action_id "
//                + " from financial_trxn  fintrxn INNER JOIN gl_code gl ON gl.glcode_id = fintrxn.glcode_id INNER JOIN coa ON coa.glcode_id = gl.glcode_id "
//                + " INNER JOIN account_trxn atrxn ON atrxn.account_trxn_id = fintrxn.account_trxn_id INNER JOIN ACCOUNT a ON a.account_id = atrxn.account_id "
//                + " inner join loan_account la on a.account_id=la.account_id where fintrxn.action_date BETWEEN '"+fromDateValueString+"' AND '"+toDateValueString+"' "
//                + " and la.fund_id="+funderId+" and debit_credit_flag=1 ) a, "
//                + " (Select atrxn.account_trxn_id,posted_amount,fintrxn.action_date,debit_credit_flag,glcode_value as 'glcodevalue',coa_name,fintrxn.fin_action_id,a.global_account_num,atrxn.account_action_id "
//                + " from financial_trxn  fintrxn INNER JOIN gl_code gl ON gl.glcode_id = fintrxn.glcode_id INNER JOIN coa ON coa.glcode_id = gl.glcode_id "
//                + " INNER JOIN account_trxn atrxn ON atrxn.account_trxn_id = fintrxn.account_trxn_id INNER JOIN account a ON a.account_id = atrxn.account_id "
//                + " inner join loan_account la on a.account_id=la.account_id where fintrxn.action_date BETWEEN '"+fromDateValueString+"' AND '"+toDateValueString+"' "
//                + " and la.fund_id="+funderId+" and debit_credit_flag=0 order by 1) b where a.account_trxn_id = b.account_trxn_id and a.fin_action_id=b.fin_action_id ";
//            
        
        String insertQuery = "insert into exporttofa (month,year,created_date) VALUES ('"+monthName+"','"+yearValue+"',now())";
            if (connection != null) {
                try {
                    int index = 1;
                    int rowcount = 0;
                    ResultSet rs = statement.executeQuery(schema);
                    ResultSet rs3 = rs;
                    rs3.last();
                    rowcount = rs3.getRow();
                    rs3.beforeFirst();
                    if(rowcount == 0){
                        JOptionPane.showMessageDialog(null, "No Records found");
                    } else {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int numberOfColumns = metaData.getColumnCount();
                    
                    HSSFWorkbook wb = new HSSFWorkbook();
                    //wb.writeProtectWorkbook("madhukar", "madhukar");
                    HSSFSheet sheet = wb.createSheet("Excel Sheet");
                    HSSFRow rowhead = sheet.createRow((short) 0);
                    for (int i = 1; i <= numberOfColumns; i++) {
                        rowhead.createCell((short) i - 1).setCellValue(metaData.getColumnName(i));
                        sheet.autoSizeColumn(i - 1);
                    }
                    while (rs.next()) {                    
                       HSSFRow row = sheet.createRow((short) index);
                       sheet.autoSizeColumn(index);
                       int month = rs.getInt(1);
                       Date transactionDate = rs.getDate(2);
                       String g = rs.getString(3);
                       int debitAccount = rs.getInt(4);
                       String description = rs.getString(5); 
                       String description1 = rs.getString(6);
                       double amount = rs.getDouble(7);
                       int o = rs.getInt(8);
                       int o1 = rs.getInt(9);
                       int creditAccount = rs.getInt(10);
                       
                       row.createCell((short) 0).setCellValue(month);
                       row.createCell((short) 1).setCellValue(transactionDate);
                       row.createCell((short) 2).setCellValue(g);
                       row.createCell((short) 3).setCellValue(debitAccount);
                       row.createCell((short) 4).setCellValue(description);
                       row.createCell((short) 5).setCellValue(description1);
                       row.createCell((short) 6).setCellValue(amount);
                       row.createCell((short) 7).setCellValue(0);
                       row.createCell((short) 8).setCellValue(o1);
                       row.createCell((short) 9).setCellValue(creditAccount);
                       row.createCell((short) 10).setCellValue("");
                       row.createCell((short) 11).setCellValue("");
                       index++;
                    }
                    String path = System.getProperty("user.dir");
                    String fileLocation = path + File.separator+ monthName;

		/** Recursively create the directory if it does not exist **/
		if (!new File(fileLocation).isDirectory()) {
			new File(fileLocation).mkdirs();
		}
                    
                    String excelName = fileLocation+"/Kongland_"+funderId+"_"+monthName+"_"+yearValue+".xls";

                    FileOutputStream fileOut = new FileOutputStream(excelName);
                    //sheet.protectSheet("madhukar");
                    wb.write(fileOut);
                    JOptionPane.showMessageDialog(null, "File is generated");
                    rs.close();
                 boolean inserted = statement.execute(insertQuery);  
//                    if (inserted) {
//                        JOptionPane.showMessageDialog(null, "An error occured while updating history");
//                    }
                    }    
                    rs3.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    statement.close();
                    connection.close();
                } 
            }
    }
    
}
