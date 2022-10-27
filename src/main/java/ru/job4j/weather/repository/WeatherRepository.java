package ru.job4j.weather.repository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.job4j.weather.model.Weather;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Repository
public class WeatherRepository {
    private final Map<Integer, Weather> weathers = new ConcurrentHashMap<>();
    {
        weathers.put(1, new Weather(1, "Lvov", 20));
        weathers.put(2, new Weather(2, "SPb", 15));
        weathers.put(3, new Weather(3, "Poltava", 15));
        weathers.put(4, new Weather(4, "New York", 15));
        weathers.put(5, new Weather(5, "Kiev", 15));
        weathers.put(6, new Weather(6, "Varshava", 15));
    }

    public Mono<Weather> findById(Integer id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    public Flux<Weather> all() {
        return Flux.fromIterable(weathers.values());
    }

    public Mono<Weather> hottest() {
        return Mono.justOrEmpty(weathers.values()
                .stream()
                .max(Comparator.comparingInt(Weather::getTemperature)));
    }

    public Flux<Weather> citiesMoreThenTemperatures(int temperature) {
        return Flux.fromIterable(weathers.values()).filter(e -> e.getTemperature() > temperature);
    }
}
