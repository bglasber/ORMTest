package kronos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.HibernateException;
import java.util.List;
import java.util.Random;


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

    public void writeBook( Book b ) throws Exception {
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
        session.save( b );
        session.getTransaction().commit();
        System.out.println("Committed transaction");
        session.close();
        System.out.println("Closed Session");
    }

    public void writeAuthor( Author a ) throws Exception {
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
        session.save( a );
        session.getTransaction().commit();
        System.out.println("Committed transaction");
        session.close();
        System.out.println("Closed Session");
    }

    public List<Book> getBooks() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
        } catch( HibernateException ex ) {
            System.out.println( ex );
        }

        System.out.println("Began session");
        session.beginTransaction();
        System.out.println("Began transaction");
        List<Book> results = session.createQuery( "from Book" ).list();
        session.getTransaction().commit();
        System.out.println("Committed transaction");
        session.close();
        System.out.println("Closed Session");
        return results;
    }

    public Book getNextBook() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
        } catch( HibernateException ex ) {
            System.out.println( ex );
        }

        System.out.println("Began session");
        session.beginTransaction();
        System.out.println("Began transaction");
        Random rand = new Random();
        long next_id = rand.nextInt( 1000 );

        //Load two books
        Book b = (Book) session.get( Book.class, next_id );
        System.out.println("First lookup done");
        next_id++;
        Book b2 = (Book) session.get( Book.class, next_id );
        System.out.println("Second lookup done");

        session.getTransaction().commit();

        System.out.println("Committed transaction");
        session.close();
        System.out.println("Closed Session");
        return b2;
    }

    public List<Book> getAuthorOneBooks() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
        } catch( HibernateException ex ) {
            System.out.println( ex );
        }

        System.out.println("Began session");
        session.beginTransaction();
        System.out.println("Began transaction");

        Author auth = (Author) session.get( Author.class, 1L );
        System.out.println("Author lookup done");

        List<Book> books = auth.getBooks();
        System.out.println("Done getBooks().");
        session.getTransaction().commit();

        System.out.println("Committed transaction");

        //Initialize Books Now
        System.out.println("I've retrieved books: " + books.size() );

        session.close();
        System.out.println("Closed Session");

        return books;
    }

    public void tearDown() throws Exception {
        if( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

}
