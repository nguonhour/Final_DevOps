package com.nguonhour.hnh.service;

import com.nguonhour.hnh.model.Template;
import com.nguonhour.hnh.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;

    public List<Template> findAll() {
        return templateRepository.findAll();
    }

    public Template getById(Long id) {
        return templateRepository.findById(id).orElseThrow();
    }

    public Template create(Template template) {
        return templateRepository.save(template);
    }

    public Template update(Long id, Template template) {
        Template existing = getById(id);

        existing.setName(template.getName());
        existing.setCode(template.getCode());
        existing.setOrganizationName(template.getOrganizationName());
        existing.setLayout(template.getLayout());
        existing.setPrimaryColor(template.getPrimaryColor());
        existing.setSecondaryColor(template.getSecondaryColor());
        existing.setTextColor(template.getTextColor());
        existing.setTagline(template.getTagline());

        return templateRepository.save(existing);
    }

    public void delete(Long id) {
        templateRepository.deleteById(id);
    }
}