package at.hollander.ibex;

import at.hollander.ibex.entity.*;
import at.hollander.ibex.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@EntityScan(
        basePackageClasses = {IbexApplication.class}
)
@SpringBootApplication
@EnableAutoConfiguration
public class IbexApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(IbexApplication.class, args);
    }

    private static final Logger log = LoggerFactory.getLogger(IbexApplication.class);

    private final PasswordEncoder passwordEncoder;

    private final AdminAccountRepository adminAccountRepository;
    private final AccountRepository accountRepository;
    private final CityRepository cityRepository;
    private final ProductRepository productRepository;
    private final DeliverySlotRepository deliverySlotRepository;
    private final RecurringOrderItemRepository recurringOrderItemRepository;
    private final RecurringOrderRepository recurringOrderRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final InvoiceRepository invoiceRepository;
    private final EntityManager entityManager;

    @Autowired
    public IbexApplication(
            AdminAccountRepository adminAccountRepository,
            AccountRepository accountRepository,
            CityRepository cityRepository,
            ProductRepository productRepository,
            DeliverySlotRepository deliverySlotRepository,
            RecurringOrderItemRepository recurringOrderItemRepository,
            RecurringOrderRepository recurringOrderRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            EntityManager entityManager,
            PasswordEncoder passwordEncoder, InvoiceRepository invoiceRepository) {
        this.adminAccountRepository = adminAccountRepository;
        this.accountRepository = accountRepository;
        this.cityRepository = cityRepository;
        this.productRepository = productRepository;
        this.deliverySlotRepository = deliverySlotRepository;
        this.recurringOrderItemRepository = recurringOrderItemRepository;
        this.recurringOrderRepository = recurringOrderRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
        this.invoiceRepository = invoiceRepository;
    }

    private static <T, ID extends Serializable> void print(String msg, CrudRepository<T, ID> repository) {
        log.info(msg + ":");
        for (T t : repository.findAll()) {
            log.info(t.toString());
        }
    }

    @Override
    public void run(String... args) {
        AdminAccount reneHollander = adminAccountRepository.save(
                AdminAccount.builder()
                        .email("rene.hollander@hotmail.de")
                        .password(passwordEncoder.encode("1234"))
                        .name("Rene Hollander").build());

        City klosterneuburg = cityRepository.save(new City(3400, "Klosterneuburg", true));

        Account maxMustermann = accountRepository.save(
                Account.builder()
                        .name("Max Mustermann")
                        .password(passwordEncoder.encode("1234"))
                        .email("max@mustermann.at")
                        .city(klosterneuburg).build());

        Account bettinaReiss = accountRepository.save(
                Account.builder()
                        .name("Bettina Reiss")
                        .password(passwordEncoder.encode("1234"))
                        .email("bettina@reiss.at")
                        .city(klosterneuburg).build());

        Product semmel = productRepository.save(new Product(1, "Semmel", new BigDecimal("0.30")));
        Product kornspitz = productRepository.save(new Product(2, "Kornspitz", new BigDecimal("0.50")));
        Product briochekipferl = productRepository.save(new Product(3, "Briochekipferl", new BigDecimal("0.70")));
        Product dinkelweckerl = productRepository.save(new Product(4, "Dinkelweckerl", new BigDecimal("0.80")));

        DeliverySlot montag = deliverySlotRepository.save(new DeliverySlot("Montag", LocalTime.of(7, 0)));
        DeliverySlot dienstag = deliverySlotRepository.save(new DeliverySlot("Dienstag", LocalTime.of(7, 0)));
        DeliverySlot mittwoch = deliverySlotRepository.save(new DeliverySlot("Mittwoch", LocalTime.of(7, 0)));
        DeliverySlot donnerstag = deliverySlotRepository.save(new DeliverySlot("Donnerstag", LocalTime.of(7, 0)));
        DeliverySlot freitag = deliverySlotRepository.save(new DeliverySlot("Freitag", LocalTime.of(7, 0)));
        DeliverySlot samstag = deliverySlotRepository.save(new DeliverySlot("Samstag", LocalTime.of(7, 0)));
        DeliverySlot sonntag = deliverySlotRepository.save(new DeliverySlot("Sonntag", LocalTime.of(7, 0)));

        RecurringOrder roMontag = recurringOrderRepository.save(new RecurringOrder(maxMustermann, montag, false));
        RecurringOrder roDienstag = recurringOrderRepository.save(new RecurringOrder(maxMustermann, dienstag, false));
        RecurringOrder roMittwoch = recurringOrderRepository.save(new RecurringOrder(maxMustermann, mittwoch, false));
        RecurringOrder roDonnerstag = recurringOrderRepository.save(new RecurringOrder(maxMustermann, donnerstag, false));
        RecurringOrder roFreitag = recurringOrderRepository.save(new RecurringOrder(maxMustermann, freitag, false));
        RecurringOrder roSamstag = recurringOrderRepository.save(new RecurringOrder(maxMustermann, samstag, false));
        RecurringOrder roSonntag = recurringOrderRepository.save(new RecurringOrder(maxMustermann, sonntag, false));

        recurringOrderItemRepository.save(new RecurringOrderItem(roMontag, semmel, 1));
        recurringOrderItemRepository.save(new RecurringOrderItem(roMontag, kornspitz, 2));
        recurringOrderItemRepository.save(new RecurringOrderItem(roMontag, briochekipferl, 3));

        recurringOrderItemRepository.save(new RecurringOrderItem(roDienstag, semmel, 3));
        recurringOrderItemRepository.save(new RecurringOrderItem(roDienstag, kornspitz, 2));
        recurringOrderItemRepository.save(new RecurringOrderItem(roDienstag, dinkelweckerl, 1));

        recurringOrderItemRepository.save(new RecurringOrderItem(roMittwoch, semmel, 2));
        recurringOrderItemRepository.save(new RecurringOrderItem(roMittwoch, briochekipferl, 3));
        recurringOrderItemRepository.save(new RecurringOrderItem(roMittwoch, dinkelweckerl, 1));

        recurringOrderItemRepository.save(new RecurringOrderItem(roDonnerstag, kornspitz, 1));
        recurringOrderItemRepository.save(new RecurringOrderItem(roDonnerstag, briochekipferl, 2));
        recurringOrderItemRepository.save(new RecurringOrderItem(roDonnerstag, dinkelweckerl, 1));

        recurringOrderItemRepository.save(new RecurringOrderItem(roFreitag, briochekipferl, 1));
        recurringOrderItemRepository.save(new RecurringOrderItem(roFreitag, dinkelweckerl, 2));

        recurringOrderItemRepository.save(new RecurringOrderItem(roSamstag, semmel, 2));
        recurringOrderItemRepository.save(new RecurringOrderItem(roSamstag, dinkelweckerl, 1));

        recurringOrderItemRepository.save(new RecurringOrderItem(roSonntag, semmel, 2));
        recurringOrderItemRepository.save(new RecurringOrderItem(roSonntag, kornspitz, 2));

        Invoice i01 = invoiceRepository.save(new Invoice(maxMustermann, LocalDate.of(2018, 2, 1), "Max Mustermann", "AT621245700000001234"));
        Invoice bi01 = invoiceRepository.save(new Invoice(bettinaReiss, LocalDate.of(2018, 2, 1), "Bettina Reiss", "AT621245700000004321"));

        Order o0101 = orderRepository.save(new Order(maxMustermann, i01, LocalDateTime.of(2018, 01, 01, 07, 00), LocalDateTime.of(2017, 12, 31, 12, 15), "Hauptstrasse 60-62/2/4", 3420, "Kritzendorf", "Zum Postkasten legen", new BigDecimal("1.5")));
        Order o0107 = orderRepository.save(new Order(maxMustermann, i01, LocalDateTime.of(2018, 01, 07, 07, 00), LocalDateTime.of(2018, 1, 6, 11, 45), "Hauptstrasse 60-62/2/4", 3420, "Kritzendorf", "Zum Postkasten legen", new BigDecimal("1.5")));
        Order o0201 = orderRepository.save(new Order(maxMustermann, null, LocalDateTime.of(2018, 02, 01, 07, 00), LocalDateTime.of(2018, 1, 31, 13, 00), "Hauptstrasse 60-62/2/4", 3420, "Kritzendorf", "Zum Postkasten legen", new BigDecimal("1.5")));
        Order o0204 = orderRepository.save(new Order(maxMustermann, null, LocalDateTime.of(2018, 02, 04, 07, 00), LocalDateTime.of(2018, 2, 3, 14, 00), "Hauptstrasse 60-62/2/4", 3420, "Kritzendorf", "Zum Postkasten legen", new BigDecimal("1.5")));

        Order bo0101 = orderRepository.save(new Order(bettinaReiss, bi01, LocalDateTime.of(2018, 01, 01, 07, 00), LocalDateTime.of(2017, 12, 31, 12, 15), "Franz Rumpler Strasse 24", 3400, "Klosterneuburg", "Zum Postkasten legen", new BigDecimal("1.5")));

        orderItemRepository.save(new OrderItem(o0101, semmel, 3, new BigDecimal("0.25")));
        orderItemRepository.save(new OrderItem(o0101, kornspitz, 2, new BigDecimal("0.70")));
        orderItemRepository.save(new OrderItem(o0107, semmel, 2, new BigDecimal("0.25")));
        orderItemRepository.save(new OrderItem(o0107, briochekipferl, 1, new BigDecimal("1.2")));
        orderItemRepository.save(new OrderItem(o0107, kornspitz, 2, new BigDecimal("0.70")));

        orderItemRepository.save(new OrderItem(bo0101, semmel, 1, new BigDecimal("0.25")));

        orderItemRepository.save(new OrderItem(o0201, semmel, 2, new BigDecimal("0.30")));
        orderItemRepository.save(new OrderItem(o0201, kornspitz, 3, new BigDecimal("0.80")));
        orderItemRepository.save(new OrderItem(o0201, dinkelweckerl, 1, new BigDecimal("0.90")));
        orderItemRepository.save(new OrderItem(o0204, semmel, 3, new BigDecimal("0.30")));
        orderItemRepository.save(new OrderItem(o0204, briochekipferl, 1, new BigDecimal("1.25")));
    }
}
