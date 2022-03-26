import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static List<String[]> list = new ArrayList<>();
    public static String[] header = {"Product ID","Active (0/1)","Name *",
            "Categories (x,y,z...)","Price tax excluded","Tax rules ID",
            "Wholesale price","On sale (0/1)", "Discount amount",
            "Discount percent","Discount from (yyyy-mm-dd)", "Discount to (yyyy-mm-dd)",
            "Reference #","Supplier reference #", "Supplier",
            "Manufacturer","EAN13","UPC",
            "Ecotax","Width","Height",
            "Depth","Weight","Delivery time of in-stock products",
            "Delivery time of out-of-stock products with allowed orders","Quantity",
            "Minimal quantity","Low stock level",
            "Send me an email when the quantity is under this level",
            "Visibility","Additional shipping cost","Unity",
            "Unit price","Summary","Description",
            "Tags (x,y,z...)","Meta title","Meta keywords",
            "Meta description","URL rewritten","Text when in stock",
            "Text when backorder allowed","Available for order (0 = No, 1 = Yes)",
            "Product available date","Product creation date",
            "Show price (0 = No, 1 = Yes)","Image URLs (x,y,z...)",
            "Image alt texts (x,y,z...)","Delete existing images (0 = No, 1 = Yes)",
            "Feature(Name:Value:Position)","Available online only (0 = No, 1 = Yes)",
            "Condition","Customizable (0 = No, 1 = Yes)",
            "Uploadable files (0 = No, 1 = Yes)",
            "Text fields (0 = No, 1 = Yes)","Out of stock action",
            "Virtual product","File URL","Number of allowed downloads",
            "Expiration date","Number of days","ID / Name of shop",
            "Advanced stock management","Depends On Stock",
            "Warehouse","Acessories  (x,y,z...)"};
    public static void main(String[] args) {
        list.add(header);
        try {
            Document doc = Jsoup.connect("https://supersklep.pl/koszulki").get();
            System.out.println(doc.title());
            int ID = 0;
            Elements elements = doc.getElementsByClass("li-product-box product-box js--productPreview");
            for (Element element : elements) {
                String productTitle = element.getElementsByClass("title").text();
                String[] descriptionEl = productTitle.split(" ",2);

                //ID auto-inc
                final String active = "1";
                String name = descriptionEl[1];
                String category = descriptionEl[0];
                String price = element.getElementsByClass("price").text();
                final String TAX = "1";
                //String wholesale;
                final String onSale = "1";
                //No discounts amount
                //No discounts perc
                //No dsc date
                //No discount date
                //No references1
                //No references2
                String[] supplierArr = descriptionEl[1].split(" ");
                String supplier = supplierArr[0];
                //No manufacturer
                //No EAN
                //No UPC
                //No ecotax
                //No Width
                //No Height
                //No Depth
                //No weight
                //No delivery time
                //No delivery time
                String quantity = "100";
                String minimalQuantity = "1";
                //No low stock level
                String sendMeEmailIfUnderLevel = "0";
                String visibility = "both";
                //No additional shopping costs
                //No unity
                //No unit price
                String summary = "<p>Koszuleczka elegnacka</p>";
                String description = "<p>Super koszuleczka</p>";
                //No tags
                //No meta
                //No meta
                //No meta
                //No URL
                //No text
                //No text
                String avaibleForOrder = "1";
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String productAvDate = dtf.format(now);
                String productCreateDate = productAvDate;
                String showPrice = "1";
                //TODO
                //String imgUrl = "";
                String altTexts = "";


                System.out.println("ID: " + ID);
                System.out.println("Active: " + active);
                System.out.println("Name: " + name);
                System.out.println("Category: " + category);
                System.out.println("Price: " + price);
                System.out.println("TAX: " + TAX);
                System.out.println("Wholesale: ");
                System.out.println("Onsale: " + onSale);
                System.out.println("Supplier: " + supplier);
                System.out.println("Product available: " + productAvDate);
                System.out.println("");
                ID++;
                String[] record = {
                        String.valueOf(ID),
                        active,
                        name,
                        category,
                        price,
                        TAX,
                        "",
                        onSale,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        supplier,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        quantity,
                        minimalQuantity,
                        "",
                        sendMeEmailIfUnderLevel,
                        visibility,
                        "",
                        "",
                        "",
                        summary,
                        description,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        avaibleForOrder,
                        productAvDate,
                        productCreateDate,
                        showPrice,
                        "",
                        altTexts
                };
                list.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //TODO
        createFile();

    }
    private static void createFile(){
        try (ICSVWriter writer = new CSVWriterBuilder(
                new FileWriter("D:\\JetBeansProducts\\MojeCwiczenia\\Scrapping\\Scrapper\\src\\main\\resources\\test.csv"))
                .withSeparator(';')
                .build()) {
            writer.writeAll(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
