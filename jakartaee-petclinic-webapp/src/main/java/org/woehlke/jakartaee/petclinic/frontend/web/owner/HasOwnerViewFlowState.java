package org.woehlke.jakartaee.petclinic.frontend.web.owner;

import org.woehlke.jakartaee.petclinic.frontend.web.common.HasCrudFlowState;

public interface HasOwnerViewFlowState extends HasCrudFlowState {

    boolean isFlowStateShow();
    void setFlowStateShow();

    boolean isFlowStateNewPet();
    void setFlowStateNewPet();

    boolean isFlowStateEditPet();
    void setFlowStateEditPet();

    boolean isFlowStateNewVisit();
    void setFlowStateNewVisit();
}