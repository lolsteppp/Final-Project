package com.didarovamedina.university.service;

import com.didarovamedina.university.dto.*;
import com.didarovamedina.university.entity.DidarovaMedinaStudent;
import com.didarovamedina.university.exception.ResourceNotFoundException;
import com.didarovamedina.university.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DidarovaMedinaStudentService {

    private final StudentRepository studentRepository;

    public Page<StudentDTO> getAll(int page, int size, String sortBy, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<DidarovaMedinaStudent> result = (search != null && !search.isEmpty())
                ? studentRepository.findByFirstNameContainingIgnoreCase(search, pageable)
                : studentRepository.findAll(pageable);
        return result.map(this::toDTO);
    }

    public StudentDTO getById(Long id) {
        return toDTO(studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id)));
    }

    public StudentDTO create(StudentRequestDTO dto) {
        DidarovaMedinaStudent s = DidarovaMedinaStudent.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
        log.info("Creating student: {}", dto.getEmail());
        return toDTO(studentRepository.save(s));
    }

    public StudentDTO update(Long id, StudentRequestDTO dto) {
        DidarovaMedinaStudent s = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
        s.setFirstName(dto.getFirstName());
        s.setLastName(dto.getLastName());
        s.setEmail(dto.getEmail());
        return toDTO(studentRepository.save(s));
    }

    public void delete(Long id) { studentRepository.deleteById(id); }

    private StudentDTO toDTO(DidarovaMedinaStudent s) {
        return StudentDTO.builder()
                .id(s.getId())
                .firstName(s.getFirstName())
                .lastName(s.getLastName())
                .email(s.getEmail())
                .build();
    }
}