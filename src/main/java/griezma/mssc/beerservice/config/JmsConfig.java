package griezma.mssc.beerservice.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Slf4j
@EnableJms
@Configuration
public class JmsConfig {

    public static final String BREWING_REQUEST_QUEUE = "brewing-request";
    public static final String INVENTORY_EVENT_QUEUE = "beer-inventory-event";
    public static final String VALIDATE_BEERORDER_QUEUE = "validate-order";
    public static final String VALIDATE_BEERORDER_RESPONSE_QUEUE = "validate-order-response";

    @Bean
    MessageConverter jsonConverter(ObjectMapper om) {
        log.debug("Configuring json converter");
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        converter.setObjectMapper(om);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_JsonType");
        return converter;
    }
}
