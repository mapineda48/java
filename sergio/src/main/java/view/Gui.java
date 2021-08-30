package view;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

class GuiConst {
    public final static int width = 500;
    public final static int height = 400;

    // Actions
    public final static String TYPE_ADD = "AGREGAR";
    public final static String TYPE_EDIT = "ACTUALIZAR";
    public final static String TYPE_DELETE = "BORRAR";

    // Messages
    public final static String ERROR = "ERROR";

    // Columns
    public final static String columns[] = { "ID", "CODIGO", "NOMBRE", "PRECIO", "CANTIDAD" };

}

class Util {
    public static String getSelectedProduct(JTable jt) {
        int row = jt.getSelectedRow();

        if (row == -1) {
            return null;
        }

        TableModel model = jt.getModel();

        String id = model.getValueAt(row, 0) + "";
        String code = model.getValueAt(row, 1) + "";
        String name = model.getValueAt(row, 2) + "";
        String price = model.getValueAt(row, 3) + "";
        String amount = model.getValueAt(row, 4) + "";

        String res = String.join(" ", id, code, name, price, amount);

        return res;
    }
}

public class Gui {
    private final Controller db;
    private final JFrame frame = new JFrame("Una simple aplicación Swing");
    private final JLabel label = new JLabel("Inventario");
    private Runnable onUnMount;

    public Gui(Controller db) {
        this.db = db;

        // Se le da al cuadro un tamaño inicial.
        this.frame.setSize(GuiConst.width, GuiConst.height);

        // Disable resize
        this.frame.setResizable(false);

        // Termine el programa cuando el usuario cierre la aplicación.
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel head = new JPanel();

        head.add(this.label);

        // Agregue la etiqueta al panel de contenido (content pane).
        this.frame.getContentPane().add(BorderLayout.NORTH, head);

        // Visualiza el marco.
        this.frame.setVisible(true);

        // https://stackoverflow.com/questions/144892/how-to-center-a-window-in-java
        this.frame.setLocationRelativeTo(null);

        this.fetchRecords();
    }

    public void unmount() {
        if (this.onUnMount == null) {
            return;
        }

        this.onUnMount.run();
        this.onUnMount = null;
    }

    public void refresh() {
        this.frame.invalidate();
        this.frame.validate();
        this.frame.repaint();
    }

    public void mountMessage(String message) {
        // create a dialog Box
        JDialog d = new JDialog(this.frame, "Alert");

        // create a label
        JLabel l = new JLabel(message);

        // create a button
        JButton b = new JButton("Aceptar");

        b.addActionListener((e) -> {
            d.dispose();
        });

        // setsize of dialog
        d.setSize(200, 100);

        // center
        d.setLocationRelativeTo(null);

        // Disable resize
        d.setResizable(false);

        // Modal
        d.setModal(true);

        // create a panel
        JPanel p = new JPanel();

        p.add(l);
        p.add(b);

        d.add(p);

        // set visibility of dialog
        d.setVisible(true);
    }

    public void mountFrameMessage(String title, String message) {
        this.unmount();

        this.setHeader(title);

        JPanel body = new JPanel();

        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        // Add Main Component to frame
        this.frame.getContentPane().add(body);

        JPanel panelMsg = new JPanel();

        JTextArea multi = new JTextArea(message, 8, 30);
        multi.setWrapStyleWord(true);
        multi.setLineWrap(true);
        multi.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(multi);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        panelMsg.add(scrollPane);

        body.add(panelMsg);

        // Creando el panel en la parte inferior y agregando componentes
        JPanel panelBtns = new JPanel();

        JButton backButton = new JButton("Volver");

        ActionListener onClickBack = (e) -> {
            this.fetchRecords();
        };

        backButton.addActionListener(onClickBack);

        // Add Buttons
        panelBtns.add(backButton);

        body.add(panelBtns);

        // Refresh JFrame
        this.refresh();

        // Clear Tasks
        this.onUnMount = () -> {
            // Remove table events
            backButton.removeActionListener(onClickBack);

            // Remove Component
            this.frame.getContentPane().remove(body);
        };

    }

    private void fetchReport() {
        try {
            String res = this.db.generateReport();
            this.mountReport(res);
        } catch (Exception e) {
            this.showDataBaseError(e);
        }
    }

