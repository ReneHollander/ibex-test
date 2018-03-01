package at.hollander.ibex.controller.api;

import at.hollander.ibex.component.UserAccountService;
import at.hollander.ibex.entity.Account;
import at.hollander.ibex.entity.Invoice;
import at.hollander.ibex.repository.InvoiceRepository;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    private final InvoiceRepository invoiceRepository;
    private final UserAccountService userAccountService;

    @Autowired
    public InvoiceController(InvoiceRepository invoiceRepository, UserAccountService userAccountService) {
        this.invoiceRepository = invoiceRepository;
        this.userAccountService = userAccountService;
    }

    @RequestMapping(value = "/invoices", method = {RequestMethod.GET})
    public Stream<InvoiceSimple> invoices() {
        Account account = userAccountService.getAccount();
        return invoiceRepository.findAllByAccount(account).stream().map(InvoiceSimple::create);
    }

    @RequestMapping(value = "/invoices/count", method = {RequestMethod.GET})
    public long invoiceCount() {
        Account account = userAccountService.getAccount();
        return invoiceRepository.countByAccount(account);
    }

    @RequestMapping(value = "/invoice/{id}", method = {RequestMethod.GET})
    public Invoice invoice(@PathVariable("id") int id) {
        Account account = userAccountService.getAccount();
        // TODO: set appropriate http status
        // TODO: don't send order items
        return invoiceRepository.findByIdAndAccount(id, account).orElseThrow(() -> new IllegalArgumentException("invoice doesn't exist for this user"));
    }

    @Data
    @Builder
    public static class InvoiceSimple {
        private int id;
        private LocalDate date;

        public static InvoiceSimple create(Invoice invoice) {
            return builder().id(invoice.getId()).date(invoice.getDate()).build();
        }
    }

}
