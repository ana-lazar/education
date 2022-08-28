package services;

import domain.Participant;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MotorcycleObserver extends Remote {
    void registeredParticipant(Participant participant) throws RemoteException;
}
