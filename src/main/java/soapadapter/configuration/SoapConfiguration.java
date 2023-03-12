package soapadapter.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import soapadapter.service.SoapClient;

/** Soap Configuration. */
@AllArgsConstructor
@Configuration
public class SoapConfiguration {

  private final SupplierConfig config;

  /**
   * Jaxb Marshaller Configuration.
   *
   * @return Jaxb 2 Marshaller.
   */
  @Bean
  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    // this package must match the package in the <generatePackage> specified in
    // pom.xml
    marshaller.setPackagesToScan("com.liquorstore");
    return marshaller;
  }

  /**
   * Intantiate Soap Client and injects the Marshaller.
   *
   * @param marshaller Jaxb Marshaller.
   * @return Soap Client.
   */
  @Bean
  public SoapClient soapClient(Jaxb2Marshaller marshaller) {
    SoapClient client = new SoapClient();
    client.setDefaultUri(config.getEndpoint());
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    return client;
  }
}
