package griezma.mssc.beerservice.services.beerorder;

import griezma.mssc.beerservice.config.JmsConfig;
import griezma.mssc.beerservice.data.BeerRepository;
import griezma.mssc.brewery.model.BeerOrderDto;
import griezma.mssc.brewery.model.BeerOrderLineDto;
import griezma.mssc.brewery.model.events.ValidateOrderRequest;
import griezma.mssc.brewery.model.events.ValidateOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderValidationHandler {
    private final BeerRepository repo;
    private final JmsTemplate jms;

    @JmsListener(destination = JmsConfig.VALIDATE_BEERORDER_QUEUE)
    void handleValidateOrder(ValidateOrderRequest validateRequest) {
        BeerOrderDto beerOrder = validateRequest.getOrder();
        List<BeerOrderLineDto> orderLineErrors = beerOrder.getOrderLines().stream()
                .filter(orderLine -> repo.findByUpc(orderLine.getUpc()).isEmpty())
                .collect(Collectors.toList());

        ValidateOrderResponse response;

        if (orderLineErrors.isEmpty()) {
            response = ValidateOrderResponse.builder()
                    .order(beerOrder)
                    .valid(true)
                    .build();
        } else {
            response = ValidateOrderResponse.builder()
                    .order(beerOrder)
                    .valid(false)
                    .build();
        }
        jms.convertAndSend(JmsConfig.VALIDATE_BEERORDER_RESPONSE_QUEUE, response);
    }
}
