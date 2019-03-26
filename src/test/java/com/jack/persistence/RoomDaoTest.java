package com.jack.persistence;

import com.jack.entity.User;
import com.jack.entity.Room;
import com.jack.utility.CleanDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RoomDaoTest {

    GenericDao roomDao;
    GenericDao userDao;
    Room room;
    User user;
    int roomId;
    int userId;

    @BeforeEach
    void setUp() {

        userDao = new GenericDao(User.class);
        User setUser = new User();
        setUser.setUsername("jackshutske");
        setUser.setEmail("jackshutske@gmail.com");
        setUser.setFirstName("jack");
        setUser.setLastName("shutske");
        setUser.setPassword("abc12345");
        userId = userDao.insert(setUser);
        user = (User)userDao.getById(userId);

        roomDao = new GenericDao(Room.class);
        Room setRoom = new Room();
        setRoom.setRoomName("initialRoom");
        setRoom.setCreator(user);
        roomId = roomDao.insert(setRoom);
        room = (Room)roomDao.getById(roomId);
    }

    @Test
    void getById() {
        Room room = (Room)roomDao.getById(roomId);
        assertEquals("initialRoom", room.getRoomName());
    }

    @Test
    void saveOrUpdate() {
        Room room = (Room)roomDao.getById(roomId);
        room.setRoomName("newRoom");
        roomDao.saveOrUpdate(room);
        Room afterRoom = (Room)roomDao.getById(roomId);
        assertEquals("newRoom", afterRoom.getRoomName());
    }

    @Test
    void insert() {
        roomId = roomDao.insert(room);
        room = (Room)roomDao.getById(roomId);
        assertEquals("initialRoom", room.getRoomName());
    }

    @Test
    void delete() {
        Room room = (Room)roomDao.getById(roomId);
        roomDao.delete(room);
        assertNull(roomDao.getById(roomId));
    }

    @Test
    void getAll() {
        List<Room> rooms = roomDao.getAll();
        assertEquals(false, rooms.isEmpty());
    }

    @AfterAll
    public static void AfterAll() {
        CleanDatabase cd = new CleanDatabase();
        cd.runCleaner();
    }
}
