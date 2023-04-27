package com.serli;

import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Java16 {

    public Java16() throws Exception {

        // Records
        var emp = new Employe();
        System.out.println(emp);
        System.out.println(emp.hashCode());
        var emp1 = new Employe();
        var emp2 = new Employe();
        System.out.println(emp1==emp2);
        System.out.println(emp1.equals(emp2));
        
        List<Etudiant> meilleurs = getMeilleursEtudiants(
            List.of(
                new Etudiant("Nom1", "prenom1", 1),
                new Etudiant("Nom2", "prenom2", 1),
                new Etudiant("Nom3", "prenom3", 2),
                new Etudiant("Nom4", "prenom4", 3)
            )
        );
        System.out.println(meilleurs);
        
        
        // Pattern matching for instanceof
        Vehicle vehicle1 = new Car("a car model");
        Vehicle vehicle2 = new Plane(300);
        if (vehicle1 instanceof Car c) {
            System.out.println("Car.model: " + c.model);
        }
        if (vehicle2 instanceof Plane p) {
            System.out.println("Plane.maxSpeed: " + p.maxSpeed);
        }

        
        // UNIX sockets
        Files.deleteIfExists(Path.of(System.getProperty("user.home")).resolve("mysocket.socket"));
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocketChannel serverChannel = ServerSocketChannel.open(StandardProtocolFamily.UNIX);
                    serverChannel.bind(UnixDomainSocketAddress.of(Path.of(System.getProperty("user.home")).resolve("mysocket.socket")));
                    System.out.println("waiting for connection");
                    SocketChannel channel = serverChannel.accept();
                    System.out.println("waiting for message");
                    while (true) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int bytesRead = channel.read(buffer);
                        if (bytesRead > 0) {
                            byte[] bytes = new byte[bytesRead];
                            buffer.flip();
                            buffer.get(bytes);
                            String message = new String(bytes);
                            System.out.printf("[Client message] %s", message);
                            break;
                        }
                        Thread.sleep(100);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread.sleep(500);
        SocketChannel clientChannel = SocketChannel.open(StandardProtocolFamily.UNIX);
        clientChannel.connect(UnixDomainSocketAddress.of(Path.of(System.getProperty("user.home")).resolve("mysocket.socket")));
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        buffer.put("My message".getBytes());
        buffer.flip();
        while (buffer.hasRemaining()) {
            clientChannel.write(buffer);
        }
        executorService.shutdown();

        
        // Stream.toList()
        List<Personne> personnes = List.of(
            new Personne("Alice", 25),
            new Personne("Bob", 45),
            new Personne("Eve", 37)
        );
        List<String> personnesFormattees = personnes.stream()
            .map(p -> p.formatted())
            .toList();
        System.out.println(personnesFormattees);
        
    }

    static List<Etudiant> getMeilleursEtudiants(List<Etudiant> etudiants) {

        record EtudiantMoyenne(Etudiant etudiant, double moyenne){};

        return etudiants.stream()
            .map(e->new EtudiantMoyenne(e,calculerMoyenne(e)))
            .peek(System.out::println)
            .sorted((e1,e2)->Double.compare(e2.moyenne(),e1.moyenne()))
            .map(EtudiantMoyenne::etudiant)
            .collect(Collectors.toList());
    }

    static double calculerMoyenne(Etudiant e) {
        return ThreadLocalRandom.current().nextDouble(0, 20);
    }
}

record Employe() {
}

record Etudiant(String nom, String prenom, int groupe) {
}

class Vehicle {}

class Car extends Vehicle {
    String model;
    public Car(String model) {
        this.model = model;
    }
}

class Plane extends Vehicle {
    int maxSpeed;
    public Plane(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}

record Personne(String nom, int age) {
    String formatted() {
        return nom + " (" + age + ")";
    }
}
