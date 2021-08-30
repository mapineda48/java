package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import model.Product;
import model.RepositoryProduct;
import view.Controller;
import view.Gui;

@SpringBootApplication
@ComponentScan("model")
@EnableJdbcRepositories("model")
public class ProductApplication {
    @Autowired
    RepositoryProduct repositoryProduct;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        new SpringApplicationBuilder(ProductApplication.class).headless(false).run(args);
    }

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            RepositoryProduct db = this.repositoryProduct;

            new Gui(new Controller() {
                public String generateReport() {
                    String sql = "SELECT generate_report() AS \"report\"";

                    return jdbcTemplate.queryForObject(sql, new RowMapper<String>() {
                        @Override
                        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return rs.getString("report");
                        }
                    });
                };

                public String add(String product) {

                    Product record = Product.crear(product);

                    db.save(record);

                    return "";
                };

                public Object[][] getData() {
                    Iterable<Product> products = db.findAll();

                    Long size = products.spliterator().getExactSizeIfKnown();

                    Object[][] res = new Object[size.intValue()][];

                    int index = 0;

                    for (Product p : products) {
                        res[index] = p.toArrObject();
                        index += 1;
                    }

                    return res;
                };

                public void update(String data) {
                    Product record = Product.parse(data);
                    db.save(record);
                };

                public void delete(String data) {
                    Product record = Product.parse(data);
                    db.delete(record);
                };
            });
        };
    }
}