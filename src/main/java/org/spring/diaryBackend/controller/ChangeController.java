package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/change")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChangeController {
}
