import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DeathForm extends JFrame{
    private JPanel deathPanel;
    private JTextField enterDe_IdTextField;
    private JTextField enterDe_P_IdTextField;
    private JTextField enterDe_D_IdTextField;
    private JTextField enterDe_C_IdTextField;
    private JTextField enterDe_P_AddressTextField;
    private JTextArea deathResults;
    private JButton selectButton;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;

    public DeathForm() {
        add(deathPanel);
        setTitle("Death Screen");
        setSize(400, 500);
        this.setLocationRelativeTo(null);


        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();

                String selectSql = "SELECT * FROM death;";
                String final_result = null;
                String col_names = null;
                deathResults.setText("");
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
                            deathResults.append(col_names);
                        }
                        deathResults.append("\n");
                        deathResults.append(final_result);
                        final_result = null;
                    }
                } catch (Exception e4) {
                    System.out.println(e4);
                    deathResults.append(e4.toString());
                }


            }
        });
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();
                String de_id = enterDe_IdTextField.getText();
                String de_p_id = enterDe_P_IdTextField.getText();
                String de_d_id = enterDe_D_IdTextField.getText();
                String de_c_id = enterDe_C_IdTextField.getText();
                String de_p_addr = enterDe_P_AddressTextField.getText();


                String insertSql = "INSERT INTO death VALUES ( ";
                deathResults.setText("");
                if (de_id == null || de_d_id == null || de_c_id == null || de_p_id == null || de_p_addr == null) {
                    System.out.println("CAN NOT INSERT INTO DEATH TABLE WITH NULL VALUES.");
                    deathResults.setText("CAN NOT INSERT INTO DEATH TABLE WITH NULL VALUES.");
                } else {
                    deathResults.setText("");
                    insertSql += de_id + ", " + de_p_id + ", " + de_d_id + ", " + de_c_id +", " + de_p_addr + ");";
                    try{
                        PreparedStatement preparedStatement = con.prepareStatement(insertSql);
                        if (preparedStatement.executeUpdate() > 0) {
                            System.out.println("New Row Inserted successfully");
                            deathResults.setText("New Row Inserted successfully");
                            selectButton.doClick();
                        }
                    } catch (Exception e4) {
                        System.out.println(e4);
                        deathResults.append(e4.toString());
                    }
                }


            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();
                String de_id = enterDe_IdTextField.getText();
                String de_p_id = enterDe_P_IdTextField.getText();
                String de_d_id = enterDe_D_IdTextField.getText();
                String de_c_id = enterDe_C_IdTextField.getText();
                String de_p_addr = enterDe_P_AddressTextField.getText();


                String updateSql = "UPDATE death SET ";
                deathResults.setText("");
                if (de_id == null && de_d_id == null && de_c_id == null && de_p_id== null || de_p_addr == null) {
                    System.out.println("CAN NOT UPDATE INTO DEATH TABLE WITH NULL PRIMARY KEY AND NOTHING TO UPDATE WITH.");
                    deathResults.setText("CAN NOT UPDATE INTO DEATH TABLE WITH NULL PRIMARY KEY AND NOTHING TO UPDATE WITH.");
                } else {
                    deathResults.setText("");
                    if (de_p_id != null) {
                        updateSql += "P_Id = " + de_p_id + ",";
                    }
                    if (de_d_id != null) {
                        updateSql += "D_Id = " + de_d_id + ",";
                    }
                    if (de_c_id != null) {
                        updateSql += "C_Id = " + de_c_id + ",";
                    }
                    if (de_p_addr != null) {
                        updateSql += "P_Address = " + de_p_addr + ",";
                    }

                    //removing last character which will be a comma
                    updateSql.substring(0, updateSql.length() - 1);
                    updateSql += " WHERE D_Id= " + de_id;

                    try {
                        PreparedStatement preparedStatement = con.prepareStatement(updateSql);
                        if (preparedStatement.executeUpdate() > 0) {
                            System.out.println("Table Updated successfully");
                            deathResults.setText("Table Updated successfully");
                            selectButton.doClick();
                        }
                    } catch (Exception e4) {
                        System.out.println(e4);
                        deathResults.append(e4.toString());
                    }

                }

            }
        });
    }
}
