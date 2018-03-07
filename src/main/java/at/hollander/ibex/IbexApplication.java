package at.hollander.ibex;

import at.hollander.ibex.component.OrderService;
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
import javax.transaction.Transactional;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

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
    private final OrderService orderService;

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
            PasswordEncoder passwordEncoder, InvoiceRepository invoiceRepository, OrderService orderService) {
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
        this.orderService = orderService;
    }

    private static <T, ID extends Serializable> void print(String msg, CrudRepository<T, ID> repository) {
        log.info(msg + ":");
        for (T t : repository.findAll()) {
            log.info(t.toString());
        }
    }

    @Override
    @Transactional
    public void run(String... args) {
        AdminAccount reneHollander = adminAccountRepository.save(
                AdminAccount.builder()
                        .email("rene.hollander@hotmail.de")
                        .password(passwordEncoder.encode("1234"))
                        .name("Rene Hollander")
                        .build());

        City klosterneuburg = cityRepository.save(new City(3400, "Klosterneuburg", true, new BigDecimal("0.5")));
        City kierling = cityRepository.save(new City(3400, "Kierling"));
        City kritzendorf = cityRepository.save(new City(3420, "Kritzendorf"));
        City weidling = cityRepository.save(new City(3400, "Weidling"));

        Account maxMustermann = accountRepository.save(
                Account.builder()
                        .name("Max Mustermann")
                        .password(passwordEncoder.encode("1234"))
                        .email("max@mustermann.at")
                        .city(klosterneuburg)
                        .address("Musterstrasse 69")
                        .deliveryNote("Zum Postkasten")
                        .iban("AT123456789")
                        .accountName("Max Mustermann")
                        .phone("0676123456")
                        .build());

        Account bettinaReiss = accountRepository.save(
                Account.builder()
                        .name("Bettina Reiss")
                        .password(passwordEncoder.encode("1234"))
                        .email("bettina@reiss.at")
                        .address("Hauptstrasse 60-62/2/4")
                        .iban("AT9349618554854")
                        .accountName("Bettina Reiss")
                        .phone("069911082373")
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
        RecurringOrder roDonnerstag = recurringOrderRepository.save(new RecurringOrder(maxMustermann, donnerstag, true));
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

//        Invoice i01 = invoiceRepository.save(new Invoice(maxMustermann, LocalDate.of(2018, 2, 1), "Max Mustermann", "AT621245700000001234"));
//        Invoice bi01 = invoiceRepository.save(new Invoice(bettinaReiss, LocalDate.of(2018, 2, 1), "Bettina Reiss", "AT621245700000004321"));
//
//        Order o0101 = orderRepository.save(new Order(maxMustermann, i01, LocalDateTime.of(2018, 1, 1, 7, 0), LocalDateTime.of(2017, 12, 31, 12, 15), "Hauptstrasse 60-62/2/4", 3420, "Kritzendorf", "Zum Postkasten legen", new BigDecimal("1.5")));
//        Order o0107 = orderRepository.save(new Order(maxMustermann, i01, LocalDateTime.of(2018, 1, 7, 7, 0), LocalDateTime.of(2018, 1, 6, 11, 45), "Hauptstrasse 60-62/2/4", 3420, "Kritzendorf", "Zum Postkasten legen", new BigDecimal("1.5")));
//        Order o0201 = orderRepository.save(new Order(maxMustermann, null, LocalDateTime.of(2018, 2, 1, 7, 0), LocalDateTime.of(2018, 1, 31, 13, 0), "Hauptstrasse 60-62/2/4", 3420, "Kritzendorf", "Zum Postkasten legen", new BigDecimal("1.5")));
//        Order o0204 = orderRepository.save(new Order(maxMustermann, null, LocalDateTime.of(2018, 2, 4, 7, 0), LocalDateTime.of(2018, 2, 3, 14, 0), "Hauptstrasse 60-62/2/4", 3420, "Kritzendorf", "Zum Postkasten legen", new BigDecimal("1.5")));
//
//        Order bo0101 = orderRepository.save(new Order(bettinaReiss, bi01, LocalDateTime.of(2018, 1, 1, 7, 0), LocalDateTime.of(2017, 12, 31, 12, 15), "Franz Rumpler Strasse 24", 3400, "Klosterneuburg", "Zum Postkasten legen", new BigDecimal("1.5")));
//
//        orderItemRepository.save(new OrderItem(o0101, semmel, new BigDecimal("0.25"), 3));
//        orderItemRepository.save(new OrderItem(o0101, kornspitz, new BigDecimal("0.70"), 2));
//        orderItemRepository.save(new OrderItem(o0107, semmel, new BigDecimal("0.25"), 2));
//        orderItemRepository.save(new OrderItem(o0107, briochekipferl, new BigDecimal("1.2"), 1));
//        orderItemRepository.save(new OrderItem(o0107, kornspitz, new BigDecimal("0.70"), 2));
//
//        orderItemRepository.save(new OrderItem(bo0101, semmel, new BigDecimal("0.25"), 1));
//
//        orderItemRepository.save(new OrderItem(o0201, semmel, new BigDecimal("0.30"), 2));
//        orderItemRepository.save(new OrderItem(o0201, kornspitz, new BigDecimal("0.80"), 3));
//        orderItemRepository.save(new OrderItem(o0201, dinkelweckerl, new BigDecimal("0.90"), 1));
//        orderItemRepository.save(new OrderItem(o0204, semmel, new BigDecimal("0.30"), 3));
//        orderItemRepository.save(new OrderItem(o0204, briochekipferl, new BigDecimal("1.25"), 1));

        entityManager.flush();
        entityManager.clear();


        List<Order> orders = orderService.recurringOrdersToOrders(LocalDate.now().plusDays(1));

        orderService.addProducts(orders).forEach((key, value) -> log.info(key.getName() + ": " + value));

        entityManager.flush();
        entityManager.clear();

        System.out.println(orderItemRepository.findAll());

        orderRepository.addProducts(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant())).forEach(entry -> log.info(entry.getProduct() + ": " + entry.getAmount()));

//        Order o = orderRepository.findById(1).get();
//        log.info("o0101.getPriceTotal(): " + o.getPriceTotal());
    }
}
