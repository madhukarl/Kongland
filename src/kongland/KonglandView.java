/*
 * KonglandView.java
 */

package kongland;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.joda.time.LocalDate;

/**
 * The application's main frame.
 */
public class KonglandView extends FrameView {

    public KonglandView(SingleFrameApplication app) {
        super(app);

        initComponents();

        Vector<String> availableFunds = new Vector<String>();
        availableFunds.add("null");
        fundId.setModel(new javax.swing.DefaultComboBoxModel(availableFunds));
            
            
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = KonglandApp.getApplication().getMainFrame();
            aboutBox = new KonglandAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        KonglandApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        fundId = new javax.swing.JComboBox();
        funder = new javax.swing.JLabel();
        generate = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        monthName = new com.toedter.calendar.JMonthChooser();
        year = new com.toedter.calendar.JYearChooser();
        selectTrxnType = new javax.swing.JComboBox();
        trxnTypeLable = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        fundId.setName("fundId"); // NOI18N
        fundId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fundIdActionPerformed(evt);
            }
        });

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(kongland.KonglandApp.class).getContext().getResourceMap(KonglandView.class);
        funder.setText(resourceMap.getString("funder.text")); // NOI18N
        funder.setName("funder"); // NOI18N

        generate.setText(resourceMap.getString("generate.text")); // NOI18N
        generate.setName("generate"); // NOI18N
        generate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateActionPerformed(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        monthName.setName("monthName"); // NOI18N

        year.setName("year"); // NOI18N

        selectTrxnType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Loans", "Savings", "Others" }));
        selectTrxnType.setName("selectTrxnType"); // NOI18N
        selectTrxnType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectTrxnTypeActionPerformed(evt);
            }
        });

        trxnTypeLable.setText(resourceMap.getString("trxnTypeLable.text")); // NOI18N
        trxnTypeLable.setName("trxnTypeLable"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(funder)
                    .addComponent(trxnTypeLable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectTrxnType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fundId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generate)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(monthName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(127, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(monthName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectTrxnType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trxnTypeLable))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fundId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(funder))
                .addGap(18, 18, 18)
                .addComponent(generate)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(kongland.KonglandApp.class).getContext().getActionMap(KonglandView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void generateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateActionPerformed
        try {
            int month = monthName.getMonth() + 1;
            int yearValue = year.getYear();
            String dateformat = yearValue + "-" + month + "-1";
            LocalDate fromDate = new LocalDate(dateformat);
            LocalDate lastDate = fromDate.dayOfMonth().withMaximumValue();

            Date fromDateValue = fromDate.toDate();
            Date toDateValue = lastDate.toDate();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fromDateValueString = dateFormat.format(fromDateValue);
            String toDateValueString = dateFormat.format(toDateValue);

            String selectedFundId = (String) fundId.getSelectedItem();
            String transactionType = (String) selectTrxnType.getSelectedItem();
            String schema = null;
            
            if (transactionType.equalsIgnoreCase("Loans") && !selectedFundId.equalsIgnoreCase("null")) {
                schema = SqlSchemas.loansWithFundIdSchema(fromDateValueString, toDateValueString,selectedFundId);
            } else if (transactionType.equalsIgnoreCase("Loans") && selectedFundId.equalsIgnoreCase("null")) {
                schema = SqlSchemas.loansWithoutFundIdSchema(fromDateValueString, toDateValueString);
            } else if (transactionType.equalsIgnoreCase("Savings")) {
                schema = SqlSchemas.savingsSchema(fromDateValueString, toDateValueString,selectedFundId);
            } else if (transactionType.equalsIgnoreCase("Others")) {
                schema = SqlSchemas.othersSchema(fromDateValueString, toDateValueString, selectedFundId);
            }



//            if (selected.equalsIgnoreCase("FunderA")) {
//                funderId = 1;
//            } else {
//                funderId = 6;
//            }
//            boolean isInserted = CheckForRecords.checkAlreadyPopulatedRecords(month, yearValue); 
//            if (isInserted) {
//                JOptionPane.showMessageDialog(null, "The sheet already generated for this month");
//            } else {

            GenerateExcel.generate(schema, month, yearValue, selectedFundId);

//            }
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }   
    }//GEN-LAST:event_generateActionPerformed

    private void selectTrxnTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectTrxnTypeActionPerformed
        
        selectTrxnType = (JComboBox)evt.getSource();
        String selected = (String) selectTrxnType.getSelectedItem();
        transactionTypeSelected = selected;
        if (selected.equalsIgnoreCase("Loans")) {
            Vector<String> availableFunds = Funds.getAvailableLoanFunds();
            availableFunds.add("null");
            fundId.setModel(new javax.swing.DefaultComboBoxModel(availableFunds));
        } else if (selected.equalsIgnoreCase("Savings")){
            Vector<String> availableFunds = Funds.getAvailableSavingFunds();
            fundId.setModel(new javax.swing.DefaultComboBoxModel(availableFunds));
        } else {
            Vector<String> availableFunds = Funds.getAvailableOtherFunds();
            fundId.setModel(new javax.swing.DefaultComboBoxModel(availableFunds));
        }
        
        String command = evt.getActionCommand();
        System.out.println("Action Command = " + command);
        
    }//GEN-LAST:event_selectTrxnTypeActionPerformed

    private void fundIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fundIdActionPerformed
//        fundId = (JComboBox) evt.getSource(); 
//        String selectedFundId = (String)fundId.getSelectedItem();
//        if (transactionTypeSelected.equalsIgnoreCase("Loans") && !selectedFundId.equalsIgnoreCase("null")) {
//            JOptionPane.showMessageDialog(null, "fundSelected for loan");
//        } else if(transactionTypeSelected.equalsIgnoreCase("Loans") && selectedFundId.equalsIgnoreCase("null")){
//            JOptionPane.showMessageDialog(null, "No fund selected");
//        } else if(transactionTypeSelected.equalsIgnoreCase("Savings") && selectedFundId.equalsIgnoreCase("null")) {
//            JOptionPane.showMessageDialog(null, "No fund selected for savings");
//        } else if(transactionTypeSelected.equalsIgnoreCase("Others") && selectedFundId.equalsIgnoreCase("null")) {
//            JOptionPane.showMessageDialog(null, "No fund selected in others");
//        }
//        
//        
    }//GEN-LAST:event_fundIdActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox fundId;
    private javax.swing.JLabel funder;
    private javax.swing.JButton generate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private com.toedter.calendar.JMonthChooser monthName;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JComboBox selectTrxnType;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JLabel trxnTypeLable;
    private com.toedter.calendar.JYearChooser year;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private String transactionTypeSelected=null;

    private JDialog aboutBox;
}
