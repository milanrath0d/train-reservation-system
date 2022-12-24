package com.milan.reservation.controller;

import com.milan.reservation.model.Class;
import com.milan.reservation.request.AddClassRequest;
import com.milan.reservation.service.ClassService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/class")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping
    public void addClass(@RequestBody AddClassRequest addClassRequest) {
        final Class classData = Class.builder()
            .classCode(addClassRequest.getClassCode())
            .className(addClassRequest.getClassName())
            .build();

        classService.add(classData);
    }

    @GetMapping("/getAll")
    public List<Class> getAll() {
        return classService.getAll();
    }
}
