import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PlaceForm extends JFrame{
    private JPanel placePanel;
    private JTextField enterPL_IdTextField;
    private JTextField enterPL_NameTextField;
    private JTextField enterPL_P_IdTextField;
    private JTextField enterPL_AddressTextField;
    private JTextArea placesResults;
    private JButton selectButton;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;

    public PlaceForm() {
        add(placePanel);
        setTitle("Places Screen");
        setSize(400, 500);
        this.setLocationRelativeTo(null);


        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();

                String selectSql = "SELECT * FROM place order by PL_Id ASC;";
                String final_result = null;
                String col_names = null;
                placesResults.setText("");
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
                            placesResults.append(col_names);
                        }
                        placesResults.append("\n");
                        placesResults.append(final_result);
                        final_result = null;
                    }
                } catch (Exception e1) {
                    System.out.println(e1);
                    placesResults.append(e1.toString());
                }

            }
        });
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();
                String pl_id = enterPL_IdTextField.getText();
                String pl_name = enterPL_NameTextField.getText();
                String pl_p_id = enterPL_P_IdTextField.getText();
                String pl_addr = enterPL_AddressTextField.getText();


                String insertSql = "INSERT INTO place VALUES ( ";
                placesResults.setText("");
                if (pl_id == null || pl_p_id == null || pl_addr == null || pl_name== null ) {
                    System.out.println("CAN NOT INSERT INTO PEOPLE TABLE WITH NULL VALUES.");
                    placesResults.setText("CAN NOT INSERT INTO PEOPLE TABLE WITH NULL VALUES.");
                } else {
                    placesResults.setText("");
                    insertSql += "'" + pl_id + "'" + ", " + "'" + pl_name + "'" + ", " + pl_p_id + ", " + "'" + pl_addr + "'" + ");";
                    try{
                        PreparedStatement preparedStatement = con.prepareStatement(insertSql);
                        if (preparedStatement.executeUpdate() > 0) {
                            System.out.println("New Row Inserted successfully");
                            placesResults.setText("New Row Inserted successfully");
                            selectButton.doClick();
                        }
                    } catch (Exception e1) {
                        System.out.println(e1);
                        placesResults.append(e1.toString());
                    }
                }

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();
                String pl_id = enterPL_IdTextField.getText();
                String pl_name = enterPL_NameTextField.getText();
                String pl_p_id = enterPL_P_IdTextField.getText();
                String pl_addr = enterPL_AddressTextField.getText();


                String updateSql = "UPDATE place SET ";
                placesResults.setText("");
                if (pl_id == null && pl_p_id == null && (pl_addr == null || pl_name == null )) {
                    System.out.println("CAN NOT UPDATE INTO PEOPLE TABLE WITH NULL PRIMARY KEY AND NOTHING TO UPDATE WITH.");
                    placesResults.setText("CAN NOT UPDATE INTO PEOPLE TABLE WITH NULL PRIMARY KEY AND NOTHING TO UPDATE WITH.");
                } else {
                    placesResults.setText("");
                    if (pl_name != "") {
                        updateSql += "PL_Name = " + "'" + pl_name + "'";
                    }
                    if (pl_p_id.length() > 5) {
                        updateSql += "P_Id = " + pl_p_id + ",";
                    }
                    if (pl_addr.length() > 5) {
                        updateSql += "PL_Address = " + "'" + pl_addr + "'" + ",";
                    }
                    //removing last character which will be a comma
                    updateSql.substring(0, updateSql.length() - 1);
                    updateSql += " WHERE PL_Id= " + "'" + pl_id + "'" ;

                    try {
                        PreparedStatement preparedStatement = con.prepareStatement(updateSql);
                        if (preparedStatement.executeUpdate() > 0) {
                            System.out.println("Table Updated successfully");
                            placesResults.setText("Table Updated successfully");
                            selectButton.doClick();
                        }
                    } catch (Exception e1) {
                        System.out.println(e1);
                        placesResults.append(e1.toString());
                    }

                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();

                String deleteSql = "DELETE FROM place where PL_Id = ";
                String pl_id = enterPL_IdTextField.getText();
                placesResults.setText("");
                try {
                    if( pl_id == null || pl_id == "") {
                        System.out.println("PRIMARY KEY CAN NOT BE NULL WHEN DELETING.");
                        placesResults.setText("PRIMARY KEY CAN NOT BE NULL WHEN DELETING.");
                    } else {
                        placesResults.setText("");
                        deleteSql += "'" + pl_id + "' ;";
                        PreparedStatement preparedStatement = con.prepareStatement(deleteSql);
                        if (preparedStatement.executeUpdate() > 0) {
                            System.out.println("Row deleted successfully");
                            placesResults.setText("Row deleted successfully");
                            selectButton.doClick();
                        }
                    }

                } catch (Exception e1) {
                    System.out.println(e1);
                    placesResults.append(e1.toString());
                }

            }
        });
    }
}
