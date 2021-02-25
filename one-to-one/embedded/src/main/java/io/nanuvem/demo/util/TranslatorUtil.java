package io.nanuvem.demo.util;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Translator Utilities
 */
public class TranslatorUtil {
  public static final String UNEXPECTED_ERROR = "unexpected.error";
  public static final String AUTHENTICATION_FAILURE = "authentication.failure";
  public static final String TOKEN_EXPIRED = "token.expired";
  public static final String UNAUTHORIZED = "unauthorized";
  public static final String USERNAME_EXISTS = "username.exists";
  public static final String ITEM_NOT_FOUND = "item.notFound";
  public static final String ITEM_UPDATE_INCONSISTENT =
    "item.update.inconsistent";

  /**
   * Empty constructor
   */
  private TranslatorUtil() {
    // SonarLint
  }

  /**
   * Logger Instance
   * @see Logger
   */
  private static final Logger LOGGER = Logger.getLogger(
    io.nanuvem.demo.util.TranslatorUtil.class.getName()
  );

  /**
   * Gets translation from received code
   * @param code code
   * @param locale locale
   * @return proper message from code
   */
  public static String getTranslation(String code, Locale locale) {
    try {
      return ResourceBundle.getBundle("messages", locale).getString(code);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, e.getMessage());
    }

    return code;
  }

  /**
   * Gets translation from received code
   * @param code code
   * @return proper message from code
   */
  public static String getTranslation(String code) {
    return getTranslation(code, LocaleContextHolder.getLocale());
  }
}
