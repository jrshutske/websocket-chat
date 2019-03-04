package com.jack.controller;

import com.jack.entity.Room;
import com.jack.entity.User;
import com.jack.persistence.UserDao;
import com.jack.persistence.RoomDao;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@SpringBootApplication
public class RoomController {
    @GetMapping("/room")
    String rooms(Model model) {
        RoomDao roomDao = new RoomDao();
        List<Room> rooms = roomDao.getAll();
        model.addAttribute("rooms", rooms);
        return "room";
    }

    @GetMapping("/room/{id}")
    String getroombyid(@PathVariable int id, Model model) {
        RoomDao roomDao = new RoomDao();
        Room room = roomDao.getById(id);
        model.addAttribute("room", room);
        return "roomshow";
    }

    @GetMapping("/room/{id}/edit")
    String editroom(@PathVariable int id, Model model) {
        RoomDao roomDao = new RoomDao();
        Room room = roomDao.getById(id);
        model.addAttribute("room", room);
        return "roomedit";
    }

    @PostMapping(value = "/room/{id}/update")
    String updateroom(@PathVariable int id,
                      @RequestParam("roomName") String roomName) {
        RoomDao roomDao = new RoomDao();
        Room room = roomDao.getById(id);
        room.setRoomName(roomName);
        roomDao.saveOrUpdate(room);
        return "redirect:/room/{id}/show";
    }

    @GetMapping("/room/new")
    String newroom(Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        return "roomnew";
    }

    @PostMapping(value = "/room/create")
    String createroom(@RequestParam("roomName") String roomName,
                      @RequestParam("creator") String creatorId) {
        RoomDao roomDao = new RoomDao();
        UserDao userDao = new UserDao();
        User creator = userDao.getById(Integer.parseInt(creatorId));
        Room room = new Room();
        room.setRoomName(roomName);
        room.setCreator(creator);
        int id = roomDao.insert(room);
        return "redirect:/room/" + id;

    }
}
