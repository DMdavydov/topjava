package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(100002, USER_ID);
        assertMatch(meal, MEAL_1);
    }

    @Test
    public void delete() {
        service.delete(100002, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID);
        assertMatch(meals, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> meals = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 31, 10, 0),
                LocalDateTime.of(2015, Month.MAY, 31, 13, 0), USER_ID);
        assertMatch(meals, MEAL_5, MEAL_4);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(ADMIN_ID), MEAL_8, MEAL_7);
    }

    @Test
    public void update() {
        Meal updateMeal = new Meal(100002, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "UPDATE", 300);
        service.update(updateMeal, USER_ID);
        assertMatch(service.get(100002, USER_ID), updateMeal);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2018, Month.NOVEMBER, 9, 18, 0), "Ужин", 700);
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(ADMIN_ID), newMeal, MEAL_8, MEAL_7);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(100003, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(100004, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        Meal updateMeal = new Meal(100002, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "UPDATE", 300);
        service.update(updateMeal, ADMIN_ID);
    }
}