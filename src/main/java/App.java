import dao.Sql2oBookingsDao;
import dao.Sql2oClientsDao;

import dao.Sql2oStylistsDao;
import dates.CurrentDate;
import models.Bookings;
import models.Clients;
import models.Stylists;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.awt.print.Book;
import java.text.DateFormat;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args){
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        setPort(port);
        
        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/hair_masters";
        Sql2o sql2o = new Sql2o(connectionString, "mac", "@dZumi0991");
        Sql2oClientsDao clientsDao = new Sql2oClientsDao(sql2o);
        Sql2oStylistsDao stylistsDao = new Sql2oStylistsDao(sql2o);
        Sql2oBookingsDao bookingsDao = new Sql2oBookingsDao(sql2o);


        CurrentDate today = new CurrentDate(Locale.UK);
        java.util.Date utilCurrentDate = java.sql.Date.valueOf(today.getCurrentDate());
        bookingsDao.deleteFrom(java.sql.Date.valueOf(today.getFirstDay()));

        // Get Routes

        get("/", (req, res) -> {
            if (req.session().attribute("logged_in") == null) {
                res.redirect("/login");
                return null;
            }
            if (req.session().attribute("role")) {
                res.redirect("/stylistDash");
                return null;
            }
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/login", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "login.hbs");
        }, new HandlebarsTemplateEngine());

        get("/create_account", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "create_account.hbs");
        }, new HandlebarsTemplateEngine());

        get("/stylists", (req, res) -> {
            if (req.session().attribute("logged_in") == null) {
                res.redirect("/login");
                return null;
            }
            Map<String, Object> model = new HashMap<>();
            boolean role = req.session().attribute("role");
            List<Stylists> stylists = stylistsDao.getAll("true");
            List<Bookings> bookings = bookingsDao.getAll(utilCurrentDate);
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
            String startofWeek = dateFormat.format(Date.valueOf(today.getFirstDay()));
            String endOfWeek = dateFormat.format(Date.valueOf(today.getLastDay()));
            String currentDayString = dateFormat.format(Date.valueOf(today.getCurrentDate()));

            model.put("startofWeek", startofWeek);
            model.put("endOfWeek", endOfWeek);
            model.put("currentDayString", currentDayString);
            model.put("utilCurrentDate", utilCurrentDate);
            model.put("stylists", stylists);
            model.put("bookings", bookings);
            model.put("userRole", role);
            return new ModelAndView(model, "stylists.hbs");
        }, new HandlebarsTemplateEngine());

        get("/stylist/:id/schedule", (req, res) -> {
            if (req.session().attribute("logged_in") == null) {
                res.redirect("/login");
                return null;
            }
            Map<String, Object> model = new HashMap<>();
            int idOfStylistDetails = Integer.parseInt(req.params("id"));
            Stylists stylist = stylistsDao.findById(idOfStylistDetails);
            List<Bookings> bookings = bookingsDao.getAllFromStylist(utilCurrentDate, stylist.getStylistName());
            model.put("stylist", stylist);
            model.put("bookings", bookings);
            model.put("userRole", req.session().attribute("role"));
            return new ModelAndView(model, "stylistSchedule.hbs");
        }, new HandlebarsTemplateEngine());

        get("/bookings", (req, res) -> {
            if (req.session().attribute("logged_in") == null) {
                res.redirect("/login");
                return null;
            }
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "bookings.hbs");
        }, new HandlebarsTemplateEngine());

        get("/adminDash", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Stylists> stylists = stylistsDao.getAll("true");
            model.put("stylists", stylists);
            return new ModelAndView(model, "adminDash.hbs");
        }, new HandlebarsTemplateEngine());

        get("/stylist/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStylistToDelete = Integer.parseInt(req.params("id"));
            stylistsDao.deleteById(idOfStylistToDelete);
            res.redirect("/adminDash");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/client/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfClientToDelete = Integer.parseInt(req.params("id"));
            clientsDao.deleteById(idOfClientToDelete);
            res.redirect("/adminDash");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/logoutClient", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            req.session().removeAttribute("logged_in");
            return new ModelAndView(model, "login.hbs");
        }, new HandlebarsTemplateEngine());

        get("/stylistDash", (req, res) -> {
            if (req.session().attribute("logged_in") == null) {
                res.redirect("/login");
                return null;
            }
            Map<String, Object> model = new HashMap<>();
            Stylists stylist = stylistsDao.findById(req.session().attribute("user_id"));
            List<Bookings> bookings = bookingsDao.getAllFromStylist(utilCurrentDate, stylist.getStylistName());
            if (stylist.getStylistEmail() == "admin@admin.com"){
                model.put("userRole", false);
            }
            else  {
                model.put("userRole", true);
            }
            model.put("stylist", stylist);
            model.put("bookings", bookings);
            return new ModelAndView(model, "stylistDash.hbs");
        }, new HandlebarsTemplateEngine());

        get("/error", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "errorPage.hbs");
        }, new HandlebarsTemplateEngine());



        // Post Routes

        post("/clientLogin", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            String username = req.queryParams("username");
            String password = req.queryParams("myPassword");
            clientsDao.loginClient(username, password);
            Clients client = clientsDao.findByUsername(username);
            req.session(true);
            req.session().attribute("logged_in", username);
            req.session().attribute("user_id", client.getClient_id());
            req.session().attribute("role", client.getRole());
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        post("/postNewClient", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            String username = req.queryParams("username");
            String clientName = req.queryParams("clientName");
            String clientPhone = req.queryParams("clientPhone");
            String password = req.queryParams("password");
            String confirmPassword = req.queryParams("confirmPassword");
            Clients newClient = new Clients(username, clientName, clientPhone, password);
            clientsDao.add(newClient);
            System.out.println("" + newClient.getClientName() + "," + newClient.getUsername() + "," + newClient.getClientPhone());
            return "null";
        });

        post("/stylistLogin", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            String stylistEmail = req.queryParams("email");
            String password = req.queryParams("myPassword");
            stylistsDao.loginStylist(stylistEmail, password);
            Stylists stylist = stylistsDao.findByEmail(stylistEmail);
            req.session(true);
            req.session().attribute("logged_in", stylistEmail);
            req.session().attribute("user_id", stylist.getStylist_id());
            req.session().attribute("role", stylist.getRole());
            res.redirect("/stylistDash");
            return null;
        }, new HandlebarsTemplateEngine());

        post("/postNewStylist", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            String stylistEmail = req.queryParams("stylistEmail");
            String stylistName = req.queryParams("stylistName");
            String stylistPhone = req.queryParams("stylistPhone");
            String stylistDept = req.queryParams("stylistDept");
            String password = req.queryParams("password");
            String confirmPassword = req.queryParams("confirmPassword");
            Stylists newStylist = new Stylists(stylistEmail, stylistName, stylistPhone, stylistDept, password);
            stylistsDao.add(newStylist);
            res.redirect("/adminDash");
            return null;
        }, new HandlebarsTemplateEngine());

        post("/makeBooking", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            String booking_date = req.queryParams("booking_date");
            String start_at = req.queryParams("start_at");
            String end_at = req.queryParams("end_at");
            String client = clientsDao.findById(req.session().attribute("user_id")).getClientName();
            String stylist =  stylistsDao.findById(Integer.parseInt(req.queryParams("stylist_id"))).getStylistName();
            Bookings newBooking = new Bookings(booking_date, start_at, end_at, client, stylist);
            bookingsDao.add(newBooking);
            return new ModelAndView(model, "bookingSuccessful.hbs");
        }, new HandlebarsTemplateEngine());

        get("/checkBookings", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();

            System.out.println("Check this Booking Date " + req.queryParams("booking_date"));
            return null;
        });
    }
}
