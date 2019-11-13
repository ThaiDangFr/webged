package openplatform.index;


/**
 * <p>Title: PPTSlide</p>
 * <p>Description: A class that holds the Powerpoint slide</p>
 * @author Hari Shanker, Sudhakar Chavali
 * @version 1.0
 */

import java.util.Vector;

class PPTSlide {
  /**
   * Holds the Slide Number
   */
  protected long slideNumber;

  /**
   * Holds the contents of the Slide
   */
  protected Vector contents;

  /**
   * Initialise the Object for holding the contents of Power Point Slide
   * @param number
   */
  public PPTSlide(long number) {
    slideNumber = number;
    contents = new Vector();
  }

  /**
   * Add the Content of Slide to this Object
   * @param content
   */

  public void addContent(String content) {
    contents.addElement(content);
  }

  /**
   * returns the contents of slide as a vector object
   * @return Vector
   */
  public Vector getContent() {
    return contents;
  }

  /**
   * returns the slide value
   * @return long
   */

  public long getSlideNumber() {
    return slideNumber;
  }

}
