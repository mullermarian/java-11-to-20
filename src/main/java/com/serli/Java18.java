package com.serli;

import com.sun.net.httpserver.SimpleFileServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.net.spi.InetAddressResolver;
import java.net.spi.InetAddressResolverProvider;
import java.nio.file.Path;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Stream;

public class Java18 {

    public Java18() throws Exception {

        // Simple Web Server
        var server = SimpleFileServer.createFileServer(
            new InetSocketAddress(8080),
            Path.of(System.getProperty("user.home")),
            SimpleFileServer.OutputLevel.VERBOSE
        );
        server.start();

        // InetAddress Resolver Provider SPI
        // META-INF/services/java.net.spi.InetAddressResolverProvider = com.serli.MyDNSResolverProvider
        InetAddress address = InetAddress.getByName("www.example.com");
        System.out.println(address.getHostAddress());
        address = InetAddress.getByName("www.mondomaine.fr");
        System.out.println(address.getHostAddress());
        
    }
}

class MyDNSResolverProvider extends InetAddressResolverProvider {

    @Override
    public InetAddressResolver get(Configuration config) {
        return new MyDNSResolver();
    }

    @Override
    public String name() {
        return "MyDNSResolverProvider";
    }
    
}

class MyDNSResolver implements InetAddressResolver {

    @Override
    public Stream<InetAddress> lookupByName(String name, LookupPolicy policy) throws UnknownHostException {
        if (name.endsWith("mondomaine.fr")) {
            return Stream.of(InetAddress.getByAddress(new byte[] { 10, 0, 0, 1}));
        }
        return Stream.empty();
    }

    @Override
    public String lookupByAddress(byte[] addr) throws UnknownHostException {
        throw new UnsupportedOperationException("Unimplemented method 'lookupByAddress'");
    }

}

