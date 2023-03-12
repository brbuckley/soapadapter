package com.marlo.training.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.transport.WebServiceConnection;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpUrlConnection;

/** Custom Web Service Callback to add needed headers in soap request. */
@Slf4j
@AllArgsConstructor
public class CustomWebServiceCallback implements WebServiceMessageCallback {

  private String correlation;

  @Override
  public void doWithMessage(WebServiceMessage message) {
    // SoapAction header
    SoapMessage soapMessage = (SoapMessage) message;
    soapMessage.setSoapAction("http://training.marlo.com/PostPurchase");
    // Correlation id header
    TransportContext context = TransportContextHolder.getTransportContext();
    WebServiceConnection connection = context.getConnection();
    if (connection instanceof HttpUrlConnection) {
      HttpUrlConnection conn = (HttpUrlConnection) connection;
      try {
        conn.addRequestHeader("X-Correlation-Id", correlation);
      } catch (Exception e) {
        log.error("Failed to add correlation header");
      }
    }
  }
}
