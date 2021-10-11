package ortopedic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ortopedic.entity.Client;
import ortopedic.entity.Message;
import ortopedic.entity.Ortopedic;
import ortopedic.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrtopedicService ortopedicService;

    public List<Message> getAll() {
        return messageRepository.getAll();
    }

    public Optional<Message> getMessage(Integer id) {
        return messageRepository.getMessage(id);
    }

    public Message save(Message message) {

        if (message.getIdMessage() == null) {
            Client client = message.getClient();

            if (client != null) {
                Integer id = client.getIdClient();

                if (id != null) {
                    Optional<Client> res = clientService.getClient(id);

                    if (res.isPresent()) {
                        message.setClient(res.get());
                    }
                }
            }

            Ortopedic ortopedic = message.getOrtopedic();

            if (ortopedic != null) {
                Integer id = ortopedic.getId();

                if (id != null) {
                    Optional<Ortopedic> res = ortopedicService.getOrtopedic(id);

                    if (res.isPresent()) {
                        message.setOrtopedic(res.get());
                    }
                }

            }

            return messageRepository.save(message);
        }

        Optional<Message> res = messageRepository.getMessage(message.getIdMessage());

        if (res.isEmpty()) {
            return messageRepository.save(message);
        } else {
            return message;
        }
    }

    public Message update(Message message) {
        Optional<Message> res = messageRepository.getMessage(message.getIdMessage());

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
