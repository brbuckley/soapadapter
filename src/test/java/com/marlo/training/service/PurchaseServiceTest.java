package com.marlo.training.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import au.com.marlo.training.supplierresponse.Supplierresponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marlo.training.AdapterResponseUtil;
import com.marlo.training.configuration.SupplierConfig;
import com.marlo.training.mapper.PurchaseMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class PurchaseServiceTest {

  @Test
  public void testSendToSupplier_whenValid_thenReturnResponse() throws JsonProcessingException {
    Supplierresponse response = new Supplierresponse();
    response.setId("SUB0000001");
    response.setPurchaseId("PUR0000001");

    // Mocks
    SoapClient mockSoap = Mockito.mock(SoapClient.class);
    // Supplierresponse is generated and does not have equals()
    Mockito.when(mockSoap.callSupplier(anyString(), any(), anyString())).thenReturn(response);
    RabbitTemplate mockRabbit = Mockito.spy(RabbitTemplate.class);
    doNothing().when(mockRabbit).send(any(), anyString(), any(), any());

    PurchaseService service =
        new PurchaseService(
            mockSoap,
            mockRabbit,
            new SupplierConfig("endpoint"),
            new ObjectMapper(),
            new PurchaseMapper());
    service.sendToSupplier(AdapterResponseUtil.defaultPurchaseRequest(), "correlation");
    verify(mockRabbit).send(any(), anyString(), any(), any());
  }
}
