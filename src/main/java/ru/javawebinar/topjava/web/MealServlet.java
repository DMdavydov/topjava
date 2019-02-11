package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.crud.MealMemory;
import ru.javawebinar.topjava.crud.MealMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DatesUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealServlet extends HttpServlet {

    private MealMemory mealMemory;

    @Override
    public void init() throws ServletException {
        super.init();
        mealMemory = new MealMemoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String action = request.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            doPost(request, response);
            return;
        }
        final String id = request.getParameter("id");
        final Meal meal = id != null ? mealMemory.read(Integer.parseInt(id)) : new Meal();

        request.setAttribute("dateFormatter", new DatesUtil());
        request.setAttribute("meal", meal);
        request.setAttribute("meals", MealsUtil.getFilteredWithExcess(mealMemory.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        final String action = request.getParameter("action");
        if ("create".equalsIgnoreCase(action)) {
            mealMemory.create(parseRequest(request));
        } else if ("update".equalsIgnoreCase(action)) {
            mealMemory.update(parseRequest(request));
        } else if ("delete".equalsIgnoreCase(action)) {
            mealMemory.delete(Integer.parseInt(request.getParameter("id")));
        }
        response.sendRedirect("meals");
    }

    private Meal parseRequest(HttpServletRequest request) {
        return new Meal(
                Integer.parseInt(request.getParameter("id")),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );
    }
}
