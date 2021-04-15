package srv;

import domain.Participant;
import domain.Race;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IObserver extends Remote {
     void ParticipantAdded(Race race) throws ProjectException, RemoteException;
//     void friendLoggedIn(User friend) throws ChatException;
//     void friendLoggedOut(User friend) throws ChatException;
}
