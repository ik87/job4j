package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Stream;


public class TrackerHibernate implements ITracker, AutoCloseable {
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    SessionFactory sf;

    TrackerHibernate init() throws Exception {
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        return this;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Override
    public Item add(Item item) {
        ItemSet itemSet = toItemSet(item);
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(itemSet);
        session.getTransaction().commit();
        session.close();
        return toItem(itemSet);
    }

    @Override
    public boolean replace(String id, Item item) {
        item.setId(id);
        ItemSet itemSet = toItemSet(item);
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(itemSet);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        ItemSet itemSet = new ItemSet();
        itemSet.setId(Integer.valueOf(id));
        Session session = sf.openSession();
        session.beginTransaction();
        session.delete(itemSet);
        result = session.contains(itemSet);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Item[] findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        Item[] result = (Item[]) session
                .createQuery("from ru.job4j.tracker.ItemSet")
                .stream()
                .map(x -> this.toItem((ItemSet) x))
                .toArray(Item[]::new);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Item[] findByName(String key) {
        Session session = sf.openSession();
        session.beginTransaction();
        String sql = "FROM ru.job4j.tracker.ItemSet WHERE name = :n";
        Item[] result = (Item[]) session
                .createQuery(sql)
                .setParameter("n", key)
                .stream()
                .map(x -> this.toItem((ItemSet) x))
                .toArray(Item[]::new);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Item findById(String id) {
        Session session = sf.openSession();
        session.beginTransaction();
        ItemSet itemSet = session.get(ItemSet.class, Integer.valueOf(id));
        session.getTransaction().commit();
        session.close();
        return toItem(itemSet);
    }

    /**
     * converter Item to ItemSet
     */
    private Item toItem(ItemSet itemSet) {
        Item item = new Item(itemSet.getName(), itemSet.getDescription());
        item.setId(String.valueOf(itemSet.getId()));
        item.setCreate(itemSet.getCreated().getTime());
        return item;
    }

    /**
     * converter ItemSet to Item
     */
    private ItemSet toItemSet(Item item) {
        ItemSet itemSet = new ItemSet();
        if (item.getId() != null) {
            itemSet.setId(Integer.valueOf(item.getId()));
        }
        itemSet.setName(item.getName());
        itemSet.setCreated(new Timestamp(item.getCreate()));
        itemSet.setDescription(item.getDesc());
        return itemSet;
    }
}
