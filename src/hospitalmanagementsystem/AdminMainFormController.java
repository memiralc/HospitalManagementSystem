/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospitalmanagementsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

/**
 *
 * @author memir
 */
public class AdminMainFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> appointments_action;
    @FXML
    private TableColumn<?, ?> appointments_appointmentID;
    @FXML
    private Button appointments_btn;
    @FXML
    private TableColumn<?, ?> appointments_contactNumber;
    @FXML
    private TableColumn<?, ?> appointments_date;
    @FXML
    private TableColumn<?, ?> appointments_dateDelete;
    @FXML
    private TableColumn<?, ?> appointments_dateModify;
    @FXML
    private TableColumn<?, ?> appointments_description;
    @FXML
    private AnchorPane appointments_form;
    @FXML
    private TableColumn<?, ?> appointments_gender;
    @FXML
    private TableColumn<?, ?> appointments_name;
    @FXML
    private TableColumn<?, ?> appointments_status;
    @FXML
    private TableView<?> appointments_tableView;
    @FXML
    private Label current_form;
    @FXML
    private Label dashboard_AD;
    @FXML
    private Label dashboard_AP;
    @FXML
    private Label dashboard_TA;
    @FXML
    private Label dashboard_TP;
    @FXML
    private Button dashboard_btn;
    @FXML
    private BarChart<?, ?> dashboard_chart_DD;
    @FXML
    private AreaChart<?, ?> dashboard_chart_PD;
    @FXML
    private TableColumn<?, ?> dashboard_col_doctorID;
    @FXML
    private TableColumn<?, ?> dashboard_col_name;
    @FXML
    private TableColumn<?, ?> dashboard_col_specialized;
    @FXML
    private TableColumn<?, ?> dashboard_col_status;
    @FXML
    private AnchorPane dashboard_form;
    @FXML
    private TableView<?> dashboard_tableView;
    @FXML
    private Label date_time;
    @FXML
    private Button doctors_btn;
    @FXML
    private TableColumn<?, ?> doctors_col_action;
    @FXML
    private TableColumn<?, ?> doctors_col_address;
    @FXML
    private TableColumn<?, ?> doctors_col_contactNumber;
    @FXML
    private TableColumn<?, ?> doctors_col_doctorID;
    @FXML
    private TableColumn<?, ?> doctors_col_email;
    @FXML
    private TableColumn<?, ?> doctors_col_gender;
    @FXML
    private TableColumn<?, ?> doctors_col_name;
    @FXML
    private TableColumn<?, ?> doctors_col_specialization;
    @FXML
    private TableColumn<?, ?> doctors_col_status;
    @FXML
    private AnchorPane doctors_form;
    @FXML
    private TableView<?> doctors_tableView;
    @FXML
    private AnchorPane main_form;
    @FXML
    private Label nav_adminID;
    @FXML
    private Label nav_username;
    @FXML
    private TableView<?> patient_tableView;
    @FXML
    private Button patients_btn;
    @FXML
    private TableColumn<?, ?> patients_col_active;
    @FXML
    private TableColumn<?, ?> patients_col_contactNumber;
    @FXML
    private TableColumn<?, ?> patients_col_date;
    @FXML
    private TableColumn<?, ?> patients_col_dateDelete;
    @FXML
    private TableColumn<?, ?> patients_col_dateModify;
    @FXML
    private TableColumn<?, ?> patients_col_description;
    @FXML
    private TableColumn<?, ?> patients_col_gender;
    @FXML
    private TableColumn<?, ?> patients_col_name;
    @FXML
    private TableColumn<?, ?> patients_col_patientID;
    @FXML
    private TableColumn<?, ?> patients_col_status;
    @FXML
    private AnchorPane patients_form;
    @FXML
    private Button payment_btn;
    @FXML
    private Button profile_btn;
    @FXML
    private Circle top_profile;
    @FXML
    private Label top_username;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            doctors_form.setVisible(false);
            patients_form.setVisible(false);
            appointments_form.setVisible(false);
        } else if (event.getSource() == doctors_btn) {
            dashboard_form.setVisible(false);
            doctors_form.setVisible(true);
            patients_form.setVisible(false);
            appointments_form.setVisible(false);
        } else if (event.getSource() == patients_btn) {
            dashboard_form.setVisible(false);
            doctors_form.setVisible(false);
            patients_form.setVisible(true);
            appointments_form.setVisible(false);
        } else if (event.getSource() == appointments_btn) {
            dashboard_form.setVisible(false);
            doctors_form.setVisible(false);
            patients_form.setVisible(false);
            appointments_form.setVisible(true);
        }
    }
    public void displayAdminIDUsername(){
        String sql = "SELECT * FROM admin WHERE username = '"+Data.admin_username+"'";
        connect = Database.connectDB();
        
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if(result.next()){
                nav_adminID.setText(result.getString("admin_id"));
                String tempUsername = result.getString("username");
                tempUsername = tempUsername.substring(0,1).toUpperCase() + tempUsername.substring(1);
                nav_username.setText(tempUsername); 
                top_username.setText(tempUsername); 
            }
            
        } catch (Exception e) {e.printStackTrace();}
        
    }
    public void runTime() {
        new Thread() {
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("MM:DD:YYYY hh:mm:ss a");
                while (true) {
                    try {

                        Thread.sleep(1000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(() -> {
                        date_time.setText(format.format(new Date()));
                    });

                }
            }
        }.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        runTime();
        displayAdminIDUsername();
    }
}
