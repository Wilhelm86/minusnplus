package minusnplus.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;


public class ScrapeService {


    private static void scrapeCarInfoWithJsoupExample() {
        final String plateNumber = "AAR731".trim().toUpperCase();
        try {
            Document webPage = Jsoup.connect("https://biluppgifter.se/fordon/" + plateNumber).get();
            Elements labels = webPage.select("li span.label");
            Optional<Element> inspectionLabel = labels.stream()
                    .filter(label -> label.text().trim().equalsIgnoreCase("Nästa besiktning senast"))
                    .findFirst();
            if (inspectionLabel.isPresent()) {
                String inspectionDateStr = inspectionLabel.get().siblingElements().select("span.value").get(0).text();
                LocalDate inspectionDate = LocalDate.parse(inspectionDateStr);
                System.out.println("Sista besiktningsdag för " + plateNumber + " är " + inspectionDate);
            } else {
                System.err.println("Kunde inte hitta sista besiktningsdag för " + plateNumber);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        scrapeCarInfoWithJsoupExample();
    }
}
