package com.milan.reservation.service;

import com.milan.reservation.model.Class;
import com.milan.reservation.repository.ClassRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Milan Rathod
 */
@Service
public class ClassService {

    private final ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public void add(Class classData) {
        classRepository.save(classData);
    }

    public List<Class> getAll() {
        return classRepository.findAll();
    }

    public Class findByClassCode(String classCode) {
        return classRepository.findByClassCode(classCode)
            .orElse(null);
    }
}
