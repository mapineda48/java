package ortopedic.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ortopedic.entity.Message;
import ortopedic.service.MessageService;

@RestController
@RequestMapping("/api/Message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/all")
    public List<Message> getAll() {
        return messageService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Message> getById(@PathVariable("id") Integer id) {
        return messageService.getMessage(id);
    }

    @PostMapping("/save")
    public Message save(@RequestBody Message message) {
        return messageService.save(message);
    }

    @PutMapping("/update")
    public Message update(@RequestBody Message message) {
        return messageService.update(message);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Integer id) {
        return messageService.delete(id);
    }

}
