package com.nguonhour.hnh.controller;

import com.nguonhour.hnh.model.Template;
import com.nguonhour.hnh.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping
    public List<Template> getAll() {
        return templateService.findAll();
    }

    @PostMapping
    public Template create(@RequestBody Template template) {
        return templateService.create(template);
    }

    @PutMapping("/{id}")
    public Template update(@PathVariable Long id, @RequestBody Template template) {
        return templateService.update(id, template);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        templateService.delete(id);
        return "Template deleted";
    }
}