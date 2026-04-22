package repository;

import models.DeliveryPartner;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DeliveryPartnerRepository {
    private Map<Integer, DeliveryPartner> partners = new ConcurrentHashMap<>();

    public DeliveryPartnerRepository(List<DeliveryPartner> partnerList) {
        for (DeliveryPartner p : partnerList) {
            partners.put(p.getId(), p);
        }
    }

    public DeliveryPartner getAvailablePartner() {
        for (DeliveryPartner p : partners.values()) {
            if (p.tryAssign()) {
                return p;
            }
        }
        return null;
    }

    public Collection<DeliveryPartner> getAll() {
        return partners.values();
    }
}
