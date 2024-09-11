package model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Item {
    private String id;
    private String description;
    private String packSize;
    private double unitPrice;
    private int qtyOnHand;

    public Item(String id, String description, String packSize, double unitPrice, int qtyOnHand){
        this.id=id;
        this.description=description;
        this.packSize=packSize;
        this.unitPrice=unitPrice;
        this.qtyOnHand=qtyOnHand;
    }

}
