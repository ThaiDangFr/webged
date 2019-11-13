package openplatform.index;

import org.xml.sax.*;
import java.io.*;

/**
 * NoOpEntityResolver.java
 *
 *
 * Created: Thu May 12 19:06:48 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class NoOpEntityResolver implements EntityResolver {
  public InputSource resolveEntity(String publicId, String systemId) {
    return new InputSource(new StringBufferInputStream(""));
  }
}




