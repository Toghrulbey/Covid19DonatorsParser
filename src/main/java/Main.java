import hibernate.test.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {


    private static List<Donator> localLegalItems = new ArrayList<>();
    private static List<Donator> localIndividualItems = new ArrayList<>();

    private static List<Donator> externalLegalItems = new ArrayList<>();
    private static List<Donator> externalIndividualItems = new ArrayList<>();


    public static void main(String[] args) {

        JsoupParser.parse();
        List<Donator> list = JsoupParser.getDonators();
        list.forEach(System.out::println);


        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            for (Donator donator : list) {
                session.save(donator);

            }
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }


}
