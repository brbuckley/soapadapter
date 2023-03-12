package com.marlo.training.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import au.com.marlo.training.supplierrequest.Supplierrequest;
import au.com.marlo.training.supplierresponse.ObjectFactory;
import au.com.marlo.training.supplierresponse.Supplierresponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ws.client.core.WebServiceTemplate;

public class SoapClientTest {

  @Test
  public void testCallSupplier_whenValid_thenReturnSupplierorder() {
    Supplierresponse supplierresponse = new Supplierresponse();
    supplierresponse.setId("SUB0000001");
    supplierresponse.setPurchaseId("PUR0000001");
    Supplierrequest supplierrequest = new Supplierrequest();
    supplierrequest.setId("PUR0000001");

    // Mocks
    WebServiceTemplate mock = Mockito.mock(WebServiceTemplate.class);
    Mockito.when(mock.marshalSendAndReceive(any(), any(), any()))
        .thenReturn(new ObjectFactory().createSupplierresponse(supplierresponse));

    SoapClient soapClient = new SoapClient();
    soapClient.setWebServiceTemplate(mock);

    assertEquals(
        "SUB0000001", soapClient.callSupplier("endpoint", supplierrequest, "correlation").getId());
  }
}
