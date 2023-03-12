package soapadapter;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

/** Springboot main class. */
@SpringBootApplication
@ConfigurationPropertiesScan("soapadapter.configuration")
public class AdapterApplication {

  public static final String MESSAGE_QUEUE = "adapter-purchase";

  @Bean
  Queue purchaseQueue() {
    return new Queue(MESSAGE_QUEUE, true);
  }

  @PostConstruct
  void started() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  /**
   * Springboot main method.
   *
   * @param args CLI arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(AdapterApplication.class, args);
  }
}
