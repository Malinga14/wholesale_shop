package controller.item;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import util.CrudUtil;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable{
    @FXML
    private TableView<Item> tblItems;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colPackSize;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUniPrice;


    @FXML
    void btnAddOnAction(ActionEvent event) {
        Item item = new Item(
                txtId.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUniPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
        );
        try {
            if((CrudUtil.execute("INSERT INTO Item VALUES(?,?,?,?,?)",
                    item.getId(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()

            ))){
                new Alert(Alert.AlertType.INFORMATION,"Item Added!").show();
                loadTable();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String SQL = "DELETE FROM Item WHERE ItemCode = '"+txtId.getText()+"'";

        try {
            ResultSet resultSet=CrudUtil.execute(SQL);
            List<Item> list = new ArrayList<>();
            while (resultSet.next()){
                txtId.setText(resultSet.getString("id"));
                txtDescription.setText(resultSet.getString("description"));
                txtPackSize.setText(resultSet.getString("packSize"));
                txtUniPrice.setText(resultSet.getString("unitPrice"));
                txtQtyOnHand.setText(resultSet.getString("qtyOnHand"));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        try{
            loadTable();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

        try{
            if(CrudUtil.execute("SELECT * FROM Item WHERE ItemCode = '"+txtId.getText()+"'")){
                loadTable();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try{
            if(CrudUtil.execute("UPDATE Item SET ItemCode = '"+
                    txtId.getText()+"',Description = '"+
                    txtDescription.getText()+"',PackSize = '"+
                    txtPackSize.getText()+"', UnitPrice='"+
                    Double.parseDouble(txtUniPrice.getText())+"', QtyOnHand ='"+
                    Integer.parseInt(txtQtyOnHand.getText())+"' Where ItemCode ='"+
                    txtId.getText()+"'")){
                new Alert(Alert.AlertType.INFORMATION,"Item Updated!").show();
                loadTable();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTextToValues(Item newValue) {
        txtId.setText(newValue.getId());
        txtDescription.setText(newValue.getDescription());
        txtPackSize.setText(newValue.getPackSize());
        txtUniPrice.setText(String.valueOf(newValue.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
    }

    private void loadTable() throws SQLException {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM item");
            List<Item> list = new ArrayList<>();
            while(resultSet.next()){
                list.add(new Item(
                        resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand")
                ));
            }
            tblItems.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblItems.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));
        try {
            loadTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
