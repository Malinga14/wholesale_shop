package controller.customer;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import util.CrudUtil;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewFormController implements Initializable {
    private Connection connection;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public TableColumn colSalary;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public DatePicker dateDob;
    public ComboBox cmbTitle;
    public JFXTextField txtSalary;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<Customer> tblCustomers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr");
        titles.add("Miss");
        titles.add("Ms");
        cmbTitle.setItems(titles);
        tblCustomers.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));
        loadTable();

        try{
            Connection connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTextToValues(Customer newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtCity.setText(newValue.getCity());
        txtProvince.setText(newValue.getProvince());
        txtSalary.setText(String.valueOf(newValue.getSalary()));
        txtPostalCode.setText(newValue.getPostalCode());
        dateDob.setValue(newValue.getDob());
        cmbTitle.setValue(newValue.getTitle());
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }


    public void btnAddOnAction(ActionEvent actionEvent) {
        add();
    }


    private void loadTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

        tblCustomers.setItems(customerObservableList);

        try {
            String SQL = "SELECT * FROM customer";
            ResultSet resultSet = CrudUtil.execute(SQL);
            List<Customer> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getString("CustAddress"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("postalCode")
                ));
                tblCustomers.setItems(FXCollections.observableArrayList(list));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        delete();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        search();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        update();
    }
    private void add(){
        Customer customer = new Customer(
                txtId.getText(),
                cmbTitle.getValue().toString(),
                txtName.getText(),
                txtAddress.getText(),
                dateDob.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtCity.getText(), txtProvince.getText(),
                txtPostalCode.getText()
        );
        try {
            if((CrudUtil.execute("INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)",
                    customer.getId(),
                    customer.getTitle(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getDob(),
                    customer.getSalary(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()
            )))
            {
                new Alert(Alert.AlertType.INFORMATION,"Customer Added!").show();
                loadTable();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void delete(){
        try {
            if(CrudUtil.execute("DELETE FROM Customer WHERE CustID='"+txtId.getText()+"'")){
                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted!").show();
                loadTable();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(){
        try {
            if(CrudUtil.execute("UPDATE Customer SET CustID='"+txtId.getText()
                    +"',CustName = '"+txtName.getText()
                    +"',CustTitle = '"+cmbTitle.getValue()
                    +"',DOB = '"+dateDob.getValue()
                    +"',salary = '"+txtSalary.getText()
                    +"',CustAddress = '"+txtAddress.getText()
                    +"',City = '"+txtCity.getText()
                    +"',Province = '"+txtProvince.getText()
                    +"',PostalCode = '"+txtPostalCode.getText()
                    +"' WHERE CustId='"+txtId.getText() +"'"))
            {
                new Alert(Alert.AlertType.INFORMATION,"Customer Updated!").show();
                loadTable();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void search(){
        try {
            String SQL = "SELECT * FROM customer WHERE CustID = '"+txtId.getText()+"'";
            ResultSet rst = CrudUtil.execute(SQL);
            List<Customer> list = new ArrayList<>();
            while(rst.next()){
                txtId.setText(rst.getString("CustID"));
                cmbTitle.setValue(rst.getString("CustTitle"));
                txtName.setText(rst.getString("CustName"));
                dateDob.setValue(LocalDate.parse(rst.getString("DOB")));
                txtSalary.setText(rst.getString("salary"));
                txtAddress.setText(rst.getString("CustAddress"));
                txtCity.setText(rst.getString("City"));
                txtProvince.setText(rst.getString("Province"));
                txtPostalCode.setText(rst.getString("PostalCode"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
