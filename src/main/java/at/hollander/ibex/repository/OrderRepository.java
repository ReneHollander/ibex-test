package at.hollander.ibex.repository;

import at.hollander.ibex.entity.Account;
import at.hollander.ibex.entity.Order;
import at.hollander.ibex.repository.helper.ProductOrderSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    public Iterable<Order> findAllByAccountAndInvoiceIsNull(Account account);

    public Optional<Order> findByIdAndAccount(int id, Account account);

    @Query(value = "SELECT new at.hollander.ibex.repository.helper.ProductOrderSummary(oi.id.product, oi.productName, SUM(oi.amount)) " +
            "FROM Order o " +
            "INNER JOIN OrderItem oi ON o.id = oi.order " +
            "WHERE DATE(o.deliveryTime) = DATE(:deliveryDate) " +
            "GROUP BY oi.id.product")
    List<ProductOrderSummary> addProducts(@Param("deliveryDate") Date deliveryDate);

}
