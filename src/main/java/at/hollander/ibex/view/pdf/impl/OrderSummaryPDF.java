package at.hollander.ibex.view.pdf.impl;

import at.hollander.ibex.entity.Order;
import at.hollander.ibex.view.pdf.AbstractITextPdfView;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderSummaryPDF extends AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Order> orders = (List<Order>) model.get("orders");

        for (Order order : orders) {
            doc.add(new Paragraph(order.toString()));
        }
    }

    public static ModelAndView create(List<Order> orders) {
        Map<String, Object> model = new HashMap<>();
        model.put("orders", orders);
        return new ModelAndView(new OrderSummaryPDF(), model);
    }

}