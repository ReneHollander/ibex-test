package at.hollander.ibex;

import at.hollander.ibex.component.OrderService;
import at.hollander.ibex.entity.*;
import at.hollander.ibex.repository.*;
import at.hollander.ibex.util.CollectionUtil;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.payment.DefaultIBANProvider;
import io.codearte.jfairy.producer.person.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@Profile("development")
public class CreateFakeDataRunner implements CommandLineRunner {

    private static final String[] ADRESSES_KLOSTERNEUBURG = {
            "Ochsnerpromenade 3-9",
            "Stadtplatz 9",
            "Karl-Rudolf-Werner-Gasse 14",
            "Hermannstraße 17-11",
            "Konradtgasse 34-40",
            "Schredtgasse 3-1",
            "Max Poosch-Gasse 47-1",
            "Schubertgasse 39",
            "Skallgasse 6",
            "Fliederweg 21-22",
            "Ottogasse 14-22",
            "Normannengasse 12",
            "Ubald Kostersitz-Gasse 12-22",
            "Leopold Weinmayer-Straße 14",
            "Waisenhausgasse 3",
            "Augustinergasse 18",
            "Wagnergasse 4-14",
            "Martinstraße 50",
            "Albrechtstraße 65",
            "Langstögergasse 8"
    };

    private static final Logger log = LoggerFactory.getLogger(IbexApplication.class);

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
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
    public CreateFakeDataRunner(
            UserRepository userRepository,
            AccountRepository accountRepository,
            CityRepository cityRepository,
            ProductRepository productRepository,
            DeliverySlotRepository deliverySlotRepository,
            RecurringOrderItemRepository recurringOrderItemRepository,
            RecurringOrderRepository recurringOrderRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            EntityManager entityManager,
            PasswordEncoder passwordEncoder,
            InvoiceRepository invoiceRepository,
            OrderService orderService) {
        this.userRepository = userRepository;
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
        log.info("creating fake data");

        User userReneHollander = userRepository.save(User.builder()
                .role(User.Role.ADMIN)
                .email("rene.hollander@hotmail.de")
                .password(passwordEncoder.encode("1234"))
                .name("Rene Hollander")
                .build());

        User userMaxMustermann = userRepository.save(User.builder()
                .name("Max Mustermann")
                .password(passwordEncoder.encode("1234"))
                .email("max@mustermann.at")
                .build());

        City klosterneuburg = cityRepository.save(new City(3400, "Klosterneuburg", true, new BigDecimal("0.5")));
        City kierling = cityRepository.save(new City(3400, "Kierling"));
        City kritzendorf = cityRepository.save(new City(3420, "Kritzendorf"));
        City weidling = cityRepository.save(new City(3400, "Weidling"));

        Account maxMustermann = accountRepository.save(
                Account.builder()
                        .enabled(true)
                        .user(userMaxMustermann)
                        .city(klosterneuburg)
                        .address("Fischergasse 3")
                        .deliveryNote("Zum Postkasten")
                        .iban("AT813236700005349832")
                        .accountName("Max Mustermann")
                        .phone("0676123456")
                        .build());

        userMaxMustermann.setAccount(maxMustermann);

        Product semmel = productRepository.save(new Product(1, "Semmel", new BigDecimal("0.30")));
        Product kornspitz = productRepository.save(new Product(2, "Kornspitz", new BigDecimal("0.50")));
        Product briochekipferl = productRepository.save(new Product(3, "Briochekipferl", new BigDecimal("0.70")));
        Product dinkelweckerl = productRepository.save(new Product(4, "Dinkelweckerl", new BigDecimal("0.80")));
        Product joghurtriegel = productRepository.save(new Product(5, "Joghurt Riegel", new BigDecimal("0.96")));
        Product croissant = productRepository.save(new Product(6, "Croissant", new BigDecimal("0.79")));
        Product kgbrot = productRepository.save(new Product(7, "1kg Brot", new BigDecimal("1.99")));
        Product zuckerkipferl = productRepository.save(new Product(8, "Zuckerkipferl", new BigDecimal("0.01")));
        Product himbeermasc = productRepository.save(new Product(9, "Himbeer-Mascarpone", new BigDecimal("10")));
        Product kasestangerl = productRepository.save(new Product(10, "Käsestangerl", new BigDecimal("0.90")));
        Product salzstangerl = productRepository.save(new Product(11, "Salzstangerl", new BigDecimal("0.50")));
        Product murbekipferl = productRepository.save(new Product(12, "Mürbes Kipferl", new BigDecimal("0.49")));
        Product bosniaken = productRepository.save(new Product(13, "Bosniaken", new BigDecimal("0.45")));
        Product franzose = productRepository.save(new Product(14, "Franzose groß", new BigDecimal("1.05")));
        Product singlebrot = productRepository.save(new Product(15, "Singlebrot", new BigDecimal("1.60")));

        DeliverySlot montag = deliverySlotRepository.save(new DeliverySlot("Montag", LocalTime.of(7, 0)));
        DeliverySlot dienstag = deliverySlotRepository.save(new DeliverySlot("Dienstag", LocalTime.of(7, 0)));
        DeliverySlot mittwoch = deliverySlotRepository.save(new DeliverySlot("Mittwoch", LocalTime.of(7, 0)));
        DeliverySlot donnerstag = deliverySlotRepository.save(new DeliverySlot("Donnerstag", LocalTime.of(7, 0)));
        DeliverySlot freitag = deliverySlotRepository.save(new DeliverySlot("Freitag", LocalTime.of(7, 0)));
        DeliverySlot samstag = deliverySlotRepository.save(new DeliverySlot("Samstag", LocalTime.of(7, 0)));
        DeliverySlot sonntag = deliverySlotRepository.save(new DeliverySlot("Sonntag", LocalTime.of(7, 0)));

        Product[] products = {semmel, kornspitz, briochekipferl, dinkelweckerl, joghurtriegel, croissant, kgbrot, zuckerkipferl, himbeermasc, kasestangerl, salzstangerl, murbekipferl, bosniaken, franzose, singlebrot};
        DeliverySlot[] deliverySlots = {montag, dienstag, mittwoch, donnerstag, freitag, samstag, sonntag};

        Random random = new Random(0);

        log.info("creating users");
        for (String address : ADRESSES_KLOSTERNEUBURG) {
            User user = createRandomUser(klosterneuburg, address, deliverySlots, products, random);
            userRepository.save(user);
        }

        RecurringOrder roMontag = recurringOrderRepository.save(new RecurringOrder(maxMustermann, montag, true));
        RecurringOrder roDienstag = recurringOrderRepository.save(new RecurringOrder(maxMustermann, dienstag, true));
        RecurringOrder roMittwoch = recurringOrderRepository.save(new RecurringOrder(maxMustermann, mittwoch, true));
        RecurringOrder roDonnerstag = recurringOrderRepository.save(new RecurringOrder(maxMustermann, donnerstag, true));
        RecurringOrder roFreitag = recurringOrderRepository.save(new RecurringOrder(maxMustermann, freitag, true));
        RecurringOrder roSamstag = recurringOrderRepository.save(new RecurringOrder(maxMustermann, samstag, true));
        RecurringOrder roSonntag = recurringOrderRepository.save(new RecurringOrder(maxMustermann, sonntag, true));

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

        Order o0101 = orderRepository.save(new Order(maxMustermann, i01, LocalDateTime.of(2018, 1, 1, 7, 0), LocalDateTime.of(2017, 12, 31, 12, 15), "Hauptstrasse 60-62/2/4", 3420, "Kritzendorf", "Zum Postkasten legen", new BigDecimal("1.5")));
        Order o0107 = orderRepository.save(new Order(maxMustermann, i01, LocalDateTime.of(2018, 1, 7, 7, 0), LocalDateTime.of(2018, 1, 6, 11, 45), "Hauptstrasse 60-62/2/4", 3420, "Kritzendorf", "Zum Postkasten legen", new BigDecimal("1.5")));
        Order o0201 = orderRepository.save(new Order(maxMustermann, null, LocalDateTime.of(2018, 2, 1, 7, 0), LocalDateTime.of(2018, 1, 31, 13, 0), "Hauptstrasse 60-62/2/4", 3420, "Kritzendorf", "Zum Postkasten legen", new BigDecimal("1.5")));
        Order o0204 = orderRepository.save(new Order(maxMustermann, null, LocalDateTime.of(2018, 2, 4, 7, 0), LocalDateTime.of(2018, 2, 3, 14, 0), "Hauptstrasse 60-62/2/4", 3420, "Kritzendorf", "Zum Postkasten legen", new BigDecimal("1.5")));

        orderItemRepository.save(new OrderItem(o0101, semmel, new BigDecimal("0.25"), 3));
        orderItemRepository.save(new OrderItem(o0101, kornspitz, new BigDecimal("0.70"), 2));
        orderItemRepository.save(new OrderItem(o0107, semmel, new BigDecimal("0.25"), 2));
        orderItemRepository.save(new OrderItem(o0107, briochekipferl, new BigDecimal("1.2"), 1));
        orderItemRepository.save(new OrderItem(o0107, kornspitz, new BigDecimal("0.70"), 2));

        orderItemRepository.save(new OrderItem(o0201, semmel, new BigDecimal("0.30"), 2));
        orderItemRepository.save(new OrderItem(o0201, kornspitz, new BigDecimal("0.80"), 3));
        orderItemRepository.save(new OrderItem(o0201, dinkelweckerl, new BigDecimal("0.90"), 1));
        orderItemRepository.save(new OrderItem(o0204, semmel, new BigDecimal("0.30"), 3));
        orderItemRepository.save(new OrderItem(o0204, briochekipferl, new BigDecimal("1.25"), 1));

        entityManager.flush();
        entityManager.clear();

        log.info("creating orders");

        CollectionUtil.streamDateRange(LocalDate.of(2017, 12, 1), LocalDate.now().plusDays(1))
                .forEach(orderService::createOrdersFromRecurringOrders);

        log.info("finished creating fake data");
    }

