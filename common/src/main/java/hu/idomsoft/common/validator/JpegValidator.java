package hu.idomsoft.common.validator;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;

import javax.imageio.ImageIO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JpegValidator implements ConstraintValidator<Jpeg, Byte[]> {

  private int definedHeight;
  private int definedWidth;

  @Autowired
  private MessageSource messageSource;

  @Override
  public void initialize(Jpeg constraintAnnotation) {
    definedHeight = constraintAnnotation.height();
    definedWidth = constraintAnnotation.width();
  }

  @Override
  public boolean isValid(Byte[] value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    context.disableDefaultConstraintViolation();
    List<String> messages = new ArrayList<>();

    try {
      String mimeType =
          URLConnection.guessContentTypeFromStream(
              new ByteArrayInputStream(ArrayUtils.toPrimitive(value)));
      MediaType mediaType = MediaType.parseMediaType(mimeType);
      if (!mediaType.equalsTypeAndSubtype(MediaType.IMAGE_JPEG)) {
        messages.add(messageSource.getMessage("validator.jpeg.typeNotJpeg.error", new Object[]{MediaType.IMAGE_JPEG.toString()}, LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      messages.add(messageSource.getMessage("validator.jpeg.typeProcess.error",null, LocaleContextHolder.getLocale()));
    }

    if (messages.isEmpty()) {
      BufferedImage image = null;
      try {
        image = ImageIO.read(new ByteArrayInputStream(ArrayUtils.toPrimitive(value)));
        int currentWidth = image.getWidth();
        int currentHeight = image.getHeight();
        if (definedHeight != currentHeight) {
          messages.add(messageSource.getMessage("validator.jpeg.height.error", new Object[]{definedHeight, currentHeight}, LocaleContextHolder.getLocale()));
        }
        if (definedWidth != currentWidth) {
          messages.add(messageSource.getMessage("validator.jpeg.width.error", new Object[]{definedWidth, currentWidth}, LocaleContextHolder.getLocale()));
        }
      } catch (IOException e) {
        messages.add(messageSource.getMessage("validator.jpeg.loadImage.error", null, LocaleContextHolder.getLocale()));
      }
    }

    if (!messages.isEmpty()) {
      context
          .buildConstraintViolationWithTemplate(
              messages.stream().map(String::valueOf).collect(Collectors.joining()))
          .addConstraintViolation();
      return false;
    }

    return true;
  }
}
