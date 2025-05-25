/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package hospitalmanagementsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author memir
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private CheckBox login_checkBox;
    @FXML
    private AnchorPane login_form;
    @FXML
    private Button login_loginBtn;
    @FXML
    private PasswordField login_password;
    @FXML
    private Hyperlink login_registerHere;
    @FXML
    private TextField login_showPassword;
    @FXML
    private ComboBox<?> login_user;
    @FXML
    private TextField login_username;
    @FXML
    private AnchorPane main_form;
    @FXML
    private CheckBox register_checkBox;
    @FXML
    private TextField register_email;
    @FXML
    private AnchorPane register_form;
    @FXML
    private Hyperlink register_loginHere;
    @FXML
    private PasswordField register_password;
    @FXML
    private TextField register_showPassword;
    @FXML
    private Button register_singupBtn;
    @FXML
    private TextField register_username;
    //DataBase tools
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private AlertMessage alert = new AlertMessage();

    public void loginAccount() {

        if (login_username.getText().isEmpty()
                || login_password.getText().isEmpty()) {
            alert.errorMessage("eksik Kullanıcı adı veya şifre");
        } else {

            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";

            connect = Database.connectDB();

            try {

                if (!login_showPassword.isVisible()) {
                    if (!login_showPassword.getText().equals(login_password.getText())) {
                        login_showPassword.setText(login_password.getText());
                    }
                } else {
                    if (!login_showPassword.getText().equals(login_password.getText())) {
                        login_password.setText(login_showPassword.getText());
                    }
                }

                prepare = connect.prepareStatement(sql);
                prepare.setString(1, login_username.getText());
                prepare.setString(2, login_password.getText());
                result = prepare.executeQuery();

                if (result.next()) {
                    Data.admin_username = login_username.getText();
                    
                    alert.successMessage("Giriş Başarılı!");                                       
                    login_loginBtn.getScene().getWindow().hide();
                    
                    Parent root = FXMLLoader.load(getClass().getResource("AdminMainForm.fxml"));
                    Stage stage = new Stage();
                    
                    stage.setTitle("Hastane Yönetim Sistemi | Admin Portal");
                    stage.setScene(new Scene(root));
                    stage.show();
                    
                    login_loginBtn.getScene().getWindow().hide();
                } else {                 
                    alert.errorMessage("Hatalı Kullanıcı adı veya şifre");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void loginShowPassword() {

        if (login_checkBox.isSelected()) {
            login_showPassword.setText(login_password.getText());
            login_showPassword.setVisible(true);
            login_password.setVisible(false);
        } else {
            login_password.setText(login_showPassword.getText());
            login_showPassword.setVisible(false);
            login_password.setVisible(true);
        }

    }

    public void registerAccount() {
        if (register_email.getText().isEmpty() || register_username.getText().isEmpty() || register_password.getText().isEmpty()) {
            alert.errorMessage("Lütfen tüm boş alanları doldurun");
        } else {
            String checkUsername = "SELECT * FROM admin WHERE username = '" + register_username.getText() + "'";

            connect = Database.connectDB();
            try {

                if (register_checkBox.isVisible()) {
                    if (!register_showPassword.getText().equals(register_password.getText())) {
                        register_showPassword.setText(register_password.getText());

                    } else {
                        if (!register_showPassword.getText().equals(register_password.getText())) {
                            register_password.setText(register_showPassword.getText());
                        }

                    }
                }
                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert.errorMessage(register_username.getText() + " Zaten Mevcut!");
                } else if (register_password.getText().length() < 8) {
                    alert.errorMessage("Geçersiz şifre, şifreniz 8 karekterden uzun olmalı!");

                } else {
                    //VERITABANINA VERI EKLEME
                    String insertData = "INSERT INTO admin (email, username, password, date) VALUES(?,?,?,?)";
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, register_email.getText());
                    prepare.setString(2, register_username.getText());
                    prepare.setString(3, register_password.getText());
                    prepare.setString(4, String.valueOf(sqlDate));

                    prepare.executeUpdate();
                    alert.successMessage("Kayıt olma Başarılı");
                    registerCler();

                    login_form.setVisible(true);
                    register_form.setVisible(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void registerCler() {
        register_email.clear();
        register_username.clear();
        register_password.clear();
        register_showPassword.clear();

    }

    public void registerShowPassword() {

        if (register_checkBox.isSelected()) {
            register_showPassword.setText(register_password.getText());
            register_showPassword.setVisible(true);
            register_password.setVisible(false);
        } else {
            register_showPassword.setText(register_password.getText());
            register_showPassword.setVisible(false);
            register_password.setVisible(true);

        }

    }

    public void userList() {

        List<String> listU = new ArrayList<>();

        for (String data : Users.user) {
            listU.add(data);
        }
        ObservableList listData = FXCollections.observableList(listU);
        login_user.setItems(listData);
    }

    public void SwitchPage() {
        if (login_user.getSelectionModel().getSelectedItem() == "Admin Portal") {

            try {

                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();

                stage.setTitle("Hospital Management System");

                stage.setMinWidth(370);
                stage.setMinHeight(580);

                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (login_user.getSelectionModel().getSelectedItem() == "Doktor Portal") {

            try {

                Parent root = FXMLLoader.load(getClass().getResource("DoctorPage.fxml"));
                Stage stage = new Stage();

                stage.setTitle("Hospital Management System");

                stage.setMinWidth(370);
                stage.setMinHeight(580);

                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (login_user.getSelectionModel().getSelectedItem() == "Hasta Portal") {
            try {

                Parent root = FXMLLoader.load(getClass().getResource("PatientPage.fxml"));
                Stage stage = new Stage();

                stage.setTitle("Hospital Management System");

                stage.setMinWidth(370);
                stage.setMinHeight(580);

                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        login_user.getScene().getWindow().hide();

    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == login_registerHere) {
            login_form.setVisible(false);
            register_form.setVisible(true);
        } else if (event.getSource() == register_loginHere) {
            register_form.setVisible(false);
            login_form.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userList();
    }

}
