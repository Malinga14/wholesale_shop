package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Order {
    private String orderID;
    private String orderDate;
    private String custID;

    public Order(String orderID, String orderDate, String cutID){
        this.orderID=orderID;
        this.orderDate=orderDate;
        this.custID=cutID;
    }
}