    public void mountReport(String res) {
        this.unmount();

        if (res == null) {
            this.mountFrameMessage("Ups...", "No se encontraron productos");
            return;
        }

        String[] report = res.split(" ");

        this.setHeader("Reporte");

        JPanel body = new JPanel();

        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        // Add Main Component to frame
        this.frame.getContentPane().add(body);

        JPanel fieldBig = new JPanel();

        fieldBig.add(new JLabel("Mayor: "));
        fieldBig.add(new JLabel(report[0]));

        JPanel fieldLittle = new JPanel();

        fieldLittle.add(new JLabel("Menor: "));
        fieldLittle.add(new JLabel(report[1]));

        JPanel fieldPromed = new JPanel();

        fieldPromed.add(new JLabel("Promedio Precio: "));
        fieldPromed.add(new JLabel(report[2]));

        JPanel fieldTotal = new JPanel();

        fieldTotal.add(new JLabel("Total: "));
        fieldTotal.add(new JLabel(report[3]));

        JPanel panelReport = new JPanel();

        panelReport.setLayout(new BoxLayout(panelReport, BoxLayout.Y_AXIS));

        panelReport.setBorder(new EmptyBorder(50, 10, 50, 10));

        panelReport.add(fieldBig);
        panelReport.add(fieldLittle);
        panelReport.add(fieldPromed);
        panelReport.add(fieldTotal);

        body.add(panelReport);

        // Creando el panel en la parte inferior y agregando componentes
        JPanel panel = new JPanel();
        JButton backButton = new JButton("Volver");

        ActionListener onClickBack = (e) -> {
            this.fetchRecords();
        };

        backButton.addActionListener(onClickBack);

        // Add Buttons
        panel.add(backButton);

        body.add(BorderLayout.SOUTH, panel);

        // Refresh JFrame
        this.refresh();

        // Clear Tasks
        this.onUnMount = () -> {
            // Remove table events
            backButton.removeActionListener(onClickBack);

            // Remove Component
            this.frame.getContentPane().remove(body);
        };
    }

    public boolean isEmpty(String val) {
        if (val == null || val.length() == 0) {
            return true;
        }

        return false;
    }

    public void mountAdd() {
        this.unmount();

        this.setHeader("Agregar");

        JPanel body = new JPanel();

        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        // Add Main Component to frame
        this.frame.getContentPane().add(body);

        JPanel fieldCode = new JPanel();
        JTextField inputCode = new JTextField(16);

        fieldCode.add(new JLabel("Codigo: "));
        fieldCode.add(inputCode);

        JPanel fieldName = new JPanel();
        JTextField inputName = new JTextField(16);

        fieldName.add(new JLabel("Nombre: "));
        fieldName.add(inputName);

        JPanel fieldPrice = new JPanel();
        JTextField inputPrice = new JTextField(16);

        fieldPrice.add(new JLabel("Precio: "));
        fieldPrice.add(inputPrice);

        JPanel fieldAmount = new JPanel();
        JTextField inputAmount = new JTextField(16);

        fieldAmount.add(new JLabel("Cantidad: "));
        fieldAmount.add(inputAmount);

        JPanel panelAdd = new JPanel();

        panelAdd.setLayout(new BoxLayout(panelAdd, BoxLayout.Y_AXIS));

        panelAdd.setBorder(new EmptyBorder(50, 10, 50, 10));

        panelAdd.add(fieldCode);
        panelAdd.add(fieldName);
        panelAdd.add(fieldPrice);
        panelAdd.add(fieldAmount);

        body.add(panelAdd);

        // Creando el panel en la parte inferior y agregando componentes
        JPanel panel = new JPanel();
        JButton addButton = new JButton("Completar");

        ActionListener onClickAdd = (e) -> {
            String code = inputCode.getText();

            if (this.isEmpty(code)) {
                this.mountMessage("Ingrese Codigo");
                return;
            }

            String name = inputName.getText();

            if (this.isEmpty(name)) {
                this.mountMessage("Ingrese Nombre");
                return;
            }
            String price = inputPrice.getText();

            if (this.isEmpty(price)) {
                this.mountMessage("Ingrese Precio");
                return;
            }

            String amount = inputAmount.getText();

            if (this.isEmpty(amount)) {
                this.mountMessage("Ingrese Cantidad");
                return;
            }

            try {
                String res = this.db.add(String.join(" ", code, name, price, amount));

                if (res == GuiConst.ERROR) {
                    this.mountMessage("El codigo " + code + " ya existe!");
                } else {
                    this.mountMessage("Producto Agregado");

                    // Limpiar formulario
                    inputCode.setText("");
                    inputName.setText("");
                    inputPrice.setText("");
                    inputAmount.setText("");
                }

                // Unhandler Error
            } catch (Exception err) {
                System.out.println(err);
                this.mountMessage("Verifique los campos");
            }
        };

        addButton.addActionListener(onClickAdd);

        JButton backButton = new JButton("Volver");

        ActionListener onClickBack = (e) -> {
            this.fetchRecords();
        };

        backButton.addActionListener(onClickBack);

        // Add Buttons
        panel.add(addButton);
        panel.add(backButton);

        body.add(BorderLayout.SOUTH, panel);

        // Refresh JFrame
        this.refresh();

        // Clear Tasks
        this.onUnMount = () -> {
            // Remove table events
            backButton.removeActionListener(onClickBack);

            // Remove Component
            this.frame.getContentPane().remove(body);
        };

    }

