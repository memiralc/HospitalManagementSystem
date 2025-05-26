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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

/**
 *
 * @author memir
 */
public class DoctorMainFormController implements Initializable {

    @FXML
    private TextArea appointment_address;

    @FXML
    private TextField appointment_appointmentID;

    @FXML
    private TextField appointment_description;

    @FXML
    private TextField appointment_diagnosis;

    @FXML
    private ComboBox<String> appointment_gender;

    @FXML
    private TextField appointment_mobileNumber;

    @FXML
    private TextField appointment_name;

    @FXML
    private ComboBox<String> appointment_status;

    @FXML
    private TextField appointment_treatment;

    @FXML
    private Button appointments_btn;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_action;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_appointmentID;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_contactNumber;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_date;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_dateDelete;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_dateModify;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_description;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_gender;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_name;

    @FXML
    private TableColumn<AppointmentData, String> appointments_col_status;

    @FXML
    private AnchorPane appointments_form;

    @FXML
    private TableView<AppointmentData> appointments_tableView;

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
    private AnchorPane main_form;

    @FXML
    private Label nav_adminID;

    @FXML
    private Label nav_username;

    @FXML
    private Label patients_PA_dateCreated;

    @FXML
    private Label patients_PA_password;

    @FXML
    private Label patients_PA_patientID;

    @FXML
    private Button patients_PI_addBtn;

    @FXML
    private Label patients_PI_address;

    @FXML
    private Label patients_PI_gender;

    @FXML
    private Label patients_PI_mobileNumber;

    @FXML
    private Label patients_PI_patientName;

    @FXML
    private Button patients_PI_recordBtn;

    @FXML
    private TextArea patients_address;

    @FXML
    private Button patients_btn;

    @FXML
    private Button patients_confirmBtn;

    @FXML
    private AnchorPane patients_form;

    @FXML
    private ComboBox<?> patients_gender;

    @FXML
    private TextField patients_mobileNumber;

    @FXML
    private TextField patients_password;

    @FXML
    private TextField patients_patientID;

    @FXML
    private TextField patients_patientName;

    @FXML
    private Button profile_btn;

    @FXML
    private Circle top_profile;

    @FXML
    private Label top_username;
    
    @FXML
    private DatePicker appointment_schedule;
    
    @FXML
    private Button appointment_insertBtn;
    
    @FXML
    private Button appointment_updateBtn;
    
    @FXML
    private Button appointment_clearBtn;
    
    @FXML
    private Button appointment_deleteBtn;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private final AlertMessage alert = new AlertMessage();
    
    public void appointmentInsertBtn() {
        if (appointment_appointmentID.getText().isEmpty() || appointment_name.getText().isEmpty() || appointment_gender.getSelectionModel().getSelectedItem() == null || appointment_mobileNumber.getText().isEmpty() || appointment_description.getText().isEmpty() || appointment_address.getText().isEmpty() || appointment_status.getSelectionModel().getSelectedItem() == null || appointment_schedule.getValue() == null) {
            alert.errorMessage("Lütfen boş alanları doldurun!");
        } else {
            String checkAppointmentID = "SELECT * FROM appointment WHERE appointment_id = " + appointment_appointmentID.getText();
            connect = Database.connectDB();
            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkAppointmentID);
                if (result.next()) {
                    alert.errorMessage(appointment_appointmentID.getText() + " Zaten alınmıştı");
                } else {
                    String getSpecialized = "";
                    String getDoctorData = "SELECT * FROM doctor WHERE doctor_id = '"+ Data.doctor_id + "'";
                    statement = connect.createStatement();
                    result = statement.executeQuery(getDoctorData);
                    if (result.next()) {
                        getSpecialized = result.getString("specialized");
                    }
                    String insertData = "INSERT INTO appointment (appointment_id, name, gender" + ", description, diagnosis, treatment, mobile_number" + ", address, date, status, doctor, specialized, schedule) " + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    prepare = connect.prepareStatement(insertData);

                    prepare.setString(1, appointment_appointmentID.getText());
                    prepare.setString(2, appointment_name.getText());
                    prepare.setString(3, (String) appointment_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(4, appointment_description.getText());
                    prepare.setString(5, appointment_diagnosis.getText());
                    prepare.setString(6, appointment_treatment.getText());
                    prepare.setString(7, appointment_mobileNumber.getText());
                    prepare.setString(8, appointment_address.getText());

                    java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());

                    prepare.setString(9, "" + sqlDate);
                    prepare.setString(10, (String) appointment_status.getSelectionModel().getSelectedItem());
                    prepare.setString(11, Data.doctor_id);
                    prepare.setString(12, getSpecialized);
                    prepare.setString(13, "" + appointment_schedule.getValue());

                    prepare.executeUpdate();

                    appointmentShowData();
                    appointmentAppointmentID();
                    appointmentClearBtn();
                    alert.successMessage("Başarıyla Eklendi!");

                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }

    }
    
