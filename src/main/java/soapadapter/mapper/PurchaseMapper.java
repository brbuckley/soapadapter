package soapadapter.mapper;

import com.liquorstore.item.Item;
import com.liquorstore.supplierrequest.Supplierrequest;
import org.springframework.stereotype.Component;
import soapadapter.api.model.purchasems.orderline.OrderLineRequest;
import soapadapter.api.model.purchasems.purchase.PurchaseRequest;

/** Mapper to parse purchase related objects. */
@Component
public class PurchaseMapper {

  /**
   * Parses from PurchaseRequest to SupplierRequest.
   *
   * @param request Purchase request.
   * @return Supplier request.
   */
  public Supplierrequest toSupplierrequest(PurchaseRequest request) {
    Supplierrequest supplierrequest = new Supplierrequest();
    supplierrequest.setId(request.getId());
    supplierrequest.setItems(new Supplierrequest.Items());
    for (OrderLineRequest orderLine : request.getItems()) {
      Item item = new Item();
      item.setQuantity(orderLine.getQuantity());
      item.setId(orderLine.getProduct().getId());
      supplierrequest.getItems().getItem().add(item);
    }
    return supplierrequest;
  }
}
