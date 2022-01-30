package ocho.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
public class ReactController extends Html {

    private String html;

    ReactController() {
        html = readIndex();
    }

    @GetMapping({
            "/login",
            "/dashboard/**"
    })
    public String getIndex(final HttpServletRequest req) {
        return html;
    }

}

@Slf4j
class Html {
    public String readIndex() {
        try {
            var loader = getClass().getClassLoader();

            InputStream inputStream = loader.getResourceAsStream("static/index.html");

            return readFromInputStream(inputStream);
        } catch (Exception e) {

            log.error("Fail load index.html", e);

            return "fail load index";
        }
    }

    public String readFromInputStream(InputStream inputStream)
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