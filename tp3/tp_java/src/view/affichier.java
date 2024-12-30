package view;

import DAO.EmployeDAOimpl;
import model.Employe;
import model.Employemodel;
import model.Type_holiday;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class affichier extends JFrame {

    private JTabbedPane tabbedPane = new JTabbedPane();
    private JPanel employeTab = new JPanel();
    private JPanel holidayTab = new JPanel();
    private JPanel Display_Table_employe = new JPanel();
    private JPanel Display_Table_holiday = new JPanel();

    // le tableau de l'employe
    public static String[] columnNames_employe = {"ID", "Nom", "Prenom", "Email", "Telephone", "Salaire", "Role", "Poste", "solde"};
    public static DefaultTableModel tableModel = new DefaultTableModel(columnNames_employe, 0);
    public static JTable Tableau = new JTable(tableModel);

    // le tableau du congé
    public static String[] columnNames_holiday = {"ID", "nom_employe", "date_debut", "date_fin", "type"};
    public static DefaultTableModel tableModel1 = new DefaultTableModel(columnNames_holiday, 0);
    public static JTable Tableau1 = new JTable(tableModel1);

    public affichier() {
        setTitle("Gestion des employes et des congés");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(tabbedPane);

        // Employe Tab
        employeTab.setLayout(new BorderLayout());
        employeTab.add(Display_Table_employe, BorderLayout.CENTER);
        Tableau.setFillsViewportHeight(true);
        Dimension preferredSize = new Dimension(900, 500);
        Tableau.setPreferredScrollableViewportSize(preferredSize);
        Display_Table_employe.add(new JScrollPane(Tableau));

        // Holiday Tab
        holidayTab.setLayout(new BorderLayout());
        holidayTab.add(Display_Table_holiday, BorderLayout.CENTER);
        Tableau1.setFillsViewportHeight(true);
        Tableau1.setPreferredScrollableViewportSize(preferredSize);
        Display_Table_holiday.add(new JScrollPane(Tableau1));

        // TabbedPane
        tabbedPane.addTab("Employe", employeTab);
        tabbedPane.addTab("Holiday", holidayTab);

        remplaire_les_employes();
        setVisible(true);
    }

    public void remplaire_les_employes() {
        List<Employe> Employes = new Employemodel(new EmployeDAOimpl()).displayEmploye();
        for (Employe elem : Employes) {
            tableModel.addRow(new Object[]{
                    elem.getId(),
                    elem.getNom(),
                    elem.getPrenom(),
                    elem.getEmail(),
                    elem.getTelephone(),
                    elem.getSalaire(),
                    elem.getRole(),
                    elem.getPost(),
                    elem.getSolde() // Assuming 'solde' is a property of Employe
            });
        }
    }

    public static void main(String[] args) {
        new affichier();
    }
}
