package hu.idomsoft.common.validator;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

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
            String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(ArrayUtils.toPrimitive(value)));
            MediaType mediaType = MediaType.parseMediaType(mimeType);
            if (!mediaType.equalsTypeAndSubtype(MediaType.IMAGE_JPEG)) {
                messages.add("A kapott fájl nem " + MediaType.IMAGE_JPEG.toString());
            }
        } catch (Exception e) {
            messages.add("A kapott fájl myme típusának beazonosítása nem sikerült!");
        }

        if (messages.isEmpty()) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(new ByteArrayInputStream(ArrayUtils.toPrimitive(value)));
                int currentWidth = image.getWidth();
                int currentHeight = image.getHeight();
                if (definedHeight != currentHeight) {
                    messages.add("Nem megfelelő magasság! Érkezett:" + currentHeight);
                }
                if (definedWidth != currentWidth) {
                    messages.add("Nem megfelelő szélesség! Érkezett:" + currentWidth);
                }
            } catch (IOException e) {
                messages.add("A kapott fájl képpé konvertálása nem sikerült!");
            }
        }

        if (!messages.isEmpty()) {
            context.buildConstraintViolationWithTemplate(messages.stream().map(String::valueOf).collect(Collectors.joining()))
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}