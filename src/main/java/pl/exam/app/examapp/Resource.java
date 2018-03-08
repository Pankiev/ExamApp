package pl.exam.app.examapp;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

@PushEndpoint("/lobby")
public class Resource
{
	@OnMessage(encoders = { JSONEncoder.class })
    public String onMessage(String message) {
        System.out.println("Handling message: " + message);
        return message;
    }
}
