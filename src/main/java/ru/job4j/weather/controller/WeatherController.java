package ru.job4j.weather.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.job4j.weather.model.Weather;
import ru.job4j.weather.service.WeatherService;

import java.time.Duration;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    /**
     * Метод all использует задержку публикации данных. Сделано это для демонстрации сервиса с долгой загрузкой.
     * @return Flux
     */

    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Weather> all() {
        Flux<Weather> data = weatherService.all();
        Flux<Long> delay = Flux.interval(Duration.ofSeconds(3));
        Flux.zip(data, delay).map(Tuple2::getT1);
        Flux<Tuple2<Weather, Long>> zip = Flux.zip(data, delay);
        Flux<Weather> map = zip.map(Tuple2::getT1);
        return map;
    }

    @GetMapping(value = "/get/{id}")
    public Mono<Weather> get(@PathVariable Integer id) {
        return weatherService.findById(id);
    }

    @GetMapping("/hottest")
    public Mono<Weather> hot() {
        return weatherService.hottest();
    }

    @GetMapping("/cityGreatThen/{temperature}")
    public Flux<Weather> greatThen(@PathVariable int temperature) {
        return weatherService.citiesMoreThenTemperatures(temperature);
    }
}