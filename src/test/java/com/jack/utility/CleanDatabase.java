package com.jack.utility;

import com.jack.entity.Room;
import com.jack.entity.User;
import com.jack.persistence.RoomDao;
import com.jack.persistence.UserDao;

import java.util.List;

public class CleanDatabase {

    public void runCleaner() {
        RoomDao roomDao = new RoomDao();
        List<Room> roomsDelete = roomDao.getAll();
        for (Room roomDelete : roomsDelete) {
            roomDao.delete(roomDelete);
        }
        UserDao userDao = new UserDao();
        List<User> usersDelete = userDao.getAll();
        for (User userDelete : usersDelete) {
            userDao.delete(userDelete);
        }
    }

}
