import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import implementacoes.LojaImpl;
import interfaces.Loja;

public class Servidor {
    public static void main(String[] args) {

        try {
            
            LojaImpl object = new LojaImpl();

            Loja sketleton = (Loja) UnicastRemoteObject.exportObject(object, 0);

            LocateRegistry.createRegistry(20002);

            Registry resgistry = LocateRegistry.getRegistry(20002);
            resgistry.rebind("Loja", sketleton);

            System.out.println("Server Init");
        } catch (Exception e) {
            System.err.println("Servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}

    
