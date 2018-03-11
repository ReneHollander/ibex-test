package at.hollander.ibex;

public interface View {

    interface City {
        interface Details {
        }
    }

    interface User {
        interface Details {
        }

        interface Name {
        }
    }

    interface Account {
        interface Basic {
        }

        interface Overview {
        }

        interface Details {
        }

        interface AddressInfo {
        }

        interface User {
        }

        interface ContactInfo {
        }
    }

    interface Invoice {
        interface Overview {
        }

        interface Details {
        }
    }

    interface Order {
        interface Overview {
        }

        interface Details {
        }

        interface Account {
        }
    }

    interface Product {
        interface Overview {
        }

        interface Details {
        }
    }

    interface ProductAmount {
        interface Details {
        }
    }


    interface Endpoint {
        interface Initial extends User.Details, View.Account.Overview, City.Details {
        }

        interface Account {
            interface Details extends User.Details, View.Account.Details, City.Details {
            }
        }

        interface DisabledCities extends City.Details {
        }

        interface EnabledCities extends City.Details {
        }

        interface Invoices extends Invoice.Overview {
        }

        interface InvoiceDetails extends Invoice.Details, Order.Overview {
        }

        interface OrderDetails extends Order.Details, Product.Details {
        }

        interface PendingOrders extends Order.Overview {
        }

        interface Admin {
            interface ProductAmounts extends ProductAmount.Details, Product.Overview {
            }

            interface OrderSummary extends Order.Details, Product.Overview, Order.Account, View.Account.User, View.Account.Basic, User.Name, View.Account.ContactInfo {
            }
        }
    }
}
