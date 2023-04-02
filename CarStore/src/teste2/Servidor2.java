import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import teste2.imple.CarroImpl;
import teste2.imple.ClienteImpl;
import teste2.imple.FuncionarioImpl;
import teste2.imple.LojaImpl;
import teste2.interfaces.Carro;
import teste2.interfaces.Cliente;
import teste2.interfaces.Funcionario;
import teste2.interfaces.Loja;


public class Servidor2 {
    
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        
        // Criar objetos das implementações das interfaces
        Loja loja = new LojaImpl();
        Carro carro = new CarroImpl();
        Cliente cliente = new ClienteImpl();
        Funcionario funcionario = new FuncionarioImpl();
        
        // Exportar objetos para permitir acesso remoto
        Loja skeletonLoja = (Loja) UnicastRemoteObject.exportObject(loja, 0);
        Carro skeletonCarro = (Carro) UnicastRemoteObject.exportObject(carro, 0);
        Cliente skeletonCliente = (Cliente) UnicastRemoteObject.exportObject(cliente, 0);
        Funcionario skeletonFuncionario = (Funcionario) UnicastRemoteObject.exportObject(funcionario, 0);
        
        // Registrar objetos no Registry
        Registry registro = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        registro.bind("Loja", skeletonLoja);
        registro.bind("Carro", skeletonCarro);
        registro.bind("Cliente", skeletonCliente);
        registro.bind("Funcionario", skeletonFuncionario);
        
        System.out.println("Servidor iniciado.");
    }
}
