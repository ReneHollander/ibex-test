package at.hollander.ibex;

public interface View {
    interface Invoice {
        interface List {
        }

        interface Overview {
        }
    }

    interface Order {
        interface List {
        }

        interface Overview {
        }
    }

    interface Product {
        interface Overview {
        }
    }

    interface InvoiceOverviewAndOrderList extends Invoice.Overview, Order.List {
    }

    interface OrderOverviewAndProductOverview extends Order.Overview, Product.Overview {
    }

}
