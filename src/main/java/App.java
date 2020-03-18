import dao.Sql2oClientsDao;

import dao.Sql2oStylistsDao;
import models.Clients;
import models.Stylists;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args){
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/hair_masters.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "mac", "@dZumi0991");
        Sql2oClientsDao clientsDao = new Sql2oClientsDao(sql2o);
        Sql2oStylistsDao stylistsDao = new Sql2oStylistsDao(sql2o);

        // Get Routes

        get("/", (req, res) -> {
            if (req.session().attribute("logged_in") == null) {
                res.redirect("/login");
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
            model.put("userRole", role);
            List<Stylists> stylists = stylistsDao.getAll();
            model.put("stylists", stylists);
            return new ModelAndView(model, "stylists.hbs");
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
            List<Stylists> stylists = stylistsDao.getAll();
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



        // Post Routes

        post("/clientLogin", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            String username = req.queryParams("username");
            String password = req.queryParams("myPassword");
            clientsDao.loginClient(username, password);
            Clients client = clientsDao.findByUsername(username);
            req.session(true);
            req.session().attribute("logged_in", username);
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
            return new ModelAndView(model, "login.hbs");
        }, new HandlebarsTemplateEngine());

        post("/stylistLogin", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            String stylistEmail = req.queryParams("email");
            String password = req.queryParams("myPassword");
            stylistsDao.loginStylist(stylistEmail, password);
            Stylists stylist = stylistsDao.findByEmail(stylistEmail);
            req.session(true);
            req.session().attribute("logged_in", stylistEmail);
            req.session().attribute("role", stylist.getRole());
            res.redirect("/bookings");
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
    }
}
