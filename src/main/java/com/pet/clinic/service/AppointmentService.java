package com.pet.clinic.service;

import com.pet.clinic.entity.Appointment;
import com.pet.clinic.repository.AppointmentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAllAppointment() {
        return appointmentRepository.findAll(Sort.by(Sort.Direction.ASC, "appointment_date"));
    }

    public List<Appointment> getAppointmentByDate(Date date){
        return appointmentRepository.findByDate(date);
    }
}
