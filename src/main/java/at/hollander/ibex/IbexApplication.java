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
    private final EntityManager entityManager;

    @Autowired
    public IbexApplication(
            AdminAccountRepository adminAccountRepository,
            AccountRepository accountRepository,
            CityRepository cityRepository,
            ProductRepository productRepository, DeliverySlotRepository deliverySlotRepository,
            RecurringOrderItemRepository recurringOrderItemRepository,
            RecurringOrderRepository recurringOrderRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, EntityManager entityManager,
            PasswordEncoder passwordEncoder) {
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
    }
}
