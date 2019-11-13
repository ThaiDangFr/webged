package openplatform.billing;

/**
 * Class BillingObject
 *
 * The billing class must implements BillingObject and extends a DBean (for persistence).
 * Then, you have to define the method toBillingString according to the desired output.
 * So, if the billing output format change, the old tickets can be generated with that new format.
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public interface BillingObject
{
    public String toBillingString();
}
