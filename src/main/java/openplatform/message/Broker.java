package openplatform.message;


/**
 * Class Broker
 *
 * In each application, you can create a broker class implementing that interface.
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public interface Broker
{
    public void notifyChange(Object obj);
    public void notifyDelete(Object obj);
    public void notifyCreate(Object obj);
}
