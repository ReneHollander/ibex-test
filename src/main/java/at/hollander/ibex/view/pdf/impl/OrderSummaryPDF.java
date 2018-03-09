package at.hollander.ibex.view.pdf.impl;

import at.hollander.ibex.entity.Order;
import at.hollander.ibex.entity.OrderItem;
import at.hollander.ibex.view.pdf.AbstractITextPdfView;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderSummaryPDF extends AbstractITextPdfView {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Date date = getDate(model);
        List<Order> orders = getOrders(model);

        Font baseFont = new Font(Font.FontFamily.HELVETICA);

        Paragraph header = new Paragraph("Bestellungen für " + DATE_FORMAT.format(date), baseFont);
        header.setSpacingAfter(10);
        doc.add(header);

        PdfPTable mainTable = new PdfPTable(2);
        mainTable.setWidthPercentage(100);

        for (Order order : orders) {
            PdfPTable orderElement = createOrder(order);
            PdfPCell c = new PdfPCell(orderElement);
            c.setCellEvent(new MarginBorder());
            c.setPadding(8);
            c.setBorder(PdfPCell.NO_BORDER);
            mainTable.addCell(c);
        }
        doc.add(mainTable);
    }

    private void addRow(Phrase p1, Phrase p2, boolean isItem, PdfPTable table) {
        PdfPCell c1 = new PdfPCell(p1);
        PdfPCell c2 = new PdfPCell(p2);
        c1.setBorder(PdfPCell.NO_BORDER);
        c2.setBorder(PdfPCell.NO_BORDER);
        if (isItem) {
            c1.setColspan(1);
            c2.setColspan(7);
        } else {
            c1.setColspan(2);
            c2.setColspan(6);
        }
        table.addCell(c1);
        table.addCell(c2);
    }

    private PdfPTable createOrder(Order order) throws DocumentException {
        PdfPTable table = new PdfPTable(8);

        addRow(new Phrase("Bestellung"), new Phrase(order.getId() + ", " + order.getDeliveryTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss"))), false, table);
        addRow(new Phrase("Kunde"), new Phrase(order.getAccount().getId() + ", " + order.getAccount().getUser().getName() + ", " + order.getAccount().getPhone()), false, table);
        addRow(new Phrase("Adresse"), new Phrase(order.getAddress() + "\n" + order.getCity()), false, table);
        addRow(new Phrase("Hinweis"), new Phrase(order.getDeliveryNote()), false, table);

        for (OrderItem item : order.getItems()) {
            addRow(new Phrase(String.valueOf(item.getAmount())), new Phrase(item.getProductName()), true, table);
        }
        return table;
    }

    @Override
    protected String getName(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        return "Bestellübersicht-" + DATE_FORMAT.format(getDate(model));
    }

    private Date getDate(Map<String, Object> model) {
        return (Date) model.get("date");
    }

    @SuppressWarnings("unchecked")
    private List<Order> getOrders(Map<String, Object> model) {
        return (List<Order>) model.get("orders");
    }

    public static ModelAndView create(List<Order> orders) {
        Map<String, Object> model = new HashMap<>();
        model.put("orders", orders);
        return new ModelAndView(new OrderSummaryPDF(), model);
    }

    public class MarginBorder implements PdfPCellEvent {
        public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
            float x1 = position.getLeft() + 4;
            float x2 = position.getRight() - 4;
            float y1 = position.getTop() - 4;
            float y2 = position.getBottom() + 4;
            PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
            canvas.rectangle(x1, y1, x2 - x1, y2 - y1);
            canvas.stroke();
        }
    }

}