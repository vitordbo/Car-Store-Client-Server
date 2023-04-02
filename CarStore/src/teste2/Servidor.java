package teste2;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import teste2.imple.CarroImpl;
import teste2.imple.LojaImpl;
import teste2.interfaces.Loja;

public class Servidor {
    
    //CarroImpl carroObj = new CarroImpl(null, null, null, null, 0, 0, false, 0)

    public Servidor() throws RemoteException {
        LojaImpl lojaObjeto = new LojaImpl();

    }

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

        try {
            Servidor refObjetoRemoto = new Servidor();

            Loja skeleton = (Loja) UnicastRemoteObject.exportObject( (Remote) refObjetoRemoto, 0);

            LocateRegistry.createRegistry( Registry.REGISTRY_PORT ); 

            Registry registro = LocateRegistry.getRegistry();

            registro.bind("Loja", skeleton);
            System.err.println("Servidor pronto:");
        
        }catch (Exception e) {
            System.err.println("Servidor: " + e.toString());
            e.printStackTrace();
        }
    }

}
