package pl.exam.app.examapp.controllers.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import pl.exam.app.examapp.socket.model.UpdateLobbyMessage;

@Controller
public class LobbyController
{
	@MessageMapping("/lobby/update")
    @SendTo("/lobby/update")
    public UpdateLobbyMessage greeting(UpdateLobbyMessage message) {
        return new UpdateLobbyMessage();
    }
}
