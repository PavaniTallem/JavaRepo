import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CountriesForm extends JFrame{
    private JPanel countriesPanel;
    private JTextField enterC_IdTextField;
    private JTextField enterC_NameTextField;
    private JTextField enterC_P_IdTextField;
    private JTextField enterC_D_TypeTextField;
    private JTextField enterC_D_NameTextField;
    private JTextField enterC_D_IdTextField;
    private JTextArea countriesResults;
    private JButton selectButton;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;

    public CountriesForm() {
        add(countriesPanel);
        setTitle("Countries Screen");
        setSize(400, 500);
        this.setLocationRelativeTo(null);

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();

                String selectSql = "SELECT * FROM countries;";
                String final_result = null;
                String col_names = null;
                countriesResults.setText("");
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
                            countriesResults.append(col_names);
                        }
                        countriesResults.append("\n");
                        countriesResults.append(final_result);
                        final_result = null;
                    }
                } catch (Exception e3) {
                    System.out.println(e3);
                    countriesResults.append(e3.toString());
                }

            }
        });
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();
                String c_id = enterC_IdTextField.getText();
                String c_name = enterC_NameTextField.getText();
                String c_p_id = enterC_P_IdTextField.getText();
                String c_d_type = enterC_D_TypeTextField.getText();
                String c_d_name = enterC_D_NameTextField.getText();
                String c_d_id = enterC_D_IdTextField.getText();


                String insertSql = "INSERT INTO countries VALUES ( ";
                countriesResults.setText("");
                if (c_id == null || c_p_id == null || c_d_type == null || c_name == null || c_d_name == null || c_d_id == null) {
                    System.out.println("CAN NOT INSERT INTO COUNTRIES TABLE WITH NULL VALUES.");
                    countriesResults.setText("CAN NOT INSERT INTO COUNTRIES TABLE WITH NULL VALUES.");
                } else {
                    countriesResults.setText("");
                    insertSql += c_id + ", " + c_name + ", " + c_p_id + ", " + c_d_type +", " + c_d_name + ", " + c_d_id + ");";
                    try{
                        PreparedStatement preparedStatement = con.prepareStatement(insertSql);
                        if (preparedStatement.executeUpdate() > 0) {
                            System.out.println("New Row Inserted successfully");
                            countriesResults.setText("New Row Inserted successfully");
                            selectButton.doClick();
                        }
                    } catch (Exception e3) {
                        System.out.println(e3);
                        countriesResults.append(e3.toString());
                    }
                }


            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();
                String c_id = enterC_IdTextField.getText();
                String c_name = enterC_NameTextField.getText();
                String c_p_id = enterC_P_IdTextField.getText();
                String c_d_type = enterC_D_TypeTextField.getText();
                String c_d_name = enterC_D_NameTextField.getText();
                String c_d_id = enterC_D_IdTextField.getText();


                String updateSql = "UPDATE countries SET ";
                countriesResults.setText("");
                if (c_id == null && c_d_id == null && c_p_id == null && (c_d_type == null || c_name== null || c_d_name == null)) {
                    System.out.println("CAN NOT UPDATE INTO COUNTRIES TABLE WITH NULL PRIMARY KEY AND NOTHING TO UPDATE WITH.");
                    countriesResults.setText("CAN NOT UPDATE INTO COUNTRIES TABLE WITH NULL PRIMARY KEY AND NOTHING TO UPDATE WITH.");
                } else {
                    countriesResults.setText("");
                    if (c_name != null) {
                        updateSql += "D_Name = " + c_name + ",";
                    }
                    if (c_p_id != null) {
                        updateSql += "D_Date = " + c_p_id + ",";
                    }
                    if (c_d_type != null) {
                        updateSql += "D_Disease = " + c_d_type + ",";
                    }
                    if (c_d_name != null) {
                        updateSql += "D_Type = " + c_d_name + ",";
                    }
                    if (c_d_id != null) {
                        updateSql += "D_Id = " + c_d_id + ",";
                    }

                    //removing last character which will be a comma
                    updateSql.substring(0, updateSql.length() - 1);
                    updateSql += " WHERE C_Id= " + c_id;

                    try {
                        PreparedStatement preparedStatement = con.prepareStatement(updateSql);
                        if (preparedStatement.executeUpdate() > 0) {
                            System.out.println("Table Updated successfully");
                            countriesResults.setText("Table Updated successfully");
                            selectButton.doClick();
                        }
                    } catch (Exception e3) {
                        System.out.println(e3);
                        countriesResults.append(e3.toString());
                    }

                }

            }
        });
    }
}
