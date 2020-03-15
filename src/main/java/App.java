import dao.Sql2oClientsDao;
import models.Books;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args){
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/lib.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "mac", "@dZumi0991");
        Sql2oClientsDao authorsDao = new Sql2oClientsDao(sql2o);

        get("/", (req, res) -> {
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
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "stylists.hbs");
        }, new HandlebarsTemplateEngine());

        get("/bookings", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "bookings.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
