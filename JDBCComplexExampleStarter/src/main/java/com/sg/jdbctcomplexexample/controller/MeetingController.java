package com.sg.jdbctcomplexexample.controller;

import com.sg.jdbctcomplexexample.dao.EmployeeDao;
import com.sg.jdbctcomplexexample.dao.MeetingDao;
import com.sg.jdbctcomplexexample.dao.RoomDao;
import com.sg.jdbctcomplexexample.entity.Employee;
import com.sg.jdbctcomplexexample.entity.Meeting;
import com.sg.jdbctcomplexexample.entity.Room;
import com.sg.jdbctcomplexexample.view.MeetingView;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author kylerudy
 */
@Component
public class MeetingController {

    @Autowired
    MeetingView view;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    MeetingDao meetingDao;

    @Autowired
    RoomDao roomDao;

    /**
     * App controller
     */
    public void run() {
        while (true) {
            try {
                view.displayProgramBanner();
                view.displayMenu();

                int choice = view.getMenuChoice(4);
                switch (choice) {
                    case 1: //Meetings
                        handleMeetings();
                        break;
                    case 2: //Rooms
                        handleRooms();
                        break;
                    case 3: //Employees
                        handleEmployees();
                        break;
                    case 4: //Exit
                        view.exit();
                        System.exit(0);
                }
            } catch (Exception ex) {
                view.printError(ex);
            }
        }
    }

    /**
     * Meetings sub-controller
     */
    private void handleMeetings() {
        while(true) {
            view.displayMeetingBanner();
            view.displayMeetingMenu();
            
            int choice = view.getMenuChoice(5);
            
            switch(choice) {
                case 1: //List Meeting
                    listMeeting();
                    break;
                case 2: //Add Meeting
                    addMeeting();
                    break;
                case 3: //Update Meeting
                    updateMeeting();
                    break;
                case 4: //Delete Meeting
                    deleteMeeting();
                    break;
                case 5: //Return to Main Menu
                    view.returnToMainMenu();
                    return;
            }
        }
    }

    /**
     * Rooms sub-controller
     */
    private void handleRooms() {
        while(true) {
            view.displayRoomBanner();
            view.displayRoomMenu();
            
            int choice = view.getMenuChoice(6);

            switch (choice) {
                case 1: //List Room
                    listRooms();
                    break;
                case 2: //Add Room
                    addRoom();
                    break;
                case 3: //Update Room
                    updateRoom();
                    break;
                case 4: //Delete Room
                    deleteRoom();
                    break;
                case 5: //List Meetings for Room
                    listMeetingsForRoom();
                    break;
                case 6: //Return to Main Menu
                    view.returnToMainMenu();
                    return;
            }
        }
    }

    /**
     * Employees sub-controller
     */
    private void handleEmployees() {
        while (true) {
            view.displayEmployeesBanner();
            view.displayEmployeesMenu();

            int choice = view.getMenuChoice(7);

            switch (choice) {
                case 1: //List Employees
                    listEmployees();
                    break;
                case 2: //Add Employee
                    addEmployee();
                    break;
                case 3: //Update Employee
                    updateEmployee();
                    break;
                case 4: //Delete Employee
                    deleteEmployee();
                    break;
                case 5: //List Meetings for Employee
                    listMeetingsForEmployee();
                    break;
                case 6:
                    addEmployeeToMeeting();
                    break;
                case 7: //Return to Main Menu
                    view.returnToMainMenu();
                    return;
            }
        }
    }

    /**
     * List all employees from db
     */
    private void listEmployees() {
        view.listEmployeesBanner();
        List<Employee> employees = employeeDao.getAllEmployees();
        view.listEmployees(employees);
    }

    /**
     * Add new employee
     */
    private void addEmployee() {
        view.addEmployeeBanner();
        
        String firstName = view.getEmployeeFirstName();
        String lastName = view.getEmployeeLastName();
        
        Employee employee = new Employee(); //d.ctor
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        
        employee = employeeDao.addEmployee(employee);
        view.addEmployeeSuccess();
    }

    /**
     * Update info on an existing employee
     */
    private void updateEmployee() {
        view.updateEmployeeBanner();
        
        int id = view.getEmployeeId();
        Employee employee = employeeDao.getEmployeeById(id);
        if (employee != null) {
            view.displayUpdateInstructions();
            
            String firstName = view.updateField("First Name", employee.getFirstName());
            String lastName = view.updateField("Last Name", employee.getLastName());
            
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            
            employeeDao.updateEmployee(employee);
            view.updateEmployeeSuccess();
        } else {
            view.invalidEmployee();
        }
    }

    /**
     * Delete employee from db
     */
    private void deleteEmployee() {
        view.deleteEmployeeBanner();
        
        int id = view.getEmployeeId();
        Employee employee = employeeDao.getEmployeeById(id);
        if (employee != null) {
            employeeDao.deleteEmployeeById(id);
            view.deleteEmployeeSuccess();
        } else {
            view.invalidEmployee();
        }
    }

