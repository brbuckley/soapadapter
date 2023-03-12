package soapadapter.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/** Property injection for Supplier A. */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "supplier")
public class SupplierConfig {

  private String endpoint;
}
