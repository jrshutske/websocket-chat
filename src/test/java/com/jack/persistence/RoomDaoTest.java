package com.jack.persistence;

import com.jack.entity.User;
import com.jack.entity.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RoomDaoTest {

    RoomDao roomDao;
    UserDao userDao;
    int currentSize;
    int roomId;
    int userId;
    Room room;
    User user;

    @BeforeEach
    void setUp() {
        userDao = new UserDao();
        User setUser = new User();
        setUser.setUserName("jiffshutske");
        setUser.setEmail("jiffshutske@gmail.com");
        setUser.setFirstName("jiff");
        setUser.setLastName("ekstuhs");
        setUser.setPassword("abc12345");
        userId = userDao.insert(setUser);
        user = userDao.getById(userId);

        roomDao = new RoomDao();
        Room setRoom = new Room();
        setRoom.setRoomName("initialRoom");
        setRoom.setCreator(user);
        int roomId = roomDao.insert(setRoom);
        room = roomDao.getById(roomId);
    }

    @Test
    void getById() {
        Room room = roomDao.getById(roomId);
        assertEquals("initialRoom", room.getRoomName());
    }

    @Test
    void saveOrUpdate() {
        Room room = roomDao.getById(roomId);
        room.setRoomName("newRoom");
        roomDao.saveOrUpdate(room);
        Room afterRoom = roomDao.getById(roomId);
        assertEquals("newRoom", afterRoom.getRoomName());
    }

    @Test
    void insert() {
        roomId = roomDao.insert(room);
        room = roomDao.getById(roomId);
        assertEquals("initialRoom", room.getRoomName());
    }

    @Test
    void delete() {
        Room room = roomDao.getById(roomId);
        roomDao.delete(room);
        assertNull(roomDao.getById(roomId));
    }

    @Test
    void getAll() {
        List<Room> rooms = roomDao.getAll();
    }
}