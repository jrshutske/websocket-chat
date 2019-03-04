package com.jack.persistence;

import com.jack.entity.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class RoomDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Get room by id
     */
    public Room getById(int id) {
        Session session = sessionFactory.openSession();
        Room room = session.get( Room.class, id );
        session.close();
        return room;
    }

    /**
     * update room
     * @param room room to be inserted or updated
     */
    public void saveOrUpdate(Room room) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(room);
        transaction.commit();
        session.close();
    }

    /**
     * update room
     * @param room  room to be inserted or updated
     */
    public int insert(Room room) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        id = (int)session.save(room);
        transaction.commit();
        session.close();
        return id;
    }

    /**
     * Delete a room
     * @param room room to be deleted
     */
    public void delete(Room room) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(room);
        transaction.commit();
        session.close();
    }


    /** Return a list of all rooms
     *
     * @return All rooms
     */
    public List<Room> getAll() {

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Room> query = builder.createQuery( Room.class );
        Root<Room> root = query.from( Room.class );
        List<Room> rooms = session.createQuery( query ).getResultList();

        logger.debug("The list of users " + rooms);
        session.close();

        return rooms;
    }
}
