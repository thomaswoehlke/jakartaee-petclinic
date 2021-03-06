package org.woehlke.jakartaee.petclinic.oodm.entities.listener;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.spi.StandardLevel;
import org.woehlke.jakartaee.petclinic.oodm.entities.Specialty;

import javax.persistence.*;
import java.io.Serializable;

import static org.woehlke.jakartaee.petclinic.oodm.entities.listener.ListenerLogger.logIt;

@Log4j2
public class SpecialtyListener implements Serializable {

  private static final long serialVersionUID = -8293962022644297603L;

  @PrePersist
  public void onPrePersist(Specialty domainObject) {
    logIt("try to Persist: ", StandardLevel.DEBUG, log, domainObject);
  }

  @PreUpdate
  public void onPreUpdate(Specialty domainObject) {
    logIt("try to Update: ", StandardLevel.DEBUG, log, domainObject);
  }

  @PreRemove
  public void onPreRemove(Specialty domainObject) {
    logIt("try to Remove: ", StandardLevel.DEBUG, log, domainObject);
  }

  @PostPersist
  public void onPostPersist(Specialty domainObject) {
    logIt("Persisted: ", StandardLevel.INFO, log, domainObject);
  }

  @PostUpdate
  public void onPostUpdate(Specialty domainObject) {
    logIt("Updated: ", StandardLevel.INFO, log, domainObject);
  }

  @PostRemove
  public void onPostRemove(Specialty domainObject) {
    logIt("Removed: ", StandardLevel.INFO, log, domainObject);
  }

  @PostLoad
  public void onPostLoad(Specialty domainObject) {
    logIt("Loaded: ", StandardLevel.TRACE, log, domainObject);
  }

}
