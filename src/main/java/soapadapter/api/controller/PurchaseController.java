package soapadapter.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import soapadapter.api.model.purchasems.purchase.PurchaseRequest;
import soapadapter.service.PurchaseService;
import soapadapter.util.HeadersUtil;

/** Controller for purchase services. */
@AllArgsConstructor
@RestController
@Validated
public class PurchaseController {

  private final PurchaseService purchaseService;

  /**
   * Post purchase endpoint.
   *
   * @param correlationId Correlation id header.
   * @param purchase Purchase Request.
   * @return Purchase Response.
   * @throws JsonProcessingException Json Processing Exception.
   */
  @PostMapping(value = "/", produces = "application/json")
  public ResponseEntity postPurchase(
      @RequestHeader(name = "X-Correlation-Id", required = false) String correlationId,
      @Valid @RequestBody PurchaseRequest purchase)
      throws JsonProcessingException {
    purchaseService.sendToSupplier(purchase, correlationId);
    return ResponseEntity.ok().headers(HeadersUtil.defaultHeaders(correlationId)).build();
  }
}
