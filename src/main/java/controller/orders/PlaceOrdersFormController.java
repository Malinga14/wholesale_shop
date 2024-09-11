package controller.orders;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrdersFormController implements Initializable {

    @FXML
    private JFXComboBox<?> cmbCustomerID;

    @FXML
    private JFXComboBox<?> cmbItemCode;

    @FXML
    private TableView<?> colCustomerID;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQTY;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private Label txtDate;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtQTY;

    @FXML
    private JFXTextField txtStock;

    @FXML
    private Label txtTime;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextField txtcustomerName;

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

    private void loadDateAndTime(){
        Date date=new Date();
        SimpleDateFormat f=new SimpleDateFormat("dd-MM-yyyy");
        String dateNow=f.format(date);

        txtDate.setText(dateNow);


        Timeline timeline=new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime now=LocalTime.now();
            txtTime.setText(now.getHour() +" : "+now.getMinute()+" : "+now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
    }
}
