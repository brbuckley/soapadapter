package com.marlo.training.service;

import au.com.marlo.training.request.ObjectFactory;
import au.com.marlo.training.supplierrequest.Supplierrequest;
import au.com.marlo.training.supplierresponse.Supplierresponse;
import com.marlo.training.util.CustomWebServiceCallback;
import javax.xml.bind.JAXBElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/** Works like a restTemplate, but for Soap. */
@Slf4j
public class SoapClient extends WebServiceGatewaySupport {

  /**
   * Parses the request into a Soap envelope and send to the given endpoint.
   *
   * @param endpoint Endpoint.
   * @param supplierrequest Supplier request.
   * @return Supplier response.
   */
  public Supplierresponse callSupplier(
      String endpoint, Supplierrequest supplierrequest, String correlation) {

    log.info("Sending Purchase: " + supplierrequest.getId() + " to Supplier B");

    JAXBElement response =
        (JAXBElement)
            getWebServiceTemplate()
                .marshalSendAndReceive(
                    endpoint,
                    new ObjectFactory().createSupplierrequest(supplierrequest),
                    new CustomWebServiceCallback(correlation));

    return (Supplierresponse) response.getValue();
  }
}
