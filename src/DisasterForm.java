import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DisasterForm extends JFrame{
    private JTextField enterD_IdTextField;
    private JTextField enterD_DateYYYYMMTextField;
    private JTextField enterD_nameTextField;
    private JTextField enterD_DiseaseTextField;
    private JTextField enterD_TypeTextField;
    private JTextArea disasterResults;
    private JButton selectButton;
    private JButton insertButton;
    private JButton updateButton;
    private JPanel disasterPanel;
    private JButton deleteButton;

    public DisasterForm() {
        add(disasterPanel);
        setTitle("Disaster Screen");
        setSize(400, 500);
        this.setLocationRelativeTo(null);

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();

                String selectSql = "SELECT * FROM disaster;";
                String final_result = null;
                String col_names = null;
                disasterResults.setText("");
                try {
                    Statement statement = con.createStatement();
                    ResultSet resultSet = statement.executeQuery(selectSql);
                    int j = 0;
                    while (resultSet.next()) {
                        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                        int colCount = resultSetMetaData.getColumnCount();
                        for (int i = 1; i <= colCount; i++) {
                            col_names += "\t" + resultSetMetaData.getColumnName(i) + "\t";
                            final_result += "\t" + resultSet.getString(i) + "\t";
                        }
                        j++;
                        if (j==1) {
                            disasterResults.append(col_names);
                        }
                        disasterResults.append("\n");
                        disasterResults.append(final_result);
                        final_result = null;
                    }
                } catch (Exception e2) {
                    System.out.println(e2);
                    disasterResults.append(e2.toString());
                }


            }
        });
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();

                String d_id = enterD_IdTextField.getText();
                String d_name = enterD_nameTextField.getText();
                String d_date = enterD_DateYYYYMMTextField.getText();
                String d_disease = enterD_DiseaseTextField.getText();
                String d_type = enterD_TypeTextField.getText();


                String insertSql = "INSERT INTO disaster VALUES ( ";
                disasterResults.setText("");
                if (d_id == null || d_date == null || d_disease == null || d_name == null || d_type == null) {
                    System.out.println("CAN NOT INSERT INTO DISASTER TABLE WITH NULL VALUES.");
                    disasterResults.setText("CAN NOT INSERT INTO DISASTER TABLE WITH NULL VALUES.");
                } else {
                    disasterResults.setText("");
                    insertSql += d_id + ", " + d_name + ", " + d_date + ", " + d_disease +", " + d_type + ");";
                    try{
                        PreparedStatement preparedStatement = con.prepareStatement(insertSql);
                        if (preparedStatement.executeUpdate() > 0) {
                            System.out.println("New Row Inserted successfully");
                            disasterResults.setText("New Row Inserted successfully");
                            selectButton.doClick();
                        }
                    } catch (Exception e2) {
                        System.out.println(e2);
                        disasterResults.append(e2.toString());
                    }
                }


            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();

                String d_id = enterD_IdTextField.getText();
                String d_name = enterD_nameTextField.getText();
                String d_date = enterD_DateYYYYMMTextField.getText();
                String d_disease = enterD_DiseaseTextField.getText();
                String d_type = enterD_TypeTextField.getText();


                String updateSql = "UPDATE disaster SET ";
                disasterResults.setText("");
                if (d_id == null && (d_date == null || d_disease == null || d_name== null || d_type == null)) {
                    System.out.println("CAN NOT UPDATE INTO DISASTER TABLE WITH NULL PRIMARY KEY AND NOTHING TO UPDATE WITH.");
                    disasterResults.setText("CAN NOT UPDATE INTO DISASTER TABLE WITH NULL PRIMARY KEY AND NOTHING TO UPDATE WITH.");
                } else {
                    disasterResults.setText("");
                    if (d_name != null) {
                        updateSql += "D_Name = " + d_name + ",";
                    }
                    if (d_date != null) {
                        updateSql += "D_Date = " + d_date + ",";
                    }
                    if (d_disease != null) {
                        updateSql += "D_Disease = " + d_disease + ",";
                    }
                    if (d_type != null) {
                        updateSql += "D_Type = " + d_type + ",";
                    }

                    //removing last character which will be a comma
                    updateSql.substring(0, updateSql.length() - 1);
                    updateSql += " WHERE D_Id= " + d_id;

                    try {
                        PreparedStatement preparedStatement = con.prepareStatement(updateSql);
                        if (preparedStatement.executeUpdate() > 0) {
                            System.out.println("Table Updated successfully");
                            disasterResults.setText("Table Updated successfully");
                            selectButton.doClick();
                        }
                    } catch (Exception e2) {
                        System.out.println(e2);
                        disasterResults.append(e2.toString());
                    }

                }

            }
        });
    }
}
