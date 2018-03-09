package at.hollander.ibex.view.pdf.impl;

import at.hollander.ibex.repository.helper.ProductAmount;
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

public class ProductAmountsPDF extends AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ProductAmount> productAmounts = (List<ProductAmount>) model.get("productAmounts");

        for (ProductAmount productAmount : productAmounts) {
            doc.add(new Paragraph(productAmount.toString()));
        }
    }

    public static ModelAndView create(List<ProductAmount> productAmounts) {
        Map<String, Object> model = new HashMap<>();
        model.put("productAmounts", productAmounts);
        return new ModelAndView(new ProductAmountsPDF(), model);
    }

}