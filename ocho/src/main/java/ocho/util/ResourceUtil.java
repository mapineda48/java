package ocho.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceUtil {
    public String loadResource(String filename) {
        try {
            var loader = getClass().getClassLoader();

            InputStream inputStream = loader.getResourceAsStream(filename);

            return readFromInputStream(inputStream);
        } catch (Exception e) {
            log.error("Fail load {}", filename, e);
            return null;
        }
    }

    private String readFromInputStream(InputStream inputStream)
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
