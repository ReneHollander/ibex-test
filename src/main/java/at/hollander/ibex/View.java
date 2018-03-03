package at.hollander.ibex;

public interface View {

    interface City {
        interface Details {
        }
    }

    interface Account {
        interface Basic {
        }

        interface Details {
        }
    }

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

    interface Endpoint {
        interface Initial extends Account.Basic {
        }

        interface AccountDetails extends Account.Details {
        }

        interface DisabledCities extends City.Details {
        }

        interface EnabledCities extends City.Details {
        }
    }

}
