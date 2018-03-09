package at.hollander.ibex.view.pdf.impl;

import at.hollander.ibex.repository.helper.ProductAmount;
import at.hollander.ibex.view.pdf.AbstractITextPdfView;
import com.google.common.collect.Iterables;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ProductAmountsPDF extends AbstractITextPdfView {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private static final float ROW_HEIGHT = 20f;
    private static final float CELL_LEFT_RIGHT_PADDING = 5f;

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Date date = getDate(model);
        List<ProductAmount> productAmounts = getProductAmounts(model);
        Font baseFont = new Font(Font.FontFamily.HELVETICA);

        Paragraph header = new Paragraph("Bestellung für " + DATE_FORMAT.format(date), baseFont);
        header.setSpacingAfter(10);
        doc.add(header);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 4, 1});
        table.setHeaderRows(1);

        Font boldFont = new Font(baseFont);
        boldFont.setStyle(Font.BOLD);

        PdfPCell idCell = createCell("#", boldFont, BaseColor.LIGHT_GRAY);
        PdfPCell nameCell = createCell("Produkt", boldFont, BaseColor.LIGHT_GRAY);
        PdfPCell amountCell = createCell("Menge", boldFont, BaseColor.LIGHT_GRAY);

        table.addCell(idCell);
        table.addCell(nameCell);
        table.addCell(amountCell);

        Iterator<BaseColor> alternatingColors = Iterables.cycle(BaseColor.WHITE, new BaseColor(211, 211, 211)).iterator();
        for (ProductAmount productAmount : productAmounts) {
            addProductAmountRow(productAmount, table, baseFont, alternatingColors.next());
        }

        doc.add(table);
    }

    private void addProductAmountRow(ProductAmount productAmount, PdfPTable table, Font font, BaseColor color) {
        PdfPCell idCell = createCell(String.valueOf(productAmount.getProduct().getId()), font, color);
        PdfPCell nameCell = createCell(productAmount.getProduct().getName(), font, color);
        PdfPCell amountCell = createCell(String.valueOf(productAmount.getAmount()), font, color);
        amountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(idCell);
        table.addCell(nameCell);
        table.addCell(amountCell);
    }

    private PdfPCell createCell(String content, Font font, BaseColor color) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setFixedHeight(ROW_HEIGHT);
        cell.setBackgroundColor(color);
        cell.setUseAscender(true);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingLeft(CELL_LEFT_RIGHT_PADDING);
        cell.setPaddingRight(CELL_LEFT_RIGHT_PADDING);
        return cell;
    }

    @Override
    protected String getName(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        return "Mengen-" + DATE_FORMAT.format(getDate(model));
    }

    private Date getDate(Map<String, Object> model) {
        return (Date) model.get("date");
    }

    @SuppressWarnings("unchecked")
    private List<ProductAmount> getProductAmounts(Map<String, Object> model) {
        return (List<ProductAmount>) model.get("productAmounts");
    }

    public static ModelAndView create(List<ProductAmount> productAmounts) {
        Map<String, Object> model = new HashMap<>();
        model.put("productAmounts", productAmounts);
        return new ModelAndView(new ProductAmountsPDF(), model);
    }

}