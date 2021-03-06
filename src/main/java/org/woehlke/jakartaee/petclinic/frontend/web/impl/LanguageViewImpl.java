package org.woehlke.jakartaee.petclinic.frontend.web.impl;

import lombok.extern.log4j.Log4j2;
import org.woehlke.jakartaee.petclinic.frontend.web.FrontendMessagesView;
import org.woehlke.jakartaee.petclinic.frontend.web.LanguageView;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 14.01.14
 * Time: 21:14
 * To change this template use File | Settings | File Templates.
 */
@Log4j2
@Named("languageView")
@SessionScoped
public class LanguageViewImpl implements LanguageView {

  private static final long serialVersionUID = -5444922829398489233L;
  private final Locale DEFAULT = Locale.ENGLISH;
  private final Locale[] LOCALE_OPTIONS = {Locale.ENGLISH, Locale.GERMAN};
  @Inject
  private FrontendMessagesView frontendMessagesView;
  private Locale locale;

  private String localeSelected;

  private Map<String, String> countries = new HashMap<>();

  public LanguageViewImpl() {
    this.locale = DEFAULT;
    this.localeSelected = DEFAULT.getLanguage();
  }

  @PostConstruct
  public void init() {
    log.trace("postConstruct");
    countries = this.getCountries();
  }

  public Map<String, String> getCountries() {
    log.trace("getCountries: " + this.locale);
    this.countries.clear();
    for (Locale locale : LOCALE_OPTIONS) {
      this.countries.put(
          locale.getDisplayLanguage(),
          locale.getLanguage()
      );
    }
    for (String key : this.countries.keySet()) {
      log.trace(key + " : " + countries.get(key));
    }
    return countries;
  }

  public void setCountries(List<SelectItem> countries) {
  }

  public Locale getLocale() {
    if (this.locale == null) {
      locale = Locale.ENGLISH;
    }
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public String getLocaleSelected() {
    return localeSelected;
  }

  public void setLocaleSelected(String localeSelected) {
    this.localeSelected = localeSelected;
  }

  public FrontendMessagesView getFrontendMessagesView() {
    return frontendMessagesView;
  }

  public void setFrontendMessagesView(FrontendMessagesView frontendMessagesView) {
    this.frontendMessagesView = frontendMessagesView;
  }

  public String getMsgCantDeleteSpecialty() {
    String msg = "";
    if (locale.equals(Locale.ENGLISH)) {
      msg = "cannot delete, Specialty still in use";
    } else if (locale.equals(Locale.GERMAN)) {
      msg = "löschen nicht möglich, Fachrichtung wird noch ausgeübt";
    }
    return msg;
  }

  public String changeLanguage() {
    Locale myLocale = new Locale(this.localeSelected);
    String msg = "cool: newLocale: " + this.locale + " -> " + myLocale;
    log.debug("changed Language " + msg);
    FacesContext
        .getCurrentInstance()
        .getViewRoot()
        .setLocale(myLocale);
    this.setLocale(myLocale);
    this.frontendMessagesView.addInfoMessage("changed Language", msg);
    return "#";
  }

  @PreDestroy
  public void preDestroy() {
    log.trace("preDestroy");
  }

}
