package controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashFromController {
    @FXML
    void btnCustomerFormOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/view_customer_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void btnItemFormOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/item_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnOrdersFormOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try{
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/orders_form.fxml"))));
            stage.show();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void btnOrderListFormOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try{
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/order_detail_form.fxml"))));
            stage.show();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
