package kronos;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        ORMApi api = new ORMApi();
        try {
            api.setUp();

            Author auth = new Author("brad");
            api.writeAuthor( auth );
            Book b = new Book("test", auth);
            System.out.println("Going to write event");
            for( int i = 0; i < 5; i++ ) {
                api.writeBook( b );
            }

            System.out.println("wrote event");
            List<Book> books = api.getAuthorOneBooks();

            api.tearDown();
            System.out.println("finished teardown");

        } catch( Exception e ) {
            System.out.println( e.getMessage() );
        }
    }
}
