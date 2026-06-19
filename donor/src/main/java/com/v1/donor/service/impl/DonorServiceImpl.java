package com.v1.donor.service.impl;

import com.v1.donor.dto.request.DonorRequest;
import com.v1.donor.dto.response.DonorResponse;
import com.v1.donor.entity.Donar;
import com.v1.donor.repository.DonarRepository;
import com.v1.donor.service.DonorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonorServiceImpl implements DonorService {
    @Autowired
    DonarRepository donarRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public DonorResponse createDonor(DonorRequest donorRequest) {
        //DonorResponse donorResponse = new DonorResponse();
        Donar donar = modelMapper.map(donorRequest, Donar.class);
        donarRepository.save(donar);
        return null;


    }

    @Override
    public List<DonorResponse> getAllDonors() {
        List<Donar> donars = donarRepository.findAll();
        return donars.stream()
                .map(donar -> modelMapper.map(donar, DonorResponse.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public DonorResponse getDonorById(String id) {
        //DonorResponse donorResponse = new DonorResponse();
        Optional<Donar> donarOptional = donarRepository.findById(Integer.valueOf(id));
        Donar donar = donarOptional.get();
        return modelMapper.map(donar, DonorResponse.class);
    }

    @Override
    public DonorResponse updateDonor(String id, DonorRequest donorRequest) {
        Optional<Donar> donarOptional = donarRepository.findById(Integer.valueOf(id));
        if (donarOptional.isPresent()) {
            Donar donar = modelMapper.map(donorRequest, Donar.class);
            donar.setDonarId(Long.valueOf(id));
            donarRepository.save(donar);
            return modelMapper.map(donar, DonorResponse.class);
        }
        return null;
    }

    @Override
    public void deleteDonor(String id) {
        donarRepository.deleteById(Integer.valueOf(id));
    }


}
