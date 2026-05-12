package com.didarovamedina.university.service;

import com.didarovamedina.university.dto.*;
import com.didarovamedina.university.entity.DidarovaMedinaTeacher;
import com.didarovamedina.university.exception.ResourceNotFoundException;
import com.didarovamedina.university.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DidarovaMedinaTeacherService {

    private final TeacherRepository teacherRepository;

    public List<TeacherDTO> getAll() {
        return teacherRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public TeacherDTO getById(Long id) {
        return toDTO(teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + id)));
    }

    public TeacherDTO create(TeacherRequestDTO dto) {
        DidarovaMedinaTeacher t = DidarovaMedinaTeacher.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .department(dto.getDepartment())
                .build();
        log.info("Creating teacher: {}", dto.getFirstName());
        return toDTO(teacherRepository.save(t));
    }

    public TeacherDTO update(Long id, TeacherRequestDTO dto) {
        DidarovaMedinaTeacher t = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + id));
        t.setFirstName(dto.getFirstName());
        t.setLastName(dto.getLastName());
        t.setDepartment(dto.getDepartment());
        return toDTO(teacherRepository.save(t));
    }

    public void delete(Long id) { teacherRepository.deleteById(id); }

    private TeacherDTO toDTO(DidarovaMedinaTeacher t) {
        return TeacherDTO.builder()
                .id(t.getId())
                .firstName(t.getFirstName())
                .lastName(t.getLastName())
                .department(t.getDepartment())
                .build();
    }
}