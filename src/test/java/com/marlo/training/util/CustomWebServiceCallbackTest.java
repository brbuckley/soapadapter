package com.marlo.training.util;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.transport.WebServiceConnection;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpUrlConnection;

public class CustomWebServiceCallbackTest {

  CustomWebServiceCallback callback;

  @Test
  public void testDoWithMessage_whenValid_thenAddHeaders() throws IOException {
    // Mocks
    SoapMessage mockMessage = Mockito.mock(SoapMessage.class);
    TransportContext mockContext = Mockito.mock(TransportContext.class);
    HttpUrlConnection mockConnection = Mockito.mock(HttpUrlConnection.class);
    Mockito.when(mockContext.getConnection()).thenReturn(mockConnection);

    callback = new CustomWebServiceCallback("correlation");
    try (MockedStatic<TransportContextHolder> context =
        Mockito.mockStatic(TransportContextHolder.class)) {
      context.when(TransportContextHolder::getTransportContext).thenReturn(mockContext);
      callback.doWithMessage(mockMessage);
      verify(mockConnection).addRequestHeader("X-Correlation-Id", "correlation");
    }
  }

  @Test
  public void testDoWithMessage_whenInvalid_thenThrow() throws IOException {
    // Mocks
    SoapMessage mockMessage = Mockito.mock(SoapMessage.class);
    TransportContext mockContext = Mockito.mock(TransportContext.class);
    HttpUrlConnection mockConnection = Mockito.mock(HttpUrlConnection.class);
    Mockito.when(mockContext.getConnection()).thenReturn(mockConnection);
    doThrow(new IOException("mock"))
        .when(mockConnection)
        .addRequestHeader("X-Correlation-Id", "correlation");

    callback = new CustomWebServiceCallback("correlation");
    try (MockedStatic<TransportContextHolder> context =
        Mockito.mockStatic(TransportContextHolder.class)) {
      context.when(TransportContextHolder::getTransportContext).thenReturn(mockContext);
      callback.doWithMessage(mockMessage);
      verify(mockConnection).addRequestHeader("X-Correlation-Id", "correlation");
    }
  }

  @Test
  public void testDoWithMessage_whenInvalidConnection_thenDoNothing() {
    // Mocks
    SoapMessage mockMessage = Mockito.mock(SoapMessage.class);
    TransportContext mockContext = Mockito.mock(TransportContext.class);
    WebServiceConnection mockConnection = Mockito.mock(WebServiceConnection.class);
    Mockito.when(mockContext.getConnection()).thenReturn(mockConnection);

    callback = new CustomWebServiceCallback("correlation");
    try (MockedStatic<TransportContextHolder> context =
        Mockito.mockStatic(TransportContextHolder.class)) {
      context.when(TransportContextHolder::getTransportContext).thenReturn(mockContext);
      callback.doWithMessage(mockMessage);
      verify(mockContext).getConnection();
    }
  }
}
