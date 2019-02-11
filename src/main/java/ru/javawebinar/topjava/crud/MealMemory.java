package ru.javawebinar.topjava.crud;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealMemory {
    Meal read(int id);

    void create(Meal meal);

    void update(Meal meal);

    void delete(int id);

    List<Meal> getAll();
}