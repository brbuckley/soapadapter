package soapadapter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liquorstore.supplierrequest.Supplierrequest;
import com.liquorstore.supplierresponse.Supplierresponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import soapadapter.AdapterApplication;
import soapadapter.api.model.purchasems.purchase.PurchaseMessage;
import soapadapter.api.model.purchasems.purchase.PurchaseRequest;
import soapadapter.configuration.SupplierConfig;
import soapadapter.mapper.PurchaseMapper;

/** Purchase services. */
@AllArgsConstructor
@Service
@Slf4j
public class PurchaseService {

  private final SoapClient soapClient;
  private final RabbitTemplate rabbitTemplate;
  private final SupplierConfig config;
  private final ObjectMapper mapper;
  private final PurchaseMapper purchaseMapper;

  /**
   * Sends the adapted purchase to SupplierA.
   *
   * @param purchase Purchase Request.
   * @param correlationId Correlation id.
   * @throws JsonProcessingException Json Processing Exception.
   */
  public void sendToSupplier(PurchaseRequest purchase, String correlationId)
      throws JsonProcessingException {
    Supplierrequest request = purchaseMapper.toSupplierrequest(purchase);
    Supplierresponse supplierorder =
        this.soapClient.callSupplier(config.getEndpoint(), request, correlationId);
    log.info("Response from Supplier B: {}", supplierorder.toString());
    PurchaseMessage purchaseMessage =
        new PurchaseMessage(supplierorder.getId(), supplierorder.getPurchaseId());
    String json = mapper.writeValueAsString(purchaseMessage);
    log.info("Sending message with id: {} & body: {}", correlationId, json);
    rabbitTemplate.convertAndSend(
        AdapterApplication.MESSAGE_QUEUE,
        json,
        m -> {
          m.getMessageProperties().getHeaders().put("X-Correlation-Id", correlationId);
          return m;
        });
  }
}
