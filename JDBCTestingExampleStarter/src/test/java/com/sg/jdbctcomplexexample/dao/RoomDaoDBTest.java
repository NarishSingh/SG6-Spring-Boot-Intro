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
public class RoomDaoDBTest {
    
    @Autowired
    RoomDao roomDao;
    
    @Autowired
    EmployeeDao employeeDao;
    
    @Autowired
    MeetingDao meetingDao;
    
    public RoomDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        //clean db before testing
        List<Room> rooms = roomDao.getAllRooms();
        for(Room room : rooms) {
            roomDao.deleteRoomById(room.getId());
        }
        
        List<Employee> employees = employeeDao.getAllEmployees();
        for(Employee employee : employees) {
            employeeDao.deleteEmployeeById(employee.getId());
        }
        
        List<Meeting> meetings = meetingDao.getAllMeetings();
        for(Meeting meeting : meetings) {
            meetingDao.deleteMeetingById(meeting.getId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAllRooms method, of class RoomDaoDB.
     */
    @Test
    public void testGetAllRooms() {
        //arrange
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        roomDao.addRoom(room);
        
        Room room2 = new Room();
        room2.setName("Test Room 2");
        room2.setDescription("Test Room 2");
        roomDao.addRoom(room2);
        
        //act
        List<Room> rooms = roomDao.getAllRooms();
        
        //assert
        assertEquals(2, rooms.size());
        assertTrue(rooms.contains(room));
        assertTrue(rooms.contains(room2));
    }

    /**
     * Test of getRoomById method, of class RoomDaoDB.
     */
    @Test
    public void testGetRoomById() {
    }

    /**
     * Test of addRoom method, of class RoomDaoDB.
     */
    @Test
    public void testAddGetRoom() {
        //arrange
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);
        
        //act
        Room fromDao = roomDao.getRoomById(room.getId());
        
        //assert
        assertEquals(room, fromDao);
    }

    /**
     * Test of updateRoom method, of class RoomDaoDB.
     */
    @Test
    public void testUpdateRoom() {
        //arrange
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);
        
        Room fromDao = roomDao.getRoomById(room.getId());
        
        assertEquals(room, fromDao);
        
        room.setName("Another Test Room");
        
        //act and assert
        roomDao.updateRoom(room);
        
        assertNotEquals(room, fromDao);
        
        fromDao = roomDao.getRoomById(room.getId());
        
        assertEquals(room, fromDao);
    }

    /**
     * Test of deleteRoomById method, of class RoomDaoDB.
     */
    @Test
    public void testDeleteRoomById() {
        //arrange
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);
        
        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);
        
        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now());
        meeting.setRoom(room);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        meeting.setAttendees(employees);
        meeting = meetingDao.addMeeting(meeting);
        
        //act
        roomDao.deleteRoomById(room.getId());
        
        //assert
        Room fromDao = roomDao.getRoomById(room.getId());
        assertNull(fromDao);
    }

}
