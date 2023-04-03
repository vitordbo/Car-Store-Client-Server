package teste2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import teste2.imple.LojaImpl;
import teste2.interfaces.Loja;

public class Servidor4 {
    public static void main(String args[]) {
        try {
            LojaImpl refObjetoRemoto = new LojaImpl();
            Loja stub = (Loja) UnicastRemoteObject.toStub(refObjetoRemoto);
            if (stub == null) {
                stub = (Loja) UnicastRemoteObject.exportObject(refObjetoRemoto, 0);
            }
            
            LocateRegistry.createRegistry(20003); 
            Registry registro = LocateRegistry.getRegistry(20003);
            registro.bind("Loja", stub);
            
            System.out.println("Servidor pronto:");
        } catch (Exception e) {
            System.err.println("Servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}
