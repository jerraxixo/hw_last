import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.Document;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.stream.Collectors;

public class Main {

    private static ArrayList<ProductAvailability> productsAvailability = new ArrayList<>();
    private static ArrayList<Sales> sales = new ArrayList<>();

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        XMLHandlerProductAvailability handlerProductAvailability = new XMLHandlerProductAvailability();
        parser.parse(new File("src/inProductAvailability"), handlerProductAvailability);

        XMLHandlerSales handlerSales = new XMLHandlerSales();
        parser.parse(new File("src/inSales"), handlerSales);

        WriteToFileTask1(getMaxEachProductAvailability());
        WriteToFileTask2(getsalesDistribution());
    }

    private static class XMLHandlerProductAvailability extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("ProductAvailability")) {
                int sellerId = Integer.parseInt(attributes.getValue("sellerId"));
                int productId = Integer.parseInt(attributes.getValue("productId"));
                int productPrice = Integer.parseInt(attributes.getValue("productPrice"));
                int productAmount = Integer.parseInt(attributes.getValue("productAmount"));

                productsAvailability.add(new ProductAvailability(sellerId, productId, productPrice, productAmount));
            }
        }
    }

    private static class XMLHandlerSales extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("sale")) {
                int salesId = Integer.parseInt(attributes.getValue("salesId"));
                int sellerId = Integer.parseInt(attributes.getValue("sellerId"));
                int productId = Integer.parseInt(attributes.getValue("productId"));
                int productAmount = Integer.parseInt(attributes.getValue("productAmount"));
                String dateString = attributes.getValue("date");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
                LocalDate date = LocalDate.parse(dateString, formatter);

                sales.add(new Sales(salesId, sellerId, productId, productAmount, date));
            }
        }
    }

    public static Map<Integer, Optional<ProductAvailability>> getMaxEachProductAvailability () {
        Map<Integer, Optional<ProductAvailability>> maxEachProductAvailability =
                productsAvailability.stream()
                        .collect(Collectors.groupingBy(ProductAvailability::getProductId,
                                Collectors.maxBy(Comparator.comparing(ProductAvailability::getProductAmount))));
        return maxEachProductAvailability;
    }

    public static Map<LocalDate, Long> getsalesDistribution () {
        Map<LocalDate, Long> salesDistribution = sales.stream()
                .collect(Collectors.groupingBy(Sales::getDate, Collectors.counting()));
        return salesDistribution;
    }

    private static void WriteToFileTask1(Map<Integer,Optional<ProductAvailability>> map) throws ParserConfigurationException, TransformerException, FileNotFoundException {
        DocumentBuilderFactory dbf;
        DocumentBuilder        db ;
        Document               doc;

        dbf = DocumentBuilderFactory.newInstance();
        db  = dbf.newDocumentBuilder();
        doc = db.newDocument();

        Element root = doc.createElement("company");
        Element name = doc.createElement("name");
        name.setTextContent("products availability list");
        Element productsAvailability = doc.createElement("ProductsAvailability");

        root.appendChild(name);
        root.appendChild(productsAvailability);

        for (Optional<ProductAvailability> productAvailability: map.values()) {
            Element prodAvEl = doc.createElement("ProductAvailability");

            ProductAvailability prodAv = null;

            if (productAvailability.isPresent()) {
                prodAv = productAvailability.get();
                prodAvEl.setAttribute("sellerId", Integer.toString(prodAv.getSellerId()));
                prodAvEl.setAttribute("ProductId", Integer.toString(prodAv.getProductId()));
                prodAvEl.setAttribute("ProductPrice", Integer.toString(prodAv.getProductPrice()));
                prodAvEl.setAttribute("ProductAmount", Integer.toString(prodAv.getProductAmount()));
            }

            productsAvailability.appendChild(prodAvEl);
        }

        doc.appendChild(root);

        Transformer t=TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream("Task1.xml")));
    }

    private static void WriteToFileTask2(Map<LocalDate, Long> map) throws ParserConfigurationException, TransformerException, FileNotFoundException {
        DocumentBuilderFactory dbf;
        DocumentBuilder        db ;
        Document               doc;

        dbf = DocumentBuilderFactory.newInstance();
        db  = dbf.newDocumentBuilder();
        doc = db.newDocument();

        Element root = doc.createElement("company");
        Element name = doc.createElement("name");
        name.setTextContent("sales list");
        Element productsAvailability = doc.createElement("NumberOfSales");

        root.appendChild(name);
        root.appendChild(productsAvailability);

        for (Map.Entry<LocalDate, Long> entry: map.entrySet()) {
            Element prodAvEl = doc.createElement("NumberOfSales");

            if (entry.getValue() != null) {
                prodAvEl.setAttribute("Date",  entry.getKey().toString());
                prodAvEl.setAttribute("NumberOfSales", Long.toString(entry.getValue()));
            }

            productsAvailability.appendChild(prodAvEl);
        }

        doc.appendChild(root);

        Transformer t=TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream("Task2.xml")));
    }
}