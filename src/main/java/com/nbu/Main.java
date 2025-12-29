package com.nbu;
import com.nbu.configuration.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        Session session = SessionFactoryUtil.getSessionFactory().openSession();
        session.close();
    }
}