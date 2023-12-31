package com.prathamesh.decentralizedHealthcareBackend.controller;

import com.prathamesh.decentralizedHealthcareBackend.entity.Doctor;
import com.prathamesh.decentralizedHealthcareBackend.entity.RecordModel;
import com.prathamesh.decentralizedHealthcareBackend.service.DoctorService;
import com.prathamesh.decentralizedHealthcareBackend.util.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/doctor")
@CrossOrigin(origins = "http://localhost:3000")
public class DoctorController {

    Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    DoctorService doctorService;

    @Autowired
    JwtHelper jwtHelper;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDoctorInfo(@RequestHeader("Authorization") HashMap<String, String> map, @PathVariable String id){
        if (map.get("authorization") == null)
            return new ResponseEntity<>("Access Denied", HttpStatus.UNAUTHORIZED);

        String token = map.get("authorization").substring(7);

        logger.error(token);

        try {
            if (jwtHelper.validateToken(token)){
                Doctor doctor = doctorService.getDoctorInfo(id);

                if (doctor == null)
                        return new ResponseEntity<>("Doctor Not Found!!!", HttpStatus.BAD_REQUEST);

                return new ResponseEntity<>(doctor, HttpStatus.OK);
            }

        }catch (Exception e) {
            return new ResponseEntity<>("Access Denied", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("Access Denied", HttpStatus.UNAUTHORIZED);
    }
}
