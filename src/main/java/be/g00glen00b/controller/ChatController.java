package be.g00glen00b.controller;

import java.util.Date;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import be.g00glen00b.dto.*;

@Controller
@RequestMapping("/")
public class ChatController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MessageSendingOperations<String> messageSendingOperations;

    @RequestMapping(method = RequestMethod.GET)
    public String viewApplication() {
        return "index";
    }

    @MessageMapping("/ws/chat")
    @SendTo("/topic/message")
    public OutputMessage sendMessage(Message message) {
        logger.info("Message sent");
        return new OutputMessage(message, new Date());
    }

    @RequestMapping("/broadcast")
    @ResponseBody
    String broadcast(){
        messageSendingOperations.convertAndSend("/topic/message", new OutputMessage(new Message(100, "Hello there. I'm anywhere"), new Date()));
        return "broadcast sent";
    }
}
