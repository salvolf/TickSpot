package tickspot.application.sev.tickspot.restservice.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sev on 10/02/15.
 */
public class ClientList {
    public List<Client> clients;
    public ClientList(List<Client> clients) {
        this.clients = new ArrayList<>(clients);
    }

}
