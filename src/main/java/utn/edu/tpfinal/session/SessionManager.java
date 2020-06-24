package utn.edu.tpfinal.session;

import org.springframework.stereotype.Component;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.models.User;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

@Component
public class SessionManager {


    Map<String, Session> sessionMap = new Hashtable<>();
    int sessionExpiration = 600000;


    public String createSession(User user) {
        String token = UUID.randomUUID().toString();
        sessionMap.put(token, new Session(token, user, new Date(System.currentTimeMillis())));
        return token;
    }

    public String createSession(User user, String token) {
        sessionMap.put(token, new Session(token, user, new Date(System.currentTimeMillis())));
        return token;
    }

    public Session getSession(String token) throws ResourceNotExistException {
        if (token == null) {
            return null;
        }

        Session session = sessionMap.get(token);
        if (session != null) {
            session.setLastAction(new Date(System.currentTimeMillis()));
        } else {
            throw new ResourceNotExistException();
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

    public User getCurrentUser(String token) throws ResourceNotExistException {
        try {
            User u = getSession(token).getLoggedUser();
            return u;
        } catch (ResourceNotExistException e) {
            throw e;
        }
    }
}
