package ocho.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ocho.util.ResourceUtil;

@RestController
public class ReactController extends ResourceUtil {

    private String html;

    ReactController() {
        var data = loadResource("static/index.html");

        if(data == null){
            html = "Ups... something wrong!!!";
            return;
        }

        html = data;
    }

    @GetMapping({
            "/shop",
            "/about",
            "/app/login",
            "/app/dashboard/",
            "/app/dashboard/welcome",
            "/app/dashboard/users",
            "/app/dashboard/laptops"
    })
    public String sendReactIndex(final HttpServletRequest req) {
        return html;
    }

}