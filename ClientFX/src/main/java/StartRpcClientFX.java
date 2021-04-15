import controller.LoginController;
import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import srv.IService;

import java.io.IOException;


public class StartRpcClientFX extends Application {
    //private Stage primaryStage;

    //private static int defaultChatPort = 55555;
    //private static String defaultServer = "localhost";


    public void start(Stage primaryStage) throws IOException {
//        System.out.println("In start");
//        Properties clientProps = new Properties();
//        try {
//            clientProps.load(StartRpcClientFX.class.getResourceAsStream("/client.properties"));
//            System.out.println("Client properties set. ");
//            clientProps.list(System.out);
//        } catch (IOException e) {
//            System.err.println("Cannot find client.properties " + e);
//            return;
//        }
//        String serverIP = clientProps.getProperty("server.host", defaultServer);
//        int serverPort = defaultChatPort;
//
//        try {
//            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
//        } catch (NumberFormatException ex) {
//            System.err.println("Wrong port number " + ex.getMessage());
//            System.out.println("Using default port: " + defaultChatPort);
//        }
//        System.out.println("Using server IP " + serverIP);
//        System.out.println("Using server port " + serverPort);
//
//        IService server = new ChatServicesRpcProxy(serverIP, serverPort);

        try {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:client-spring.xml");
        IService server=(IService)factory.getBean("service");
        System.out.println("Obtained a reference to remote project server");


        //FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("loginView.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginView.fxml"));
        Parent root=loader.load();

        LoginController ctrl = loader.<LoginController>getController();
        ctrl.setService(server);

        //FXMLLoader cloader = new FXMLLoader(getClass().getClassLoader().getResource("mainView.fxml"));
        FXMLLoader cloader = new FXMLLoader(getClass().getResource("mainView.fxml"));
        Parent croot=cloader.load();


        MainController mainCtrl = cloader.getController();

        ctrl.setMainController(mainCtrl);
        ctrl.setParent(croot);

        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        }
        catch (Exception e) {
                System.err.println("Project Initialization  exception:"+e);
                e.printStackTrace();
        }


    }



}


