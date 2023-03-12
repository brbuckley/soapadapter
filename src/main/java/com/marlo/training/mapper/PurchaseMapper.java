package com.marlo.training.mapper;

import au.com.marlo.training.item.Item;
import au.com.marlo.training.supplierrequest.Supplierrequest;
import com.marlo.training.api.model.purchasems.orderline.OrderLineRequest;
import com.marlo.training.api.model.purchasems.purchase.PurchaseRequest;
import org.springframework.stereotype.Component;

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
