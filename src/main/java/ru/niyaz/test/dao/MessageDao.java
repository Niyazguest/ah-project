package ru.niyaz.test.dao;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.postgresql.translation.messages_bg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.niyaz.test.entity.Message;

import java.util.List;

/**
 * Created by user on 03.04.16.
 */

@Repository
public class MessageDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public Long saveMessage(String message) {
        Message message1 = new Message(message);
        sessionFactory.getCurrentSession().save(message1);
        return message1.getId();
    }

    @Transactional
    public List getMessages(Long afterID) {
        return sessionFactory.getCurrentSession().createCriteria(Message.class)
                .setMaxResults(10)
                .add(Restrictions.gt("id", afterID))
                .list();
    }
}
