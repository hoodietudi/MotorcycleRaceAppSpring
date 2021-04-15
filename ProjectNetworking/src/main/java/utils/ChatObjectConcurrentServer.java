package utils;//package utils;
//
//
//import ChatClientObjectWorker;
//import chat.services.IChatServices;
//import srv.IService;
//
//import java.net.Socket;
//
//
//public class ChatObjectConcurrentServer extends AbsConcurrentServer {
//    private IService chatServer;
//    public ChatObjectConcurrentServer(int port, IService chatServer) {
//        super(port);
//        this.chatServer = chatServer;
//        System.out.println("Chat- ChatObjectConcurrentServer");
//    }
//
//    @Override
//    protected Thread createWorker(Socket client) {
//        ChatClientObjectWorker worker=new ChatClientObjectWorker(chatServer, client);
//        Thread tw=new Thread(worker);
//        return tw;
//    }
//
//
//}
