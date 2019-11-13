package openplatform.mail;

/**
 * Class MailTextPart
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class MailTextPart extends MailPartInterface
{
    private String text;

    public void setText(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }
}
