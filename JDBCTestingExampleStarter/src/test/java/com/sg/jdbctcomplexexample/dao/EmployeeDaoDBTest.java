package com.sg.jdbctcomplexexample.dao;

import com.sg.jdbctcomplexexample.TestApplicationConfiguration;
import com.sg.jdbctcomplexexample.entity.Employee;
import com.sg.jdbctcomplexexample.entity.Meeting;
import com.sg.jdbctcomplexexample.entity.Room;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author naris
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class EmployeeDaoDBTest {
    
    @Autowired
    RoomDao roomDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    MeetingDao meetingDao;
    
    public EmployeeDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Room> rooms = roomDao.getAllRooms();
        for (Room room : rooms) {
            roomDao.deleteRoomById(room.getId());
        }

        List<Employee> employees = employeeDao.getAllEmployees();
        for (Employee employee : employees) {
            employeeDao.deleteEmployeeById(employee.getId());
        }

        List<Meeting> meetings = meetingDao.getAllMeetings();
        for (Meeting meeting : meetings) {
            meetingDao.deleteMeetingById(meeting.getId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAllEmployees method, of class EmployeeDaoDB.
     */
    @Test
    public void testGetAllEmployees() {
        //arramge
        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);
        
        Employee employee2 = new Employee();
        employee2.setFirstName("Test First 2");
        employee2.setLastName("Test Last 2");
        employee2 = employeeDao.addEmployee(employee2);
        
        //act
        List<Employee> employees = employeeDao.getAllEmployees();
        
        //assert
        assertEquals(2, employees.size());
        assertTrue(employees.contains(employee));
        assertTrue(employees.contains(employee2));
    }

    /**
     * Test of addEmployee method, of class EmployeeDaoDB.
     */
    @Test
    public void testAddGetEployee() {
        //arrange
        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        
        //act
        employee = employeeDao.addEmployee(employee);
        
        Employee fromDao = employeeDao.getEmployeeById(employee.getId());
        
        //arrange
        assertEquals(employee, fromDao);
    }

    /**
     * Test of updateEmployee method, of class EmployeeDaoDB.
     */
    @Test
    public void testUpdateEmployee() {
        //arrange
        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);
        
        Employee fromDao = employeeDao.getEmployeeById(employee.getId());
        
        assertEquals(employee, fromDao);
        
        //act
        employee.setFirstName("Another Test First");
        
        employeeDao.updateEmployee(employee);
        
        //assert
        assertNotEquals(employee, fromDao);
        
        fromDao = employeeDao.getEmployeeById(employee.getId());
        
        assertEquals(employee, fromDao);
    }

    /**
     * Test of deleteEmployeeById method, of class EmployeeDaoDB.
     */
    @Test
    public void testDeleteEmployeeById() {
        //arrange
        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);
        
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);
        
        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now());
        meeting.setRoom(room);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        meeting.setAttendees(employees);
        meeting = meetingDao.addMeeting(meeting);
        
        //act
        employeeDao.deleteEmployeeById(employee.getId());
        
        Employee fromDao = employeeDao.getEmployeeById(employee.getId());
        
        //assert
        assertNull(fromDao);
    }
    
}