    /**
     * List all meetings for a particular employee
     */
    private void listMeetingsForEmployee() {
        view.listMeetingsForEmployeeBanner();
        
        int id = view.getEmployeeId();
        Employee employee = employeeDao.getEmployeeById(id);
        if (employee != null) {
            view.displayEmployee(employee);
            List<Meeting> meetings = meetingDao.getMeetingsForEmployee(employee);
            view.displayMeetings(meetings);
        } else {
            view.invalidEmployee();
        }
    }

    /**
     * Add a employee to meeting
     */
    private void addEmployeeToMeeting() {
        view.addEmployeToMeetingBanner();
        
        int id = view.getEmployeeId();
        Employee employee = employeeDao.getEmployeeById(id);
        if (employee != null) {
            List<Meeting> meetings = meetingDao.getAllMeetings();
            view.displayMeetings(meetings);
            int meetingId = view.getMeetingIdToJoin();
            Meeting meeting = meetingDao.getMeetingByid(meetingId);
            
            if(!meeting.getAttendees().contains(employee)) {
                meeting.getAttendees().add(employee); //add to meeting list
                meetingDao.updateMeeting(meeting);
            }
            view.addEmployeeToMeetingSuccess();
        } else {
            view.invalidEmployee();
        }
    }

    /**
     * List all active rooms in db
     */
    private void listRooms() {
        view.listRoomsBanner();
        List<Room> rooms = roomDao.getAllRooms();
        view.displayRooms(rooms);
    }

    /**
     * Add a room to db
     */
    private void addRoom() {
        view.addRoomBanner();
        
        String name = view.getRoomName();
        String description = view.getRoomDescription();
        
        Room room = new Room();
        room.setName(name);
        room.setDescription(description);
        room = roomDao.addRoom(room);
        
        view.addRoomSuccess();
    }

    /**
     * Update an existing room in db
     */
    private void updateRoom() {
        view.updateRoomBanner();
        
        int id = view.getRoomId();
        Room room = roomDao.getRoomById(id);
        if (room != null) {
            view.displayUpdateInstructions();
            
            String name = view.updateField("Name", room.getName());
            String description = view.updateField("Description", room.getDescription());
            room.setName(name);
            room.setDescription(description);
            roomDao.updateRoom(room);
            
            view.updateRoomSuccess();
        } else {
            view.invalidRoom();
        }
    }

    /**
     * Delete a room from db
     */
    private void deleteRoom() {
        view.deleteRoomBanner();
        
        int id = view.getRoomId();
        Room room = roomDao.getRoomById(id);
        if (room != null) {
            roomDao.deleteRoomById(id);
            view.deleteRoomSuccess();
        } else {
            view.invalidRoom();
        }
    }

    /**
     * List all meetings for a given room
     */
    private void listMeetingsForRoom() {
        view.listMeetingsForRoomBanner();
        
        int id = view.getRoomId();
        Room room = roomDao.getRoomById(id);
        if (room != null) {
            view.displayRoom(room);
            List<Meeting> meetings = meetingDao.getMeetingsForRoom(room);
            view.displayMeetings(meetings);
        } else {
            view.invalidRoom();
        }
    }

    /**
     * List all meetings
     */
    private void listMeeting() {
        view.listMeetingsBanner();
        List<Meeting> meetings = meetingDao.getAllMeetings();
        view.displayMeetings(meetings);
    }

    /**
     * Add a meeting
     */
    private void addMeeting() {
        view.addMeetingBanner();
        
        String name = view.getMeetingName();
        LocalDateTime time = view.getMeetingDatetime();
        List<Room> rooms = roomDao.getAllRooms();
        
        view.displayRooms(rooms);
        int id = view.getMeetingRoomId();
        Room room = roomDao.getRoomById(id);
        
        Meeting meeting = new Meeting();
        meeting.setName(name);
        meeting.setRoom(room);
        meeting.setTime(time);        
        meetingDao.addMeeting(meeting);
        view.addMeetingSuccess();
    }

    /**
     * Update meeting info
     */
    private void updateMeeting() {
        view.updateMeetingBanner();
        
        int id = view.getMeetingId();
        Meeting meeting = meetingDao.getMeetingByid(id);
        if (meeting != null) {
            view.displayUpdateInstructions();
            String name = view.updateField("Name", meeting.getName());
            LocalDateTime datetime = view.updateMeetingTime(meeting.getTime());
            
            List<Room> rooms = roomDao.getAllRooms();
            view.displayRooms(rooms);
            int roomId = Integer.parseInt(view.updateField("Room ID", "" + meeting.getRoom().getId()));
            
            Room room = roomDao.getRoomById(roomId);
            meeting.setName(name);
            meeting.setTime(datetime);
            meeting.setRoom(room);
            
            meetingDao.updateMeeting(meeting);
            
            view.updateMeetingSuccess();
        } else {
            view.invalidMeeting();
        }
    }

    /**
     * Delete a meeting from db
     */
    private void deleteMeeting() {
        view.deleteMeetingBanner();
        
        int id = view.getMeetingId();
        Meeting meeting = meetingDao.getMeetingByid(id);
        if (meeting != null) {
            meetingDao.deleteMeetingById(id);
            view.deleteMeetingSuccess();
        } else {
            view.invalidMeeting();
        }
    }
}
