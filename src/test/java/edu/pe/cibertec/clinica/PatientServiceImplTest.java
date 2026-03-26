package edu.pe.cibertec.clinica;

import edu.pe.cibertec.clinica.model.Patient;
import edu.pe.cibertec.clinica.repository.PatientRepository;
import edu.pe.cibertec.clinica.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PatientServiceImpl - Unit test with Mockito")
public class PatientServiceImplTest {

    @Mock
    private PatientRepository repository;

    @InjectMocks
    private PatientServiceImpl service;

    // getAllPatients;
    @Test
    @DisplayName("Return all patientes from repository")
    void givenPatienteExist_whenGetAllPatients_thenReturnList(){
        List<Patient> patients = List.of(
                new Patient(1L, "Paula Perez", "12345678", "987654321", "pperez@mail.com"),
                new Patient(2L, "Luis Ramos", "87654321", "912345678", "luisram@gmail.com")
        );

        when(repository.findAll()).thenReturn(patients);

        List<Patient> result = service.getAllPatients();

        assertEquals(2, result.size());

        verify(repository, times(1)).findAll();


    }

    ///  getPatientById
    @Test
    @DisplayName("Returns patient when ID exists")
    void givenExistingId_whenGetPatientById_thenReturnPatient(){
        Patient patient = new Patient(1L, "Marco Sifuentes", "87654321", "912345678", "msfiuentes@gmail.com");

        when(repository.findById(1L)).thenReturn(Optional.of(patient));

        Patient result = service.getPatientById(1L);

        assertEquals("Marco Sifuentes", patient.getFullName());
        verify(repository, times(1)).findById(1L);

    }

    // Update patient
    @Test
    @DisplayName("Updates patiente when ID exists")
    void givenExistingId_whenUpdatePatient_thenReturnUpdatedPatient(){
        Patient existing =  new Patient(1L, "Ana Torres", "12345678", "987654321", "ana@mail.com");

        Patient updated = new Patient(1L, "Ana Torres Ruiz", "12345678", "999999999", "anamailnuevo@mail.com");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(updated);

        Patient result = service.updatePatient(1L, updated);

        assertEquals("Ana Torres Ruiz", result.getFullName());
        assertEquals("999999999", result.getPhone());
        assertEquals("anamailnuevo@mail.com", result.getEmail());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(existing);


    }

}
