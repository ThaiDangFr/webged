package openplatform.snmp.stack;

/**
 * <p>
 * This class represents the variable bindings to a Pdu.
 * A variable consists of a name (an AsnObjectId) and a value (an
 * AsnObject)
 * </p>
 *
 * <p>
 * The varbind is usely passed to the Observers of the Pdu when
 * notifying them. 
 * </p>
 *
 * @see Pdu#addOid(varbind)
 * @see Pdu#addOid(String, AsnObject)
 */
public class varbind extends Object 
{

    private AsnObjectId name;
    private AsnObject value;

    /** 
     * Constructor.
     * It will clone the varbind given as parameter.
     *
     * @param var The varbind
     */
    public varbind (varbind var)
    {
        name  = var.name;
        value = var.value;
    }

    /** 
     * Constructor.
     * The name will be set to the Oid, the value will be set to
     * AsnNull. This is usualy used to Get or GetNext requests.
     *
     * @param Oid The oid
     * @see AsnNull
     */
    public varbind(String Oid) 
    {
        this(new AsnObjectId(Oid), new AsnNull());
    }

    /** 
     * Constructor.
     * The name will be set to the Oid, the value will be set to
     * AsnNull. This is usualy used to Get or GetNext requests.
     *
     * @param Oid The oid
     * @see AsnNull
     */
    public varbind(AsnObjectId Oid) 
    {
        this(Oid, new AsnNull());
    }

    /** 
     * Constructor.
     * The name and value will be set. 
     *
     * @param Oid The oid
     * @param val The value for the varbind
     */
    public varbind(String Oid, AsnObject val) 
    {
        this(new AsnObjectId(Oid), val); 
    }

    /** 
     * Constructor.
     * The name and value will be set. 
     *
     * @param Oid The oid
     * @param val The value for the varbind
     * @since 4_12
     */
    public varbind(AsnObjectId Oid, AsnObject val) 
    {
        name = Oid;
        value = val;
    }

    varbind(AsnSequence vb)
    {
        name  = (AsnObjectId) vb.getObj(0);
        value = vb.getObj(1);
    }

    /** 
     * Returns the oid, this is the name of the varbind.
     *
     * @return the name as an AsnObjectId
     */
    public AsnObjectId getOid() 
    {
        return name;
    }

    /** 
     * Returns the value of the varbind.
     *
     * @return the value as AsnObject
     */
    public AsnObject getValue() 
    {
        return value;
    }

    Object setValue(AsnSequence vb) 
    {
        name  = (AsnObjectId) vb.getObj(0);
        value = vb.getObj(1);
        return value;
    }

    /** 
     * Returns the string representation of the varbind.
     *
     * @return The string of the varbind
     */
    public String toString()
    {
        return (name.toString() + ": " + value.toString());
    }
}
