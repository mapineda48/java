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

import ortopedic.entity.Ortopedic;
import ortopedic.service.OrtopedicService;

@RestController
@RequestMapping("/api/Ortopedic")
public class OrtopedicController {

    @Autowired
    private OrtopedicService ortopedicService;

    @GetMapping("/all")
    public List<Ortopedic> getAll() {
        return ortopedicService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Ortopedic> getById(@PathVariable("id") Integer id) {
        return ortopedicService.getOrtopedic(id);
    }

    @PostMapping("/save")
    public Ortopedic save(@RequestBody Ortopedic ortopedic) {
        return ortopedicService.save(ortopedic);
    }

    @PutMapping("/update")
    public Ortopedic update(@RequestBody Ortopedic ortopedic) {
        return ortopedicService.update(ortopedic);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Integer id) {
        return ortopedicService.delete(id);
    }

}
