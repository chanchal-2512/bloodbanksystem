package com.example;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class MainFrame extends JFrame {
    private DonorDAO donorDAO = new DonorDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private HospitalDAO hospitalDAO = new HospitalDAO();
    private BloodBankDAO bloodBankDAO = new BloodBankDAO();
    private DefaultTableModel donorModel, patientModel, hospitalModel, bloodBankModel;
    private JTable donorTable;
    private JTable patientTable;
    private JTable bloodBankTable;



    public MainFrame() {
        setTitle("Blood Bank Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Donors", createDonorPanel());
        tabbedPane.addTab("Patients", createPatientPanel());
        tabbedPane.addTab("Hospitals", createHospitalPanel());
        tabbedPane.addTab("Blood Banks", createBloodBankPanel());
        tabbedPane.addTab("Search Blood", createSearchPanel());


        add(tabbedPane);

        loadAllData();
    }

    private JPanel createDonorPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table with donor columns
        donorModel = new DefaultTableModel(new String[]{"ID", "Name", "DOB", "Age", "Blood Group", "Address", "Phone"}, 0);
        donorTable = new JTable(donorModel);
        panel.add(new JScrollPane(donorTable), BorderLayout.CENTER);

        // Form panel
        JPanel form = new JPanel(new GridLayout(2, 8, 5, 5));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField dobField = new JTextField();  // Expected format YYYY-MM-DD
        JTextField ageField = new JTextField();
        JTextField bloodField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField phoneField = new JTextField();

        form.add(new JLabel("ID"));
        form.add(new JLabel("Name"));
        form.add(new JLabel("DOB (YYYY-MM-DD)"));
        form.add(new JLabel("Age"));
        form.add(new JLabel("Blood Group"));
        form.add(new JLabel("Address"));
        form.add(new JLabel("Phone"));
        form.add(new JLabel(""));  // Empty cell for button

        form.add(idField);
        form.add(nameField);
        form.add(dobField);
        form.add(ageField);
        form.add(bloodField);
        form.add(addressField);
        form.add(phoneField);

        JButton addButton = new JButton("Add Donor");
        form.add(addButton);

        panel.add(form, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            try {
                String dobString = dobField.getText().trim();
                java.sql.Date sqlDob;

                try {
                    java.util.Date parsed = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dobString);
                    sqlDob = new java.sql.Date(parsed.getTime());
                } catch (java.text.ParseException ex) {
                    JOptionPane.showMessageDialog(this, "DOB must be in YYYY-MM-DD format");
                    return;
                }

                int age = Integer.parseInt(ageField.getText().trim());

                Donor donor = new Donor(
                        idField.getText().trim(),
                        nameField.getText().trim(),
                        sqlDob,
                        age,
                        bloodField.getText().trim(),
                        addressField.getText().trim(),
                        phoneField.getText().trim()
                );

                donorDAO.addDonor(donor);
                loadDonors();

                JOptionPane.showMessageDialog(this, "Donor added successfully!");

                // Clear input fields
                idField.setText("");
                nameField.setText("");
                dobField.setText("");
                ageField.setText("");
                bloodField.setText("");
                addressField.setText("");
                phoneField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Age must be a valid integer");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding donor: " + ex.getMessage());
            }
        });

        return panel;
    }

    private JPanel createPatientPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table with patient columns
        patientModel = new DefaultTableModel(new String[]{"ID", "Name", "Gender", "Blood Group", "Quantity", "Hospital ID"}, 0);
        patientTable = new JTable(patientModel);
        panel.add(new JScrollPane(patientTable), BorderLayout.CENTER);

        // Form panel
        JPanel form = new JPanel(new GridLayout(2, 7, 5, 5));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField bloodField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField hospitalIDField = new JTextField();

        form.add(new JLabel("ID"));
        form.add(new JLabel("Name"));
        form.add(new JLabel("Gender"));
        form.add(new JLabel("Blood Group"));
        form.add(new JLabel("Quantity"));
        form.add(new JLabel("Hospital ID"));
        form.add(new JLabel(""));  // Empty for button

        form.add(idField);
        form.add(nameField);
        form.add(genderField);
        form.add(bloodField);
        form.add(quantityField);
        form.add(hospitalIDField);

        JButton addButton = new JButton("Add Patient");
        form.add(addButton);

        panel.add(form, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            try {
                int quantity = Integer.parseInt(quantityField.getText().trim());

                Patient patient = new Patient(
                        idField.getText().trim(),
                        nameField.getText().trim(),
                        genderField.getText().trim(),
                        bloodField.getText().trim(),
                        quantity,
                        hospitalIDField.getText().trim()
                );

                patientDAO.addPatient(patient);
                loadPatients();

                JOptionPane.showMessageDialog(this, "Patient added successfully!");

                // Clear input fields
                idField.setText("");
                nameField.setText("");
                genderField.setText("");
                bloodField.setText("");
                quantityField.setText("");
                hospitalIDField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity must be a valid integer");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding patient: " + ex.getMessage());
            }
        });

        return panel;
    }
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Top panel for search input
        JPanel inputPanel = new JPanel();
        JTextField bloodGroupField = new JTextField(5);
        JButton searchButton = new JButton("Search");
        inputPanel.add(new JLabel("Enter Blood Group:"));
        inputPanel.add(bloodGroupField);
        inputPanel.add(searchButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        // Table for search results
        DefaultTableModel searchModel = new DefaultTableModel(new String[]{"Blood Bank ID", "Quantity"}, 0);
        JTable searchTable = new JTable(searchModel);
        panel.add(new JScrollPane(searchTable), BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String bloodGroup = bloodGroupField.getText().trim().toUpperCase();
            searchModel.setRowCount(0); // clear old results

            if (bloodGroup.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a blood group.");
                return;
            }

            try {
                CollectsBloodDAO collectsBloodDAO = new CollectsBloodDAO();
                Map<String, Integer> results = collectsBloodDAO.getBloodGroupAvailability(bloodGroup);
                if (results.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No availability found for this blood group.");
                } else {
                    for (Map.Entry<String, Integer> entry : results.entrySet()) {
                        searchModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
                    }
                }
            } catch (Exception ex) {
                showError(ex);
            }
        });

        return panel;
    }


    private JPanel createHospitalPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        hospitalModel = new DefaultTableModel(new String[]{"ID", "Name", "Location"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make table cells read-only
            }
        };

        JTable table = new JTable(hospitalModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }


    private JPanel createBloodBankPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        bloodBankModel = new DefaultTableModel(new String[]{"ID", "Name", "City", "State", "Stock"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make cells non-editable
            }
        };

        bloodBankTable = new JTable(bloodBankModel);
        panel.add(new JScrollPane(bloodBankTable), BorderLayout.CENTER);

        return panel;
    }



    private void loadAllData() {
        loadDonors();
        loadPatients();
        loadHospitals();
        loadBloodBanks();
    }

    private void loadDonors() {
        try {
            donorModel.setRowCount(0);
            for (Donor d : donorDAO.getAllDonors()) {
                donorModel.addRow(new Object[]{d.getDonorID(), d.getName(), d.getDob(), d.getAge(), d.getBloodGroup(), d.getAddress(), d.getph_no()});
            }
        } catch (Exception e) {
            showError(e);
        }
    }

    private void loadPatients() {
        try {
            patientModel.setRowCount(0);
            for (Patient p : patientDAO.getAllPatients()) {
                patientModel.addRow(new Object[]{p.getPatientID(), p.getName(), p.getGender(), p.getBloodGroup(), p.getQuantityRequired(), p.getHospitalID()});
            }
        } catch (Exception e) {
            showError(e);
        }
    }

    private void loadHospitals() {
        try {
            hospitalModel.setRowCount(0);
            for (Hospital h : hospitalDAO.getAllHospitals()) {
                hospitalModel.addRow(new Object[]{h.getId(), h.getName(),h.getLocation()});
            }
        } catch (Exception e) {
            showError(e);
        }
    }

    private void loadBloodBanks() {
        try {
            bloodBankModel.setRowCount(0);
            for (BloodBank b : bloodBankDAO.getAllBloodBanks()) {
                bloodBankModel.addRow(new Object[]{b.getId(), b.getName(), b.getCity(), b.getState(), b.getStock()});
            }
        } catch (Exception e) {
            showError(e);
        }
    }

    private void showError(Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}


