package com.arhohuttunen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class OrderServiceTests {
    @Mock
    //@Autowired
    private OrderRepository orderRepository;

    @Mock
    //@Autowired
    private PaymentRepository paymentRepository;

    @InjectMocks
    //@Autowired
    private OrderService orderService;

    /* or mock annotation
    @BeforeEach
    void setupService() {
        orderRepository = mock(OrderRepository.class);

        paymentRepository = mock(PaymentRepository.class);

        orderService = new OrderService(orderRepository, paymentRepository);
    }*/

    @Test
    void payOrder() {
        Order order = new Order(1L, false);
        orderRepository.save(order);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(paymentRepository.save(any())).then(returnsFirstArg());

        Payment payment = orderService.pay(1L, "4532756279624064");

        assertThat(payment.getOrder().isPaid()).isTrue();
        assertThat(payment.getCreditCardNumber()).isEqualTo("4532756279624064");
    }

    /*@Test
    void cannotPayAlreadyPaidOrder() {
        Order order = new Order(1L, true);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(PaymentException.class, () -> orderService.pay(order.getId(), "4556622577268643"));
    }*/
}
