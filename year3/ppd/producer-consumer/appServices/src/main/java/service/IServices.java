package service;

import java.rmi.RemoteException;
import java.util.List;

public interface IServices {
    boolean cumpara(Long idSpectacol, List<Integer> listaBilete) throws RemoteException;
}
