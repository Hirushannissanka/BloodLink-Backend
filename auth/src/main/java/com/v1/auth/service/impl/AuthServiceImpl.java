package com.v1.auth.service.impl;

import com.v1.auth.client.DonorClient;
import com.v1.auth.dto.request.AuthRequest;
import com.v1.auth.dto.request.DonarRequest;
import com.v1.auth.dto.response.AuthResponse;
import com.v1.auth.entity.User;
import com.v1.auth.entity.UserRole;
import com.v1.auth.repository.UserRepository;
import com.v1.auth.service.AuthService;
import com.v1.auth.utils.JwtUtil;
import com.v1.auth.utils.ResponseCodes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private DonorClient donorClient;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        AuthResponse authResponse = new AuthResponse();


        return null;
    }

    @Override
    public AuthResponse signUp(AuthRequest authRequest) {
        AuthResponse authResponse = new AuthResponse();
        Optional<User> userByEmail = userRepository.findByEmail(authRequest.getEmail());
        if(userByEmail.isPresent()){
            authResponse.setResponseCode(ResponseCodes.EMAIL_ALREADY_TAKEN);
            return authResponse;
        }
        try{
            /*modelMapper.typeMap(AuthRequest.class, User.class)
                    .addMappings(mapper -> {
                        mapper.skip(User::setBloodGroup);
                        mapper.skip(User::setAvailabilityStatus);
                        mapper.skip(User::setLastDonationDate);

                    });*/

            User user = modelMapper.map(authRequest, User.class);
            user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
            userRepository.save(user);
            authResponse.setUsername(user.getUserName());
            authResponse.setResponseCode(ResponseCodes.SUCCESS);

            String token = jwtUtil.generateToken(user);
            authResponse.setToken(token);
            authResponse.setResponseCode(ResponseCodes.SUCCESS);

            if(user.getRole()== UserRole.Donar){
                DonarRequest donarRequest = new DonarRequest();
                donarRequest.setUserId(user.getUserId());
                donarRequest.setBloodGroup(authRequest.getBloodGroup());
                donarRequest.setAvailabilityStatus(authRequest.getAvailabilityStatus());
                donarRequest.setLastDonateDate(authRequest.getLastDonationDate());
                //System.out.println(donarRequest);
                donorClient.sendDonorDetails(donarRequest);
               // System.out.println(d);

            }



        }catch (Exception e){
            authResponse.setResponseCode(ResponseCodes.USER_SIGNUP_FAILURE);
        }
        return authResponse;
    }
}
