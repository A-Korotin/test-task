package org.ra.atomidtesttask.application;

public interface SecurityService<ID> {
    boolean userHasAccessToView(ID entityId, String username);
    boolean userHasAccessToModify(ID entityId, String username);
}