    private void fetchRecords() {
        try {
            Object[][] data = this.db.getData();
            this.mountRecords(data);
        } catch (Exception e) {
            this.showDataBaseError(e);
        }
    }

    public void mountRecords(Object[][] data) {
        this.unmount();
        this.setHeader("Productos");

        JPanel body = new JPanel();

        // Add Main Component to frame
        this.frame.getContentPane().add(BorderLayout.CENTER, body);

        /**
         * Data Model Table
         * https://itqna.net/questions/112117/jtable-only-accept-float-type-numbers-your-cell-closed
         */
        DefaultTableModel model = new DefaultTableModel(data, GuiConst.columns) {

            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return String.class;
                    case 3:
                        return Float.class;
                    case 4:
                        return Integer.class;
                    default:
                        return super.getColumnClass(column);

                }

            }
        };

        /**
         * Create Table
         */
        JTable jt = new JTable(model);

        /**
         * Only decimal
         * https://comp.lang.java.help.narkive.com/gDcZhUQk/formatting-floats-in-a-table
         */
        jt.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            private final NumberFormat formatter = new DecimalFormat("0.00");

            protected void setValue(Object value) {
                if (value instanceof Number) {
                    value = formatter.format(value).replace(",", ".");
                }
                super.setValue(value);
            }
        });

        // Hide Column Id
        jt.removeColumn(jt.getColumnModel().getColumn(0));

        // On Change Some values
        // Make sure catch change some value
        jt.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        TableModelListener onChangeValue = (e) -> {
            int row = jt.getSelectedRow();

            int column = jt.getSelectedColumn();

            Object current = data[row][column];

            Object next = jt.getValueAt(row, column);

            try {
                String dml = Util.getSelectedProduct(jt);

                this.db.update(dml);

                data[row][column] = next;
            } catch (Exception err) {
                // Undo
                jt.setValueAt(current, row, column);

                System.out.println(err);

                if (column == 0) {
                    this.mountMessage("El codigo no esta disponible");
                } else {
                    this.mountMessage("Error al editar " + column);
                }
            }
        };

        jt.getModel().addTableModelListener(onChangeValue);

        JScrollPane js = new JScrollPane(jt);

        js.setPreferredSize(new Dimension(GuiConst.width - 20, GuiConst.height - 150));
        js.setVisible(true);

        body.add(js);

        // Creando el panel en la parte inferior y agregando componentes
        JPanel panel = new JPanel(); // el panel no está visible en la salida
        JButton addButton = new JButton("Agregar");

        ActionListener onClickAdd = (e) -> {
            this.mountAdd();
        };

        addButton.addActionListener(onClickAdd);

        JButton delButton = new JButton("Eliminar");

        ActionListener onClickDel = (e) -> {
            String line = Util.getSelectedProduct(jt);

            if (line == null) {
                this.mountMessage("Seleccione un producto!!!");
                return;
            }

            try {
                this.db.delete(line);
                this.fetchRecords();
            } catch (Exception err) {
                this.mountMessage("Fallo Eliminar");
            }
        };

        delButton.addActionListener(onClickDel);

        JButton reportButton = new JButton("Reporte");

        ActionListener onClickReport = (e) -> {
            this.fetchReport();
        };

        reportButton.addActionListener(onClickReport);

        JButton editButton = new JButton("Editar");

        ActionListener onClickEdit = (e) -> {
            this.mountMessage("Edite la celda en la tabla");
        };

        editButton.addActionListener(onClickEdit);

        // Add Buttons
        panel.add(addButton);
        panel.add(editButton);
        panel.add(delButton);
        panel.add(reportButton);

        body.add(BorderLayout.SOUTH, panel);

        // Refresh JFrame
        this.refresh();

        // Clear Tasks
        this.onUnMount = () -> {
            // Remove table events
            addButton.removeActionListener(onClickAdd);
            editButton.removeActionListener(onClickEdit);
            delButton.removeActionListener(onClickDel);
            reportButton.removeActionListener(onClickReport);
            jt.getModel().removeTableModelListener(onChangeValue);

            // Remove Component
            this.frame.getContentPane().remove(body);
        };
    }

    public void showDataBaseError(Exception e) {
        System.out.println(e);
        this.mountFrameMessage("Error",
                "Asegurese de cargar correctamente\n\n\"<root project>/src/main/resources/db/src.sql\"");
    }

    public void setHeader(String text) {
        this.label.setText(text);
    }
}
