package fr.univtln.bruno.samples.jee91.wsclient;

import jakarta.websocket.*;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;


@ClientEndpoint
@Slf4j
public class WsClient {
    Session userSession = null;
    private MessageHandler messageHandler;
    private Map<String, Object> properties;

    public WsClient(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            var url = getClass().getClassLoader().getResource("mycert-pub.jks");
            System.getProperties().put("javax.net.debug", "ssl");
            if (url != null) {
                String pathtoCert = url.toURI().getPath();
                System.getProperties().put(SSLContextConfigurator.KEY_STORE_FILE, pathtoCert);
                System.getProperties().put(SSLContextConfigurator.KEY_STORE_TYPE, "JKS");
                System.getProperties().put(SSLContextConfigurator.TRUST_STORE_FILE, pathtoCert);
                System.getProperties().put(SSLContextConfigurator.TRUST_STORE_TYPE, "JKS");
                System.getProperties().put(SSLContextConfigurator.KEY_STORE_PASSWORD, "storepass");
                System.getProperties().put(SSLContextConfigurator.TRUST_STORE_PASSWORD, "storepass");
            }
            final SSLContextConfigurator defaultConfig = new SSLContextConfigurator();
            defaultConfig.retrieve(System.getProperties());
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {

            final WsClient clientEndPoint = new WsClient(new URI("wss://localhost:8181/wsApp/hellows"));
            clientEndPoint.sendMessage("Hello 1 !");
            Thread.sleep(5000);
            clientEndPoint.sendMessage("Hello 2 !");
            Thread.sleep(5000);

        } catch (InterruptedException ex) {
            log.error("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            log.error("URISyntaxException exception: " + ex.getMessage());
        }
    }

    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.properties = config.getUserProperties();
        this.userSession = session;
        log.info(properties.toString());
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        log.info("Bye");
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("Received message: " + message);
    }

    @OnError
    public void onError(Session session, Throwable t) {
        log.error(t.getMessage());
    }
}