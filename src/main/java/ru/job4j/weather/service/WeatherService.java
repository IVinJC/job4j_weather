package ru.job4j.weather.service;


import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.job4j.weather.model.Weather;
import ru.job4j.weather.repository.WeatherRepository;

import java.util.Comparator;

@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Mono<Weather> findById(Integer id) {
        return weatherRepository.findById(id);
    }

    public Flux<Weather> all() {
        return weatherRepository.all();
    }

    public Mono<Weather> hottest() {
        return weatherRepository.hottest();
    }

    public Flux<Weather> citiesMoreThenTemperatures(int temperature) {
        return weatherRepository.citiesMoreThenTemperatures(temperature);
    }
}