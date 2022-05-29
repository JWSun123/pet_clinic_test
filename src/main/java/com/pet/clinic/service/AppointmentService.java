package com.pet.clinic.service;

import com.pet.clinic.entity.Appointment;
import com.pet.clinic.exception.RecordNotFoundException;
import com.pet.clinic.repository.AppointmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAllAppointment() {
        return appointmentRepository.findAll(Sort.by(Sort.Direction.ASC, "appointmentDate"));
    }

    public void saveOrUpdateAppointment(Appointment newAppointment) throws RecordNotFoundException {
        if(newAppointment.getId() == null){
            appointmentRepository.save(newAppointment);
        }
        else{
            Appointment appointmentFromDb = getAppointmentById(newAppointment.getId());
            appointmentFromDb.setAppointmentDate(newAppointment.getAppointmentDate());
            appointmentFromDb.setDescription(newAppointment.getDescription());
            appointmentFromDb.setPet(newAppointment.getPet());
            appointmentRepository.save(appointmentFromDb);
        }
    }

    public Appointment getAppointmentById(Long id) throws RecordNotFoundException {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(appointment.isPresent()) return appointment.get();
        throw new RecordNotFoundException("Appointment Not Found");
    }

    public void cancelAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }


    public List<Appointment> getAppointmentByDate(String date){
        return appointmentRepository.findByAppointmentDate(date);
    }
}
