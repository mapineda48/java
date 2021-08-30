package view;

public interface Controller {   
    String generateReport();

    String add(String producto);

    Object[][] getData();

    void update(String data);

    void delete(String data);

}