    public void appointmentUpdateBtn() {

        if (appointment_appointmentID.getText().isEmpty() || appointment_name.getText().isEmpty() || appointment_gender.getSelectionModel().getSelectedItem() == null || appointment_mobileNumber.getText().isEmpty() || appointment_description.getText().isEmpty() || appointment_address.getText().isEmpty() || appointment_status.getSelectionModel().getSelectedItem() == null || appointment_schedule.getValue() == null) {
            alert.errorMessage("Lütfen boş alanları doldurun!");
        } else {
            java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
            String updateData = "UPDATE appointment SET name = '" + appointment_name.getText() + "', gender = '" + appointment_gender.getSelectionModel().getSelectedItem() + "', mobile_number = '" + appointment_mobileNumber.getText() + "', description = '" + appointment_description.getText() + "', address = '" + appointment_address.getText() + "', status = '" + appointment_status.getSelectionModel().getSelectedItem() + "', schedule = '" + appointment_schedule.getValue() + "', date_modify = '" + sqlDate + "' WHERE appointment_id = '" + appointment_appointmentID.getText() + "'";
            connect = Database.connectDB();
            try {
                if (alert.confirmationMessage("Randevu Kimliğinizi GÜNCELLEMEK istediğinizden emin misiniz: " + appointment_appointmentID.getText() + "?")) {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();
                    appointmentShowData();
                    appointmentAppointmentID();
                    appointmentClearBtn();
                    alert.successMessage("Başarıyla Güncellendi!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void appointmentDeleteBtn() {
        if (appointment_appointmentID.getText().isEmpty()) {
            alert.errorMessage("Lütfen önce öğeyi seçin!");
        } else {
            String updateData = "UPDATE appointment SET date_delete = ? WHERE appointment_id = '" + appointment_appointmentID.getText() + "'";
            connect = Database.connectDB();
            try {
                java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
                if (alert.confirmationMessage("Randevu Kimliğinizi SİLMEK istediğinizden emin misiniz:" + appointment_appointmentID.getText() + "?")) {
                    prepare = connect.prepareStatement(updateData);
                    prepare.setString(1, String.valueOf(sqlDate));
                    prepare.executeUpdate();
                    appointmentShowData();
                    appointmentAppointmentID();
                    appointmentClearBtn();
                    alert.successMessage("Başarıyla Güncellendi!");
                } else {
                    alert.errorMessage("İptal Edildi!.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void appointmentClearBtn() {
        appointment_name.clear();
        appointment_gender.getSelectionModel().clearSelection();
        appointment_mobileNumber.clear();
        appointment_description.clear();
        appointment_treatment.clear();
        appointment_diagnosis.clear();
        appointment_address.clear();
        appointment_status.getSelectionModel().clearSelection();
        appointment_schedule.setValue(null);
    }

    
    private Integer appointmentID;
    public void appointmentGetAppointmentID() {
        String sql = "SELECT MAX(appointment_id) FROM appointment";
        connect = Database.connectDB();

        int tempAppID = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                tempAppID = result.getInt("MAX(appointment_id)");
            }
            if (tempAppID == 0) {
                tempAppID += 1;
            } else {
                tempAppID += 1;
            }
            appointmentID = tempAppID;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void appointmentAppointmentID() {
        appointmentGetAppointmentID();

        appointment_appointmentID.setText("" + appointmentID);
        appointment_appointmentID.setDisable(true);

    }
    
    public void appointmentGenderList(){
        List<String> listG = new ArrayList<>();
        for(String data : Data.gender){
            listG.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listG);
        appointment_gender.setItems(listData);
    }
    
    public void appointmentStatusList(){
        List<String> lists = new ArrayList<>();
        for(String data : Data.status){
            lists.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(lists);
        appointment_status.setItems(listData);
    }

    public ObservableList<AppointmentData> appointmentGetData() {
        ObservableList<AppointmentData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointment WHERE date_delete IS NULL";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            AppointmentData appData;
            while (result.next()) {
                appData = new AppointmentData(result.getInt("appointment_id"), result.getString("name"), result.getString("gender"), result.getLong("mobile_number"), result.getString("description"), result.getString("diagnosis"), result.getString("treatment"), result.getString("address"),result.getDate("date"), result.getDate("date_modify"), result.getDate("date_delete"), result.getString("status"), result.getDate("schedule"));
                listData.add(appData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    public ObservableList<AppointmentData> appointmentListData;

    public void appointmentShowData() {
        appointmentListData = appointmentGetData();

        appointments_col_appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointments_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        appointments_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        appointments_col_contactNumber.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        appointments_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointments_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        appointments_col_dateModify.setCellValueFactory(new PropertyValueFactory<>("dateModify"));
        appointments_col_dateDelete.setCellValueFactory(new PropertyValueFactory<>("dateDelete"));
        appointments_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        appointments_tableView.setItems(appointmentListData);

    }
    
    public void appointmentSelect() {

        AppointmentData appData = appointments_tableView.getSelectionModel().getSelectedItem();
        int num = appointments_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        appointment_appointmentID.setText("" + appData.getAppointmentID());
        appointment_name.setText(appData.getName());
        appointment_gender.getSelectionModel().select(appData.getGender());
        appointment_mobileNumber.setText("" + appData.getMobileNumber());
        appointment_description.setText(appData.getDescription());
        appointment_diagnosis.setText(appData.getDiagnosis());
        appointment_treatment.setText(appData.getTreatment());
        appointment_address.setText(appData.getAddress());
        appointment_status.getSelectionModel().select(appData.getStatus());

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

    public void displayAdminIDNumberName() {
        String name = Data.doctor_name;
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        nav_username.setText(name);
        nav_adminID.setText(Data.doctor_id);
        top_username.setText(name);
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            patients_form.setVisible(false);
            appointments_form.setVisible(false);
        } else if (event.getSource() == patients_btn) {
            dashboard_form.setVisible(false);
            patients_form.setVisible(true);
            appointments_form.setVisible(false);
        } else if (event.getSource() == appointments_btn) {
            dashboard_form.setVisible(false);
            patients_form.setVisible(false);
            appointments_form.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayAdminIDNumberName();
        runTime();
        
        appointmentShowData();
        appointmentGenderList();
        appointmentStatusList();
        appointmentAppointmentID();
        
    }

}
