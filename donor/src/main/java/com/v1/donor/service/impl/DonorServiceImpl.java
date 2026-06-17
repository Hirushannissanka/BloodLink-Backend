package com.v1.donor.service.impl;

import com.v1.donor.dto.request.DonorRequest;
import com.v1.donor.dto.response.DonorResponse;
import com.v1.donor.entity.Donar;
import com.v1.donor.repository.DonarRepository;
import com.v1.donor.service.DonorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public DonorResponse getAllDonors() {

        return null;
    }


}
