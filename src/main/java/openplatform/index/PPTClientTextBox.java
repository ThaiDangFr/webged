package openplatform.index;

/**
 * <p>Title: PPTClientTextBox</p>
 * <p>Description:  A class that holds the Powrpoint Client Text box content</p>
 * @author Hari Shanker, Sudhakar Chavali
 * @version 1.0
 */


public class PPTClientTextBox {
  /**
   * Holds the current id of a client text box
   */
  protected long currentID;

  /**
   * holds the content of client text box
   */
  protected String content;

  /**
   * Instantiates the client text box object
   * @param number
   */
  public PPTClientTextBox(long number) {
    currentID = number;
    this.content = "";
  }

  /**
   * Instantiates the client text box object
   * @param number
   * @param content
   */
  public PPTClientTextBox(long number, String content) {
    currentID = number;
    this.content = content;
  }

  /**
   * Sets the content of a client text box
   * @param content
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * returns the content of a client text box
   * @return
   */

  public String getContent() {
    return content;
  }

  /**
   * returns the current client text box id
   * @return long
   */
  public long getID() {
    return currentID;
  }
}
