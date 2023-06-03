package com.example.demo.api;

import com.example.demo.domain.CarEntity;
import com.example.demo.repo.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarRepository carRepository;

    @GetMapping("/car")
    public List<CarEntity> getCar() {
        return carRepository.findAll();
    }

}
