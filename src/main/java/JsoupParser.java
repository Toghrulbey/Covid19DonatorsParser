import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsoupParser {
    private static String url = "http://covid19fund.gov.az/az/donation";
    private static String legalEntitiesSelector = "#legal-entities > div > table";
    private static String individualSelector = "#indivudal-local > div > table > tbody";


    private static String foreignIndividualsSelector = "#foreign-individuals > div > table";
    private static String foreignLegalEntitiesSelector = "#foreign-legal-entities > div > table > tbody";
    private static List<Donator> donators = new ArrayList<>();
    public static void parse(){
        try {
            Document doc = Jsoup.connect(url).get();


            //localLegalEntities
            Element legalTable = doc.select(legalEntitiesSelector).get(0);
            Elements legalTableRows = legalTable.select("tr");

            for (int i = 0; i < legalTableRows.size()-1; i++) {
                Element row = legalTableRows.get(i);
                Elements cols = row.select("td");
                Donator item = new Donator();


                item.setName(cols.get(0).html());

                item.setAmount(getNormalizedDecimal(cols.get(1).html()));
                item.setCurrency("AZN");
                item.setTypes(PersonTypes.LEGAL);
                donators.add(item);



            }


            Element foreignLegalTable = doc.select(foreignLegalEntitiesSelector).get(0);
            Elements foreignLegalTableRows = foreignLegalTable.select("tr");

            for (int i = 0; i < foreignLegalTableRows.size(); i++) {
                Element row = foreignLegalTableRows.get(i);
                Elements cols = row.select("td");

                Donator item = new Donator();
                item.setName(cols.get(0).html());


                String amount = cols.get(1).html();
                List<String> list = Arrays.asList(amount.split(" "));

                BigDecimal value = getNormalizedDecimal((list.get(0)));

                String currency = list.get(1);


                item.setAmount(value);
                item.setCurrency(getNormalizedCurrency(currency));
                item.setTypes(PersonTypes.LEGAL);

                donators.add(item);
            }


            Element individualTable = doc.select(individualSelector).get(0);
            Elements individualTableRows = individualTable.select("tr");


            for (int i = 0; i < individualTableRows.size(); i++) {
                Element row = individualTableRows.get(i);
                Elements cols = row.select("td");
                Donator item = new Donator();
                item.setName(cols.get(0).html());

                if (isEmptyColValue(cols.get(0).html())) {
                    continue;
                }
                item.setAmount(getNormalizedDecimal(cols.get(1).html()));
                item.setCurrency("AZN");
                item.setTypes(PersonTypes.INDIVIDUAL);


                donators.add(item);
            }


            Element foreignIndividualTable = doc.select(foreignIndividualsSelector).get(0);
            Elements foreignIndividualTableRows = foreignIndividualTable.select("tr");

            for (int i = 0; i < foreignIndividualTableRows.size(); i++) {
                Element row = foreignIndividualTableRows.get(i);
                Elements cols = row.select("td");

                Donator item = new Donator();
                item.setName(cols.get(0).html());


                String amount = cols.get(1).html();
                List<String> list = Arrays.asList(amount.split(" "));

                BigDecimal value = getNormalizedDecimal((list.get(0)));

                String currency = list.get(1);


                item.setAmount(value);
                item.setCurrency(getNormalizedCurrency(currency));
                item.setTypes(PersonTypes.INDIVIDUAL);
//int qaytarir axi goreh
                donators.add(item);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static boolean isEmptyColValue(String value) {
        if (value.equals("&nbsp;") || value.isEmpty()) {
            return true;
        }
        return false;
    }

    public static BigDecimal getNormalizedDecimal(String value) {
        value = value.trim();
        value = value.replaceAll(",", "");
        value = value.replaceAll(" ", "");
        value = value.replaceAll("&nbsp;", "");
        return new BigDecimal(value);
    }
    public static String getNormalizedCurrency(String currency){

        currency = currency.replaceAll("&nbsp;", "");
        return currency;
    }

    public static List<Donator> getDonators() {
        return donators;
    }
}
