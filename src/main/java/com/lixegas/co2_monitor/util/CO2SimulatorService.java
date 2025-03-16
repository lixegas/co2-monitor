package com.lixegas.co2_monitor.util;


import com.lixegas.co2_monitor.controller.TrackController;
import com.lixegas.co2_monitor.model.City;
import com.lixegas.co2_monitor.model.District;
import com.lixegas.co2_monitor.model.Sensor;
import com.lixegas.co2_monitor.model.Track;
import com.lixegas.co2_monitor.model.request.TrackCreationRequest;
import com.lixegas.co2_monitor.repository.CityRepository;
import com.lixegas.co2_monitor.repository.DistrictRepository;
import com.lixegas.co2_monitor.repository.SensorRepository;
import com.lixegas.co2_monitor.repository.TrackRepository;
import com.lixegas.co2_monitor.service.TrackService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class CO2SimulatorService {

    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;
    private final TrackService trackService;

    private final Random random = new Random();


    @Scheduled(fixedRate = 10000)
    public void simulateCO2Readings() {
        List<City> cities = cityRepository.findAll();

        for (City city : cities) {
            List<District> districts = districtRepository.findByCityId(city.getId());

            for (District district : districts) {
                Sensor sensor = district.getSensor();

                if (sensor != null) {
                    double co2Level = generateRandomCO2Level();

                    TrackCreationRequest trackCreationRequest = new TrackCreationRequest();
                    trackCreationRequest.setSensorId(sensor.getId());
                    trackCreationRequest.setCo2Level(co2Level);

                    trackService.save(trackCreationRequest);
                }
            }
        }
    }


    private double generateRandomCO2Level() {
        return 200 + (random.nextDouble() * 800);
    }
}