    private User createRandomUser(City city, String adress, DeliverySlot[] deliverySlots, Product[] products, Random random) {
        Fairy fairy = Fairy.builder().withLocale(Locale.GERMAN).withRandomSeed(random.nextInt()).build();
        Person person = fairy.person();

        User user = User.builder()
                .name(person.getFullName())
                .password(passwordEncoder.encode("1234"))
                .email(person.getEmail())
                .build();

        Account account = Account.builder()
                .accountName(user.getName())
                .iban(new DefaultIBANProvider(fairy.baseProducer()).get().getIbanNumber())
                .phone(person.getTelephoneNumber())
                .address(adress)
                .city(city)
                .enabled(true)
                .deliveryNote(fairy.textProducer().latinSentence())
                .user(user)
                .build();

        user.setAccount(account);

        account.setRecurringOrders(
                Arrays.stream(deliverySlots)
                        .map(slot -> new RecurringOrder(account, slot, random.nextBoolean()))
                        .peek(order -> order.setItems(fairy.baseProducer()
                                .randomElements(fairy.baseProducer().randomBetween(order.isEnabled() ? 1 : 0, 5), products)
                                .stream()
                                .map(p -> new RecurringOrderItem(order, p, fairy.baseProducer().randomBetween(1, 5)))
                                .collect(Collectors.toList())))
                        .collect(Collectors.toList()));

        return user;
    }
}
