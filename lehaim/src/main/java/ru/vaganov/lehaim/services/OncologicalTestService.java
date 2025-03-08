package ru.vaganov.lehaim.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vaganov.lehaim.exceptions.OncologicalTestExistsException;
import ru.vaganov.lehaim.exceptions.OncologicalTestNotFoundException;
import ru.vaganov.lehaim.exceptions.ParameterNotFoundException;
import ru.vaganov.lehaim.exceptions.PatientNotFoundException;
import ru.vaganov.lehaim.mappers.OncologicalTestMapper;
import ru.vaganov.lehaim.mappers.ParameterResultMapper;
import ru.vaganov.lehaim.models.OncologicalTest;
import ru.vaganov.lehaim.models.Parameter;
import ru.vaganov.lehaim.models.ParameterResult;
import ru.vaganov.lehaim.patient.entity.Patient;
import ru.vaganov.lehaim.dto.oncotests.OncologicalTestDTO;
import ru.vaganov.lehaim.dto.oncotests.OncologicalTestRestDTO;
import ru.vaganov.lehaim.dto.ParameterResultDTO;
import ru.vaganov.lehaim.dto.ParameterResultRestDTO;
import ru.vaganov.lehaim.repositories.CatalogRepository;
import ru.vaganov.lehaim.repositories.OncologicalTestRepository;
import ru.vaganov.lehaim.repositories.ParameterResultRepository;
import ru.vaganov.lehaim.repositories.PatientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OncologicalTestService {

    private final OncologicalTestRepository oncologicalTestRepository;

    private final OncologicalTestMapper testMapper;

    private final PatientRepository patientRepository;

    private final ParameterResultRepository resultRepo;

    private final ParameterResultMapper resultMapper;

    private final CatalogRepository catalogRepository;


    public List<OncologicalTestDTO> getAllTestByPatientId(UUID patientId) {
        return testMapper.toDto(oncologicalTestRepository.findByPatientOwner_IdOrderByTestDateDesc(patientId));
    }

    public void deleteTestById(Long testId) {
        oncologicalTestRepository.deleteById(testId);
    }

    @Transactional
    public OncologicalTestRestDTO saveNewOncologicalTest(UUID ownerId, OncologicalTestRestDTO dto) {
        Patient patient = patientRepository.findById(ownerId)
                .orElseThrow(() -> new PatientNotFoundException(ownerId));
        if (oncologicalTestRepository.findByPatientOwner_IdAndTestDate(ownerId, dto.getTestDate()).isPresent())
            throw new OncologicalTestExistsException(ownerId, dto.getTestDate());

        OncologicalTest newTest = new OncologicalTest();
        newTest.setTestDate(dto.getTestDate());
        newTest.setTestNote(dto.getTestNote());
        newTest.setPatientOwner(patient);
        newTest = oncologicalTestRepository.save(newTest);

        List<ParameterResult> results = new ArrayList<>();
        for (ParameterResultRestDTO resDTO : dto.getResults()) {
            Parameter parameter = catalogRepository.findById(resDTO.getCatalogId())
                    .orElseThrow(() -> new ParameterNotFoundException(resDTO.getCatalogId()));

            ParameterResult result = ParameterResult.builder()
                    .attachedTest(newTest)
                    .value(resDTO.getValue())
                    .parameter(parameter).build();
            results.add(resultRepo.save(result));
        }
        return testMapper.toRestDto(newTest.getId(), newTest.getTestDate(), newTest.getTestNote(), results);
    }

    public List<ParameterResultDTO> getAllResultsByTestId(Long testId) {
        return resultMapper.toDto(resultRepo.findByAttachedTest_Id(testId));
    }

    @Transactional
    public OncologicalTestRestDTO updateOncologicalTest(
            UUID ownerId, Long testId, OncologicalTestRestDTO requestDTO) {
        OncologicalTest test = oncologicalTestRepository.findById(testId)
                .orElseThrow(() -> new OncologicalTestNotFoundException(testId));
        Optional<OncologicalTest> otherTestByDate =
                oncologicalTestRepository.findByPatientOwner_IdAndTestDate(ownerId, requestDTO.getTestDate());
        if (requestDTO.getTestDate() != null) {
            if (otherTestByDate.isPresent() && !otherTestByDate.get().getId().equals(testId))
                throw new OncologicalTestExistsException(ownerId, requestDTO.getTestDate());

            test.setTestDate(requestDTO.getTestDate());
            if(requestDTO.getTestNote() != null){
                test.setTestNote(requestDTO.getTestNote());
            }
            test = oncologicalTestRepository.save(test);
        }

        for (ParameterResultRestDTO dto : requestDTO.getResults()) {
            ParameterResult result = resultRepo.findByAttachedTest_PatientOwner_IdAndAttachedTest_IdAndParameter_Id(
                            ownerId, testId, dto.getCatalogId())
                    .orElseGet(() -> {
                        Parameter parameter = catalogRepository.findById(dto.getCatalogId())
                                .orElseThrow(() -> new ParameterNotFoundException(dto.getCatalogId()));
                        return ParameterResult.builder()
                                .parameter(parameter)
                                .build();
                    });
            if (result.getAttachedTest() == null)
                result.setAttachedTest(test);
            result.setValue(dto.getValue());
            resultRepo.save(result);

        }
        List<ParameterResult> results = resultRepo.findByAttachedTest_Id(testId);
        return testMapper.toRestDto(test.getId(), test.getTestDate(), test.getTestNote(), results);
    }

    public OncologicalTestRestDTO findOncologicalTestById(UUID patientId, Long testId) {
        OncologicalTest test = oncologicalTestRepository.findById(testId)
                .orElseThrow(() -> new OncologicalTestNotFoundException(testId));

        List<ParameterResult> results = resultRepo.findByAttachedTest_Id(testId);
        return testMapper.toRestDto(test.getId(), test.getTestDate(), test.getTestNote(), results);
    }
}
