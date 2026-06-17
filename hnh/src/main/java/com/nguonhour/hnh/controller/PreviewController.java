package com.nguonhour.hnh.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.nguonhour.hnh.service.ProfileService;
import com.nguonhour.hnh.model.Profile;

@Controller
@RequiredArgsConstructor
public class PreviewController {

    private final ProfileService profileService;

    @GetMapping("/preview/{id}")
    public String preview(@PathVariable Long id, Model model) {
        Profile profile = profileService.getById(id);
        model.addAttribute("profile", profile);
        return "preview";
    }
}
