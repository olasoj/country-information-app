package com.example.countryinformationapplication.client.socketfactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class NetworkSocketFactory extends SSLSocketFactory {
    private final SSLSocketFactory originalSSLSocketFactory;

    public NetworkSocketFactory() throws KeyManagementException, NoSuchAlgorithmException{
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, null, null);
        originalSSLSocketFactory = context.getSocketFactory();
    }

    public NetworkSocketFactory(TrustManager[] tm) throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tm, new java.security.SecureRandom());
        originalSSLSocketFactory = context.getSocketFactory();
    }
    public NetworkSocketFactory(KeyManager[] keyManagers , TrustManager[] tm) throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(keyManagers, tm, null);
        originalSSLSocketFactory = context.getSocketFactory();
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return originalSSLSocketFactory.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return originalSSLSocketFactory.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket() throws IOException {
        return enableTLSOnSocket(originalSSLSocketFactory.createSocket());
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return enableTLSOnSocket(originalSSLSocketFactory.createSocket(s, host, port, autoClose));
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException{
        return enableTLSOnSocket(originalSSLSocketFactory.createSocket(host, port));
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
        return enableTLSOnSocket(originalSSLSocketFactory.createSocket(host, port, localHost, localPort));
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return enableTLSOnSocket(originalSSLSocketFactory.createSocket(host, port));
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return enableTLSOnSocket(originalSSLSocketFactory.createSocket(address, port, localAddress, localPort));
    }

    private Socket enableTLSOnSocket(Socket socket) {
        if(socket instanceof SSLSocket) {
            //Create list of supported protocols

            ArrayList<String> supportedProtocols = new ArrayList<>();
            for (String protocol : ((SSLSocket)socket).getSupportedProtocols()) {
                if (protocol.toUpperCase().contains("TLS")) supportedProtocols.add(protocol);
            }

            //Force add TLSv1.1, 1.2 and 1.3 if not already added
            if (!supportedProtocols.contains("TLSv1.1")) supportedProtocols.add("TLSv1.1");

            if (!supportedProtocols.contains("TLSv1.2")) supportedProtocols.add("TLSv1.2");

            if (!supportedProtocols.contains("TLSv1.3")) supportedProtocols.add("TLSv1.3");


            String[] protocolArray = supportedProtocols.toArray(new String[0]);

            //enable protocols in our list
            ((SSLSocket)socket).setEnabledProtocols(protocolArray);
        }
        return socket;
    }
}
