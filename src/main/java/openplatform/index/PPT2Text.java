package openplatform.index;

import java.io.*;
import java.util.*;
import org.apache.poi.hpsf.*;
import org.apache.poi.poifs.eventfilesystem.*;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndian;
import org.apache.poi.hdf.extractor.*;

/**
 * <p>Title: PPT2Text</p>
 * <p>Description: A class that parses the Powerpoint document content to text </p>
 * @author Hari Shanker, Sudhakar Chavali
 * @version 1.0
 */

public class PPT2Text
    implements PPTConstants {
    /**
     *
     * <p>Title: PPTListener</p>
     * <p>Description:  Class that used to handle the Power Point Events</p>
     * @author Hari Shanker,Sudhakar Chavali
     * @version 1.0
     */

    class PPTListener
        implements POIFSReaderListener {

        public void processPOIFSReaderEvent(POIFSReaderEvent event) {

            try {

                org.apache.poi.poifs.filesystem.DocumentInputStream _documentStream = null;

                // Checking for PowerPoint Document Stream
                if (!event.getName().startsWith("PowerPoint Document")) {
                    return;
                }

                _documentStream = event.getStream();

                byte pptdata[] = new byte[_documentStream.available()];
                _documentStream.read(pptdata, 0, _documentStream.available());

                int sNum = 0;

                long offset = 0, offsetEnd = 0;
                long offsetPD = 0, oldoffsetPD = 0, docRef = 0, maxPresist = 0;

                // Traverse Bytearray to get CurrentUserEditAtom

                // Call to extract the Text in all PlaceHolders

                // To hold PPTClientTextBox objects for mapping into Slide Objects
                java.util.Hashtable _containerClientTextBox = new java.util.Hashtable();

                // Traverse ByteArray to identiy edit paths of ClientTextBoxes
                for (long i = 0; i < pptdata.length - 20; i++) {
                    long type = LittleEndian.getUShort(pptdata, (int) i + 2);
                    long size = LittleEndian.getUInt(pptdata, (int) i + 4);

                    if (type == PPT_USEREDIT_ATOM) { // Checking the Record Header (UserEditAtom)
                        long lastSlideID = LittleEndian.getInt(pptdata, (int) i + 8);
                        long version = LittleEndian.getUInt(pptdata, (int) i + 12);
                        offset = LittleEndian.getUInt(pptdata, (int) i + 16);
                        offsetPD = LittleEndian.getUInt(pptdata,
                                                        (int) i + 20);

                        // Call to extract ClientTextBox text in each UserEditAtom
                        _containerClientTextBox = extractClientTextBoxes(
                            _containerClientTextBox, offset, pptdata, offsetPD);

                    }
                }

                Vector slides = extractPlaceHoders(offset, pptdata, offsetPD);

                if (slides.size() == 0) {
                    slides.addElement(new PPTSlide(256));
                }

                PPTSlide _slide = (PPTSlide) slides.get(slides.size() - 1);

                for (Enumeration enum0 = _containerClientTextBox.elements();
                     enum0.hasMoreElements(); ) {
                    PPTClientTextBox _clientTextBox = (PPTClientTextBox) enum0.nextElement();
                    _slide.addContent(_clientTextBox.getContent());

                }

                //Merging ClientTextBox data with Slide Data

                // Printing the text from Slides vector object (need further modification)

                for (int i = 0; i < slides.size(); i++) {

                    _slide = (PPTSlide) slides.get(i);

                    Vector scontent = _slide.getContent();
                    //          StringBuffer _stringbuffer = new StringBuffer();
                    for (int j = 0; j < scontent.size(); j++) {
                        pptTextBuffer.append(scontent.get(j).toString());
                    }

                }

            }
            catch (Throwable ex) {

                return;
            }
        }
    }

    /**
     * Method that returns the client text boxes of a slide
     * @param clientTextBoxContainer
     * @param offset
     * @param pptBytes
     * @param offsetPD
     * @return Hashtable
     * @throws MSPowerPointDocumentParserException
     */

    public java.util.Hashtable extractClientTextBoxes(java.util.Hashtable
                                                      _containerClientTextBox, long offset, byte[] pptdata, long offsetPD) throws
        Throwable {

        //To hold temparary data
        ByteArrayOutputStream _outStream = new ByteArrayOutputStream();

        PPTClientTextBox _clientTextBox = null;

        // Traversing the bytearray upto Presist directory position
        for (long i = offset; i < offsetPD - 20; i++) {

            try {

                long rinfo = LittleEndian.getUShort(pptdata, (int) i);
                long type = LittleEndian.getUShort(pptdata, (int) i + 2); // Record Type
                long size = LittleEndian.getUInt(pptdata, (int) i + 4); // Record Size

                if (type == PPT_DIAGRAMGROUP_ATOM) { //Record type is of Drawing Group

                    long shapeCount = LittleEndian.getUInt(pptdata, (int) i + 8); // Total number of objects
                    long _currentID = LittleEndian.getInt(pptdata, (int) i + 12); // Group ID+number of objects

                    _currentID = ( (int) (_currentID / 1024)) * 1024;

                    if (_currentID == PPT_MASTERSLIDE) { // Ignore Master Slide objects
                        i++;
                        continue;
                    }

                    //Check for the ClientTextBox GroupID existence
                    if (!_containerClientTextBox.containsKey(new Long(_currentID))) {
                        _clientTextBox = new PPTClientTextBox(_currentID);
                        _containerClientTextBox.put(new Long(_currentID), _clientTextBox);
                    }
                    else {
                        // If exists get Client Textbox Group
                        _clientTextBox = (PPTClientTextBox) _containerClientTextBox.get(new
                                                                                        Long(_currentID));
                        _clientTextBox.setContent("");
                    }

                    // Iterating the bytearray for TextCharAtoms and TextBytesAtom
                    for (long j = i + 8; j < offsetPD - 20; j++) {
                        try {
                            long nrinfo = LittleEndian.getUShort(pptdata, (int) j);
                            long ntype = LittleEndian.getUShort(pptdata, (int) j + 2); //Record Type
                            long nsize = LittleEndian.getUInt(pptdata, (int) j + 4); // Record size

                            if (ntype == PPT_DIAGRAMGROUP_ATOM) { // Break the loop if next GroupID found
                                i = j - 1;
                                break;
                            }
                            else if (ntype == PPT_TEXTBYTE_ATOM) { //TextByteAtom record

                                _outStream = new ByteArrayOutputStream();
                                long ii = 0;
                                for (ii = j + 6; ii <= j + 6 + nsize; ii++) { // For loop to changed to a function
                                    short ch = Utils.convertBytesToShort(pptdata, (int) ii + 2);
                                    if (ch == 0 || ch == 16 || ch == 13 || ch == 10) {
                                        _outStream.write( (byte) '\r');

                                    }
                                    else if (ch == 0x201c) { // for left double quote
                                        _outStream.write( (byte) 147);
                                    }
                                    else if (ch == 0x201d) { // for right double quote
                                        _outStream.write( (byte) 148);

                                    }
                                    else if (ch == 0x2019) { // for right single quote
                                        _outStream.write( (byte) 146);
                                    }
                                    else if (ch == 0x2018) { // for left single quote
                                        _outStream.write( (byte) 145);
                                    }
                                    else if (ch == 0x2013) { // for '-' character
                                        _outStream.write( (byte) 150);
                                    }
                                    else {
                                        _outStream.write( (byte) ch);
                                    }
                                }

                                // Setting the identified text for Current groupID
                                _clientTextBox.setContent(_clientTextBox.getContent() +
                                                          new String(_outStream.toByteArray()));

                            }
                            else if (ntype == PPT_TEXTCHAR_ATOM) { // TextCharAtom record
                                _outStream = new ByteArrayOutputStream();
                                String strTempContent = new String(pptdata, (int) j + 6,
                                                                   (int) (nsize) + 2);
                                byte bytes[] = strTempContent.getBytes();

                                for (int ii = 0; ii < bytes.length - 1; ii += 2) { // For loop to changed to a function
                                    short ch = Utils.convertBytesToShort(bytes, ii);
                                    if (ch == 0 || ch == 16 || ch == 13 || ch == 10) {
                                        _outStream.write( (byte) '\r');

                                    }

                                    else if (ch == 0x201c) {
                                        _outStream.write( (byte) 147);

                                    }
                                    else if (ch == 0x201d) {
                                        _outStream.write( (byte) 148);

                                    }
                                    else if (ch == 0x2019) {
                                        _outStream.write( (byte) 146);
                                    }
                                    else if (ch == 0x2018) {
                                        _outStream.write( (byte) 145);
                                    }
                                    else if (ch == 0x2013) { // for - character
                                        _outStream.write( (byte) 150);
                                    }

                                    else {
                                        _outStream.write( (byte) ch);
                                    }
                                }

                                // Setting the identified text for Current groupID
                                _clientTextBox.setContent(_clientTextBox.getContent() +
                                                          new String(_outStream.toByteArray()));
                            }
                        }
                        catch (Throwable e) {
                            break;
                        }
                    }
                }
            }
            catch (Throwable ee) {
                return _containerClientTextBox;
            }
        }
        return _containerClientTextBox;
    }

    /**
     * Method that returns the Powerpoint place holders
     * @param offset
     * @param pptBytes
     * @param offsetPD
     * @return Vector
     * @throws MSPowerPointDocumentParserException
     */

    public Vector extractPlaceHoders(long offset, byte[] pptdata, long offsetPD) throws
        Throwable {

        int sNum = 0;

        Vector slides = new Vector(); // To All Slides data

        PPTSlide currentSlide = null; // Object to hold current slide data

        ByteArrayOutputStream _outStream = new ByteArrayOutputStream(); // To store data found in TextCharAtoms and TextBytesAtoms

        for (long i = offset; i < pptdata.length - 20; i++) {
            try {
                long rinfo = LittleEndian.getUShort(pptdata, (int) i);
                long type = LittleEndian.getUShort(pptdata, (int) i + 2);
                long size = LittleEndian.getUInt(pptdata, (int) i + 4);

                if (type == PPT_TEXTBYTE_ATOM) { //TextByteAtom record

                    _outStream = new ByteArrayOutputStream();
                    long ii = 0;
                    for (ii = i + 6; ii <= i + 6 + size; ii++) {
                        short ch = Utils.convertBytesToShort(pptdata, (int) ii + 2);

                        if (ch == 0 || ch == 16 || ch == 13 || ch == 10) {
                            _outStream.write( (byte) '\r');

                        }
                        else if (ch == 0x201c) { // for left double quote
                            _outStream.write( (byte) 147);
                        }
                        else if (ch == 0x201d) { // for right double quote
                            _outStream.write( (byte) 148);
                        }
                        else if (ch == 0x2019) { // for right single quote
                            _outStream.write( (byte) 146);
                        }
                        else if (ch == 0x2018) { // for left single quote
                            _outStream.write( (byte) 145);
                        }
                        else if (ch == 0x2013) { // for '-' character
                            _outStream.write( (byte) 150);
                        }
                        else {
                            _outStream.write(ch);
                        }
                    }

                    // Setting the identified text for Current Slide
                    currentSlide.addContent(_outStream.toString());

                }
                else if (type == PPT_TEXTCHAR_ATOM) { //TextCharAtom record
                    _outStream = new ByteArrayOutputStream();
                    String strTempContent = new String(pptdata, (int) i + 6,
                                                       (int) (size) + 2);
                    byte bytes[] = strTempContent.getBytes();

                    for (int ii = 0; ii < bytes.length - 1; ii += 2) {
                        short ch = Utils.convertBytesToShort(bytes, ii);

                        if (ch == 0 || ch == 16 || ch == 13 || ch == 10) {
                            _outStream.write( (byte) '\r');
                        }
                        else if (ch == 0x201c) {
                            _outStream.write( (byte) 147);
                        }
                        else if (ch == 0x201d) {
                            _outStream.write( (byte) 148);
                        }
                        else if (ch == 0x2019) {
                            _outStream.write( (byte) 146);
                        }
                        else if (ch == 0x2018) {
                            _outStream.write( (byte) 145);
                        }
                        else if (ch == 0x2013) { // for - character
                            _outStream.write( (byte) 150);
                        }

                        else {
                            _outStream.write( (byte) ch);
                        }
                    }

                    // Setting the identified text for Current Slide
                    currentSlide.addContent(_outStream.toString());
                }
                else if (type == PPT_SLIDEPERSISTANT_ATOM) { // SlidePresistAtom Record type
                    if (sNum != 0) {
                        _outStream = new ByteArrayOutputStream();

                        long slideID = LittleEndian.getUInt(pptdata,
                                                            (int) i + 20);

                        currentSlide = new PPTSlide(slideID);
                        //currentSlide.addContent(_outStream.toString());
                        slides.addElement(currentSlide);
                    }
                    sNum++;
                }
                else
                    if (type == PPT_DIAGRAMGROUP_ATOM) { //DG
                        break;
                    }
            }
            catch (Throwable ee) {

            }

            /*******************************************************************/

        }

        return slides;
    }

    /**
     * Constructor that takes a Powerpoint document name as an argument for getting the text
     * @param fileName
     */
    public PPT2Text(String fileName) {
        this.docName = fileName;
    }

    /**
     * Method that reads the Powerpoint document for parsing the text
     * @throws MSPowerPointDocumentParserException
     */
    public void read() throws Exception {
        try {
            POIFSReader reader = new POIFSReader();
            reader.registerListener(new PPTListener());
            reader.read(new FileInputStream(docName));
        }
        catch (Throwable _docError) {
            throw new Exception(
                "Unable to read the PPT Document \nError Cause : " + _docError,
                _docError);
        }

    }

    /**
     * returns the PowerPoint text
     * @return String
     */
    public String getText() {

        return pptTextBuffer.toString();
    }

    /**
     * Holds the Powerpoint document name
     */
    private String docName;

    /**
     * Holds the parsed Powerpoint Text
     */
    private StringBuffer pptTextBuffer = new StringBuffer();

//     public static void main(String args[])
//         throws Exception
//     {
//         PPT2Text p = new PPT2Text("Presentation_de_CVS.ppt");
//         p.read();
//         System.out.println(p.getText());
//     }
}
