package io.nanuvem.onetomany.combobox.demo.util;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * BeanUtils to copy properties and ignore null
 */
public class BeanUtils extends BeanUtilsBean {
  /**
   * BeanUtils Instance
   */
  private static final io.nanuvem.onetomany.combobox.demo.util.BeanUtils INSTANCE = new io.nanuvem.onetomany.combobox.demo.util.BeanUtils();

  /**
   * Copy ignoring null properties
   * @param target Target Object
   * @param source Source Object
   */
  public static void copyNonNullProperties(Object target, Object source) {
    try {
      INSTANCE.copyProperties(target, source);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(
        TranslatorUtil.getTranslation(TranslatorUtil.UNEXPECTED_ERROR),
        e
      );
    }
  }

  /**
   * Copy all properties
   * @param target Target Object
   * @param source Source Object
   */
  public static void copyAllProperties(Object target, Object source) {
    org.springframework.beans.BeanUtils.copyProperties(target, source);
  }

  /**
   * (non-javadoc)
   * @see BeanUtilsBean#copyProperty(Object, String, Object)
   */
  @Override
  public void copyProperty(Object bean, String name, Object value)
    throws IllegalAccessException, InvocationTargetException {
    if (value == null) return;
    super.copyProperty(bean, name, value);
  }

  public static <S> Set<S> removeDuplicateItemsUsingField(Set<S> source, Set<S> target, String fieldName) throws RuntimeException{
    Set<S> newTarget = target;

    source.forEach((S item) ->{
      try {
        Object sourceFieldValue = item.getClass().getDeclaredField(fieldName).get(item);
        target.forEach((S targetItem) ->{
          try {
            Object targetFieldValue = targetItem.getClass().getDeclaredField(fieldName).get(targetItem);
            if(targetFieldValue.equals(sourceFieldValue)){
              newTarget.remove(targetItem);
            }
          } catch (NoSuchFieldException | IllegalAccessException e) {
              throw new RuntimeException(
                      TranslatorUtil.getTranslation(TranslatorUtil.UNEXPECTED_ERROR),
                      e
              );
          }
        });
      } catch (NoSuchFieldException | IllegalAccessException e) {
          throw new RuntimeException(
                  TranslatorUtil.getTranslation(TranslatorUtil.UNEXPECTED_ERROR),
                  e
          );
      }
    });
    return newTarget;
  }
}
