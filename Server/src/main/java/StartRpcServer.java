import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartRpcServer {
    //private static int defaultPort=55555;
    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        /*Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/chatserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }
        OficiuDBRepository oficiuDBRepository = new OficiuDBRepository(serverProps);
        ParticipantDBRepository participantDBRepository = new ParticipantDBRepository(serverProps);
        ProbaDBRepository probaDBRepository = new ProbaDBRepository(serverProps);
        InscriereDBRepository inscriereDBRepository = new InscriereDBRepository(serverProps);


        IService chatServerImpl=new Service(oficiuDBRepository,participantDBRepository,probaDBRepository,inscriereDBRepository);
        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);

        AbstractServer server = new ChatRpcConcurrentServer(chatServerPort, chatServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }*/
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring.xml");
    }
}
