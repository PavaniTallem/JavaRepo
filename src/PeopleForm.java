import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PeopleForm extends JFrame {
    private JPanel peoplePanel;
    private JTextField P_IdText;
    private JTextField P_NameText;
    private JTextField P_AddressText;
    private JTextField D_IdText;
    private JTextArea peopleResults;
    private JButton selectButton;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;

    public PeopleForm() {
        add(peoplePanel);
        setTitle("People Screen");
        setSize(400, 500);
        this.setLocationRelativeTo(null);


        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();

                String selectSql = "SELECT * FROM people order by P_Id asc ;";
                String final_result = null;
                String col_names = null;
                peopleResults.setText("");
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
                            peopleResults.append(col_names);
                        }
                        peopleResults.append("\n");
                        peopleResults.append(final_result);
                        final_result = null;
                    }
                } catch (Exception e1) {
                    System.out.println(e1);
                    peopleResults.append(e1.toString());
                }
            }
        });
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();

                String p_id = P_IdText.getText();
                String p_name = P_NameText.getText();
                String p_addr = P_AddressText.getText();
                String p_d_id = D_IdText.getText();


                String insertSql = "INSERT INTO people VALUES ( ";
                peopleResults.setText("");
                if (p_id == null || p_addr == null || p_d_id == null || p_name== null ) {
                    System.out.println("CAN NOT INSERT INTO PEOPLE TABLE WITH NULL VALUES.");
                    peopleResults.setText("CAN NOT INSERT INTO PEOPLE TABLE WITH NULL VALUES.");
                } else {
                    peopleResults.setText("");
                    insertSql += p_id + ", " + "'" + p_name + "'" + ", " + "'" + p_addr + "'" + ", " + p_d_id + ");";
                    peopleResults.append(insertSql);
                    try{
                        PreparedStatement preparedStatement = con.prepareStatement(insertSql);
                        if (preparedStatement.executeUpdate() > 0) {
                            System.out.println("New Row Inserted successfully");
                            peopleResults.setText("New Row Inserted successfully");
                            selectButton.doClick();
                        }
                    } catch (Exception e1) {
                        System.out.println(e1);
                        peopleResults.append(e1.toString());
                    }
                }

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();

                String p_id = P_IdText.getText();
                String p_name = P_NameText.getText();
                String p_addr = P_AddressText.getText();
                String p_d_id = D_IdText.getText();


                String updateSql = "UPDATE people SET ";
                peopleResults.setText("");
                if (p_id == null && (p_addr == null || p_d_id == null || p_name == null )) {
                    System.out.println("CAN NOT UPDATE INTO PEOPLE TABLE WITH NULL PRIMARY KEY AND NOTHING TO UPDATE WITH.");
                    peopleResults.setText("CAN NOT UPDATE INTO PEOPLE TABLE WITH NULL PRIMARY KEY AND NOTHING TO UPDATE WITH.");
                } else {
                    peopleResults.setText("");
                    if (p_name != "") {
                        updateSql += "P_Name = " + "'" + p_name + "'" ;
                    }
                    if (p_addr.length() > 5) {
                        updateSql += "P_Address = " + "'" + p_addr + "'" + ",";
                    }
                    if (p_d_id.length() > 5) {
                        updateSql += "D_Id = " + p_d_id + ",";
                    }
                    //removing last character which will be a comma
                    updateSql.substring(0, updateSql.length() - 1);
                    updateSql += " WHERE P_Id= " + p_id;

                    peopleResults.append(updateSql);

                    try {
                        PreparedStatement preparedStatement = con.prepareStatement(updateSql);
                        if (preparedStatement.executeUpdate() > 0) {
                            System.out.println("Table Updated successfully");
                            peopleResults.setText("Table Updated successfully");
                            selectButton.doClick();
                        }
                    } catch (Exception e1) {
                        System.out.println(e1);
                        peopleResults.append(e1.toString());
                    }

                }


            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DatabaseConnection connection = new DatabaseConnection();
                Connection con = connection.get_connection();

                String deleteSql = "DELETE FROM people where P_ID = ";
                String p_id = P_IdText.getText();
                peopleResults.setText("");
                try {
                    if( p_id == null || p_id == "") {
                        System.out.println("CAN NOT INSERT INTO PEOPLE TABLE WITH NULL VALUES.");
                        peopleResults.setText("CAN NOT INSERT INTO PEOPLE TABLE WITH NULL VALUES.");
                    } else {
                        peopleResults.setText("");
                        deleteSql += p_id + ";";
                        PreparedStatement preparedStatement = con.prepareStatement(deleteSql);
                        if (preparedStatement.executeUpdate() > 0) {
                            System.out.println("Row deleted successfully");
                            peopleResults.setText("Row deleted successfully");
                            selectButton.doClick();
                        }
                    }

                } catch (Exception e1) {
                    System.out.println(e1);
                    peopleResults.append(e1.toString());
                }
            }
        });
    }
}
