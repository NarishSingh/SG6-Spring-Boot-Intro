package com.sg.jdbctcomplexexample.dao;

import com.sg.jdbctcomplexexample.TestApplicationConfiguration;
import com.sg.jdbctcomplexexample.entity.Employee;
import com.sg.jdbctcomplexexample.entity.Meeting;
import com.sg.jdbctcomplexexample.entity.Room;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class MeetingDaoDBTest {

    @Autowired
    RoomDao roomDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    MeetingDao meetingDao;

    public MeetingDaoDBTest() {
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
     * Test of getAllMeetings method, of class MeetingDaoDB.
     */
    @Test
    public void testGetAllMeetings() {
        //arrange
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now().withNano(0));
        meeting.setRoom(room);
        meeting.setAttendees(employees);
        meeting = meetingDao.addMeeting(meeting);

        Meeting meeting2 = new Meeting();
        meeting2.setName("Test Meeting 2");
        meeting2.setTime(LocalDateTime.now().withNano(0));
        meeting2.setRoom(room);
        meeting2.setAttendees(employees);
        meeting2 = meetingDao.addMeeting(meeting2);
        
        //act
        List<Meeting> meetings = meetingDao.getAllMeetings();

        //assert
        assertEquals(2, meetings.size());
        assertTrue(meetings.contains(meeting));
        assertTrue(meetings.contains(meeting2));
    }

    /**
     * Test of addMeeting method, of class MeetingDaoDB.
     */
    @Test
    public void testAddGetMeeting() {
        //arrange
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now().withNano(0));
        meeting.setRoom(room);
        meeting.setAttendees(employees);
        
        //act
        meeting = meetingDao.addMeeting(meeting);

        Meeting fromDao = meetingDao.getMeetingByid(meeting.getId());

        //assert
        assertEquals(meeting, fromDao);
    }

    /**
     * Test of updateMeeting method, of class MeetingDaoDB.
     */
    @Test
    public void testUpdateMeeting() {
        //arrange
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now().withNano(0));
        meeting.setRoom(room);
        meeting.setAttendees(employees);
        meeting = meetingDao.addMeeting(meeting);

        Meeting fromDao = meetingDao.getMeetingByid(meeting.getId());

        assertEquals(meeting, fromDao);

        meeting.setName("Test Meeting 2");

        Employee employee2 = new Employee();
        employee2.setFirstName("Test First 2");
        employee2.setLastName("Test Last 2");
        employee2 = employeeDao.addEmployee(employee2);

        employees.add(employee2);

        meeting.setAttendees(employees);

        //act
        meetingDao.updateMeeting(meeting);

        //assert
        assertNotEquals(meeting, fromDao);

        fromDao = meetingDao.getMeetingByid(meeting.getId());

        assertEquals(meeting, fromDao);
    }

    /**
     * Test of deleteMeetingById method, of class MeetingDaoDB.
     */
    @Test
    public void testDeleteMeetingById() {
        //arrange
        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now().withNano(0));
        meeting.setRoom(room);
        meeting.setAttendees(employees);
        meeting = meetingDao.addMeeting(meeting);

        //act
        meetingDao.deleteMeetingById(meeting.getId());

        //assert
        Meeting fromDao = meetingDao.getMeetingByid(meeting.getId());

        assertNull(fromDao);
    }

    /**
     * Test of getMeetingsForRoom method, of class MeetingDaoDB.
     */
    @Test
    public void testGetMeetingsForRoom() {
        //arrange
        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Room room2 = new Room();
        room2.setName("Test Room 2");
        room2.setDescription("Test Room Description 2");
        room2 = roomDao.addRoom(room2);

        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now().withNano(0));
        meeting.setRoom(room);
        meeting.setAttendees(employees);
        meeting = meetingDao.addMeeting(meeting);

        Meeting meeting2 = new Meeting();
        meeting2.setName("Test Meeting");
        meeting2.setTime(LocalDateTime.now().withNano(0));
        meeting2.setRoom(room2);
        meeting2.setAttendees(employees);
        meeting2 = meetingDao.addMeeting(meeting2);

        Meeting meeting3 = new Meeting();
        meeting3.setName("Test Meeting");
        meeting3.setTime(LocalDateTime.now().withNano(0));
        meeting3.setRoom(room);
        meeting3.setAttendees(employees);
        meeting3 = meetingDao.addMeeting(meeting3);

        //act
        List<Meeting> meetingsForRoom = meetingDao.getMeetingsForRoom(room);

        //assert
        assertEquals(2, meetingsForRoom.size());
        assertTrue(meetingsForRoom.contains(meeting));
        assertTrue(meetingsForRoom.contains(meeting3));
        assertFalse(meetingsForRoom.contains(meeting2));
    }

    /**
     * Test of getMeetingsForEmployee method, of class MeetingDaoDB.
     */
    @Test
    public void testGetMeetingsForEmployee() {
        //arrange
        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        Employee employee2 = new Employee();
        employee2.setFirstName("Test First 2");
        employee2.setLastName("Test Last 2");
        employee2 = employeeDao.addEmployee(employee2);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(employee2);

        List<Employee> employees2 = new ArrayList<>();
        employees2.add(employee2);

        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now().withNano(0));
        meeting.setRoom(room);
        meeting.setAttendees(employees);
        meeting = meetingDao.addMeeting(meeting);

        Meeting meeting2 = new Meeting();
        meeting2.setName("Test Meeting");
        meeting2.setTime(LocalDateTime.now().withNano(0));
        meeting2.setRoom(room);
        meeting2.setAttendees(employees2);
        meeting2 = meetingDao.addMeeting(meeting2);

        Meeting meeting3 = new Meeting();
        meeting3.setName("Test Meeting");
        meeting3.setTime(LocalDateTime.now().withNano(0));
        meeting3.setRoom(room);
        meeting3.setAttendees(employees);
        meeting3 = meetingDao.addMeeting(meeting3);

        //act
        List<Meeting> meetingsForEmployee = meetingDao.getMeetingsForEmployee(employee);

        //assert
        assertEquals(2, meetingsForEmployee.size());
        assertTrue(meetingsForEmployee.contains(meeting));
        assertTrue(meetingsForEmployee.contains(meeting3));
        assertFalse(meetingsForEmployee.contains(meeting2));
    }

}
