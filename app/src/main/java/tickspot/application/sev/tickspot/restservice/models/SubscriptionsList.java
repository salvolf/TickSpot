package tickspot.application.sev.tickspot.restservice.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sev on 12/02/15.
 */
public class SubscriptionsList implements Serializable {
    public List<Subscription> subscriptions;

    public SubscriptionsList(List<Subscription> subscriptions) {
        this.subscriptions = new ArrayList<>(subscriptions);
    }
}