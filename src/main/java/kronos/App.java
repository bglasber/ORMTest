package kronos;

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
            Event e = new Event("test");
            System.out.println("Going to write event");
            api.writeEvent( e );
            System.out.println("wrote event");
            api.tearDown();
        } catch( Exception e ) {
            System.out.println( e.getMessage() );
        }
    }
}
