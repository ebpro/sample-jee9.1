package fr.univtln.bruno.samples.jee91.ws;

import fr.univtln.bruno.samples.jee91.dao.DAO1;
import fr.univtln.bruno.samples.jee91.ejb.Hello;
import fr.univtln.bruno.samples.jee91.ejb.qualifiers.SpokenLanguage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.java.Log;

import java.io.IOException;


@ApplicationScoped
@ServerEndpoint(value = "/hellows")
@Log
public class WSServer {

   @Inject
    @SpokenLanguage(SpokenLanguage.Language.ENGLISH)
    Hello hello;

    @Inject
    DAO1 dao1;

    @OnOpen
    public void onOpen(Session session) throws EncodeException, IOException {
        System.out.println("WebSocket opened: " + session.getId());
        log.info("Connection opened : ");
        session.getBasicRemote().sendObject(dao1.getMetadata().toString());
        session.getBasicRemote().sendText("HELLO FROM JAKARTA !");
    }

    @OnMessage
    public void onMessage(Session session,
                          String message) throws IOException {
        session.getBasicRemote().sendText("Echo :" + message);
    }
}

