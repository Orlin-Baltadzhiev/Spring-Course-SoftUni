import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    private static final String GRINGOTTS_PU= "gringotts";
    private static final String SALES_PU= "sales";
    private static final String UNIVERSITY_SYSTEM = "university_system";
    private static final String BILLS_PAYMENT_SYSTEM = "bills_payment_system";
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory(GRINGOTTS_PU);

      EntityManager entityManager = entityManagerFactory.createEntityManager();
      Engine engine = new Engine(entityManager);

        engine.run();

    }
}
