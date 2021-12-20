package ocho.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReactController {
    private String html;

    ReactController() {
        html = Resources.readIndex(getClass().getClassLoader());
    }

    @GetMapping({ "/dashboard/**" })
    public String getIndex(final HttpServletRequest req) {
        return html;
    }

}

class Resources {
    public static String readIndex(ClassLoader classLoader) {
        try {
            InputStream inputStream = classLoader.getResourceAsStream("static/index.html");
            return readFromInputStream(inputStream);
        } catch (Exception e) {
            System.out.println(e);
            return "fail read react index";
        }
    }

    public static String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}