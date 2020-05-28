package utn.edu.tpfinal.session;

import org.springframework.stereotype.Component;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.models.UserType;

import java.util.*;

@Component
public class SessionManager {


    Map<String, Session> sessionMap;
    int sessionExpiration = 600000;


    public SessionManager() {
        sessionMap = new Hashtable<>();
        UserType userType = new UserType(1,"user",null);
        createSession(new User(0, userType ,123456789, "aa" , "bb", "cc", "dd", null,null), "1");
    }

    public String createSession(User user) {
        String token = UUID.randomUUID().toString();
        sessionMap.put(token, new Session(token, user, new Date(System.currentTimeMillis())));
        return token;
    }

    public String createSession(User user, String token) {
        sessionMap.put(token, new Session(token, user, new Date(System.currentTimeMillis())));
        return token;
    }

    public Session getSession(String token) {
        if (token == null) {
            return null;
        }
        Session session = sessionMap.get(token);
        if (session != null) {
            session.setLastAction(new Date(System.currentTimeMillis()));
        }
        return session;
    }

    public void removeSession(String token) {
        sessionMap.remove(token);
    }

    public void expireSessions() {
        for (String k : sessionMap.keySet()) {
            Session v = sessionMap.get(k);
            if (v.getLastAction().getTime() + (sessionExpiration * 1000) < System.currentTimeMillis()) {
                System.out.println("Expiring session " + k);
                sessionMap.remove(k);
            }
        }
    }

    public User getCurrentUser(String token) {
        return getSession(token).getLoggedUser();
    }
}
