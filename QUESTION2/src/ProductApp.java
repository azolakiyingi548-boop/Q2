import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ProductApp extends JFrame {

    private JTextField txtCustomerName;
    private JComboBox<String> cmbProduct;
    private JTextField txtPrice;
    private JButton btnProcess;

    public ProductApp() {
        setTitle("Product App");
        setSize(400, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");

        JMenuItem mnuProcess = new JMenuItem("Process");
        JMenuItem mnuClear = new JMenuItem("Clear");
        JMenuItem mnuExit = new JMenuItem("Exit");

        fileMenu.add(mnuExit);
        editMenu.add(mnuProcess);
        editMenu.add(mnuClear);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        setJMenuBar(menuBar);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtCustomerName = new JTextField();
        cmbProduct = new JComboBox<>(new String[]{
                "Cozy Nights Monarch",
                "Restonic Barbados",
                "Sealy Phuket"
        });
        txtPrice = new JTextField();
        btnProcess = new JButton("PROCESS");

        panel.add(new JLabel("Customer Name:"));
        panel.add(txtCustomerName);
        panel.add(new JLabel("Select Product:"));

        panel.add(cmbProduct);
        panel.add(new JLabel("Enter Price:"));
        panel.add(txtPrice);
        panel.add(new JLabel(""));
        panel.add(btnProcess);

        add(panel);

        btnProcess.addActionListener(e -> processSale());
        mnuProcess.addActionListener(e -> processSale()); // Same logic as button
        mnuClear.addActionListener(e -> clearForm());
        mnuExit.addActionListener(e -> System.exit(0));
    }

    private void processSale() {
        try {
            String customerName =

                    txtCustomerName.getText();
            String product = (String) cmbProduct.getSelectedItem();
            double price = Double.parseDouble(txtPrice.getText());

            ProductCalculations calc = new ProductCalculations(price);
            double vat = calc.GetVat();
            double commission = calc.GetCommission();
            double total = calc.GetTotal();

            String message = "Customer Name: " + customerName
                    + "\nProduct: " + product
                    + "\nVAT: R " + vat
                    + "\nCOMMISSION: R " + commission
                    + "\nTOTAL: R " + total;

            JOptionPane.showMessageDialog(this, message, "Message",
                    JOptionPane.INFORMATION_MESSAGE);

            saveToFile(customerName, product, vat, commission, total);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid price.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveToFile(String customer, String product,
                            double vat, double commission, double total) {
        try {
            File file = new File("data.txt");

            boolean isNew = !file.exists() || file.length() == 0;

            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);

            if (isNew) {
                pw.println("DATA LOG");
                pw.println("******************************************");
            }
            pw.println("Customer Name: " + customer);
            pw.println("Product: " + product);
            pw.println("VAT: R " + vat);
            pw.println("COMMISSION: R " + commission);
            pw.println("TOTAL: R " + total);
            pw.println("******************************************");
            pw.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving to file: " + ex.getMessage());
        }
    }

    private void clearForm() {
        txtCustomerName.setText("");
        txtPrice.setText("");
        cmbProduct.setSelectedIndex(0);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProductApp().setVisible(true);
        });
    }
}