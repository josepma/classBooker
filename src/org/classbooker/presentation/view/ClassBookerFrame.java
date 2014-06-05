package org.classbooker.presentation.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JLabel;

import org.classbooker.service.*;
import org.classbooker.presentation.controller.*;

/**
 *
 * @author josepma
 *
 *
 *
 */
public class ClassBookerFrame extends JFrame {

    private JMenuBar menubar;
    private JPanel content;

    private ReservationMgrService resService;
    private SpaceMgrService spaceService;
    private StaffMgrService staffService;

    public ClassBookerFrame(ReservationMgrService resService, SpaceMgrService spaceService, StaffMgrService staffService) {

        super("ClassBooker space reservation application");
        this.resService = resService;
        this.spaceService = spaceService;
        this.staffService = staffService;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.setSize(600, 600);

        createMenuBar();
        createContents();

        this.setVisible(true);

    }

    private void createContents() {

        content = new JPanel();

        content.add(new JLabel("Welcome to ClassBooker"));

        this.getContentPane().add(content, BorderLayout.CENTER);

    }

    private void createMenuBar() {
        menubar = new JMenuBar();

        createSpacesMenu();
        createStaffMenu();
        createReservationsMenu();

        this.setJMenuBar(menubar);
    }

    private void createSpacesMenu() {
        JMenu spacemenu = new JMenu("Spaces");
        spacemenu.add(new JSeparator());

        JMenuItem spaceItem1 = new JMenuItem("New building");
        spaceItem1.addActionListener(new BuildingInsertionAction(this, spaceService));
        //We associate the "New building" button of the menu to the 
        // BuildingInsertionAction controller action.
        //This is not ideal. It would be better to decouple completely the 
        //view (e.g., this ClassBookerFrame class) and the controller (BuildingInsertionAction).
        //However, the overall design would be a bit more complicated (but more reusable).

        JMenuItem spaceItem2 = new JMenuItem("New room");
        spaceItem2.addActionListener(new RoomInsertionAction(this, spaceService));

        JMenuItem spaceItem3 = new JMenuItem("List buildings");
        //spaceItem3.addActionListener(new BuildingListAction(this, spaceService));

        spacemenu.add(spaceItem1);
        spacemenu.add(spaceItem2);
        spacemenu.add(spaceItem3);
        menubar.add(spacemenu);

    }

    private void createStaffMenu() {
        JMenu staffmenu = new JMenu("Staff");
        staffmenu.add(new JSeparator());

        JMenuItem staffItem1 = new JMenuItem("Add user");
        staffItem1.addActionListener(new UserInsertionAction(this, staffService));

        JMenuItem staffItem2 = new JMenuItem("Add masive users");
        staffItem2.addActionListener(new MassiveUserInsertionAction(this, staffService));

        staffmenu.add(staffItem1);
        staffmenu.add(staffItem2);
        menubar.add(staffmenu);
    }

    private void createReservationsMenu() {
        JMenu reservationsmenu = new JMenu("Reservations");
        reservationsmenu.add(new JSeparator());

        
        JMenuItem reservationItem1 = new JMenuItem("Find user reservations");
     //   reservationItem1.addActionListener(new ReservationFindAction(this, resService));
        
        JMenuItem reservationItem2 = new JMenuItem("Make reservation by type");
//        reservationItem2.addActionListener(new ReservationFindAction(this, resService));
        
        reservationsmenu.add(reservationItem1);
        reservationsmenu.add(reservationItem2);
        menubar.add(reservationsmenu);
    }

}
