package database;

import model.User;

/**
 * Created by Carolin on 16.06.2017.
 */
public interface IRemoteInit {

    public boolean authorizeUser(User user);
    public boolean isConnected();

}
