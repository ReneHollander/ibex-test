package at.hollander.ibex.controller.api;

import at.hollander.ibex.View;
import at.hollander.ibex.component.UserAccountService;
import at.hollander.ibex.entity.Account;
import at.hollander.ibex.entity.Invoice;
import at.hollander.ibex.repository.InvoiceRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @JsonView({View.Endpoint.Invoices.class})
    @RequestMapping(value = "/invoices", method = {RequestMethod.GET})
    public Iterable<Invoice> invoices() {
        Account account = userAccountService.getAccount();
        return invoiceRepository.findAllByAccount(account);
    }

    @RequestMapping(value = "/invoices/count", method = {RequestMethod.GET})
    public long invoiceCount() {
        Account account = userAccountService.getAccount();
        return invoiceRepository.countByAccount(account);
    }

    @JsonView({View.Endpoint.InvoiceDetails.class})
    @RequestMapping(value = "/invoice/{id}", method = {RequestMethod.GET})
    public Invoice invoice(@PathVariable("id") int id) {
        Account account = userAccountService.getAccount();
        // TODO: set appropriate http status
        return invoiceRepository.findByIdAndAccount(id, account).orElseThrow(() -> new IllegalArgumentException("invoice doesn't exist for this user"));
    }

}
