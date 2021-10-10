package ortopedic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ortopedic.entity.Message;
import ortopedic.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getAll() {
        return messageRepository.getAll();
    }

    public Optional<Message> getMessage(Integer id) {
        return messageRepository.getMessage(id);
    }

    public Message save(Message message) {

        if (message.getId() == null) {
            return messageRepository.save(message);
        }

        Optional<Message> res = messageRepository.getMessage(message.getId());

        if (res.isEmpty()) {
            return messageRepository.save(message);
        } else {
            return message;
        }
    }

    public Message update(Message message) {
        Optional<Message> res = messageRepository.getMessage(message.getId());

        if (!res.isEmpty()) {
            Message record = res.get();

            String messageText = message.getMessageText();

            if (messageText != null) {
                record.setMessageText(messageText);
            }

            messageRepository.save(record);

            return record;
        } else {
            return message;
        }

    }

    public boolean delete(Integer id) {
        Boolean aBoolean = getMessage(id).map(message -> {
            messageRepository.delete(message);
            return true;
        }).orElse(false);

        return aBoolean;
    }

}
