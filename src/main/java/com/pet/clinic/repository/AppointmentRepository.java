package com.pet.clinic.repository;

import com.pet.clinic.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    //TODO: Page pagable.


//    @Query(value = "select * from appointments where appointment_date = :appointmentDate order by appointment_date", nativeQuery = true)
//    List<Appointment> findByAppointmentDate(@Param("appointmentDate") Date appointmentDate);
}
