package com.didarovamedina.university.service;

import com.didarovamedina.university.dto.*;
import com.didarovamedina.university.entity.*;
import com.didarovamedina.university.exception.ResourceNotFoundException;
import com.didarovamedina.university.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DidarovaMedinaCourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public List<CourseDTO> getAll(String title, Integer credits) {
        if (title != null && !title.isEmpty())
            return courseRepository.findByTitleContainingIgnoreCase(title)
                    .stream().map(this::toDTO).collect(Collectors.toList());
        if (credits != null)
            return courseRepository.findByCredits(credits)
                    .stream().map(this::toDTO).collect(Collectors.toList());
        return courseRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CourseDTO getById(Long id) {
        return toDTO(courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id)));
    }

    public CourseDTO create(CourseRequestDTO dto) {
        DidarovaMedinaTeacher teacher = dto.getTeacherId() != null
                ? teacherRepository.findById(dto.getTeacherId()).orElse(null) : null;
        DidarovaMedinaCourse c = DidarovaMedinaCourse.builder()
                .title(dto.getTitle()).description(dto.getDescription())
                .credits(dto.getCredits()).teacher(teacher).build();
        log.info("Creating course: {}", dto.getTitle());
        return toDTO(courseRepository.save(c));
    }

    public CourseDTO update(Long id, CourseRequestDTO dto) {
        DidarovaMedinaCourse c = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id));
        c.setTitle(dto.getTitle());
        c.setDescription(dto.getDescription());
        c.setCredits(dto.getCredits());
        return toDTO(courseRepository.save(c));
    }

    public void delete(Long id) { courseRepository.deleteById(id); }

    private CourseDTO toDTO(DidarovaMedinaCourse c) {
        return CourseDTO.builder()
                .id(c.getId()).title(c.getTitle()).description(c.getDescription())
                .credits(c.getCredits())
                .teacherName(c.getTeacher() != null
                        ? c.getTeacher().getFirstName() + " " + c.getTeacher().getLastName() : null)
                .build();
    }
}