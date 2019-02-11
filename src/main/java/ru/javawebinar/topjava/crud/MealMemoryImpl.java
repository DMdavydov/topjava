package ru.javawebinar.topjava.crud;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMemoryImpl implements MealMemory {
    private final static List<Meal> meals = Arrays.asList(
            new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
            new Meal(7, LocalDateTime.of(2015, Month.JUNE, 1, 10, 0), "Завтрак", 500),
            new Meal(8, LocalDateTime.of(2015, Month.JUNE, 1, 13, 0), "Обед", 500),
            new Meal(9, LocalDateTime.of(2015, Month.JUNE, 1, 20, 0), "Ужин", 1000)
    );

    private final static Map<Integer, Meal> MEAL_MAP = new ConcurrentHashMap<>();

    private final static AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    static {
        meals.forEach(meal -> {
            meal.setId(ID_GENERATOR.incrementAndGet());
            MEAL_MAP.put(meal.getId(), meal);
        });
    }

    @Override
    public void create(Meal meal) {
        meal.setId(ID_GENERATOR.incrementAndGet());
        MEAL_MAP.put(meal.getId(), meal);
    }

    @Override
    public Meal read(int id) {
        return MEAL_MAP.get(id);
    }

    @Override
    public void update(Meal meal) {
        MEAL_MAP.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        MEAL_MAP.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(MEAL_MAP.values());
    }
}
