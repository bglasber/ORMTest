package kronos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.HibernateException;


public class ORMApi {
    private SessionFactory sessionFactory;

    public ORMApi() {

    }

    public void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );

            System.out.println(e);
        }
    }

    public void writeEvent( Event e ) throws Exception {
        System.out.println("Beginning session");
        Session session = null;
        try {
            session = sessionFactory.openSession();
        } catch( HibernateException ex ) {
            System.out.println( ex );
        }
        System.out.println("Began session");
        session.beginTransaction();
        System.out.println("Began transaction");
        session.save( e );
        session.getTransaction().commit();
        session.close();
    }

    public void tearDown() throws Exception {
        if( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

}
