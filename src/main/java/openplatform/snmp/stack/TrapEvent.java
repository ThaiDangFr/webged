package openplatform.snmp.stack;



/**
 * The TrapEvent class. This class is delivered when a trap is received.
 * Since traps cannot always be decoded, this event either contains the
 * 'raw' bytes or the decoded trap pdu.
 *
 */
public class TrapEvent extends java.util.EventObject  
{

    protected boolean consumed = false;

    private int version;
    private String hostAddress;
    private byte [] message;
    private Pdu trapPdu;
    private boolean isDecoded;

/** 
 * The constructor for a decoded trap event. The SnmpContext classes
 * will fire decoded trap events.
 *
 * @param source The source of the event
 * @param pdu The trap pdu
 *
 * @see #getPdu()
 */
public TrapEvent(Object source, Pdu pdu) 
{
    super(source);
    trapPdu = pdu;
    isDecoded = true;
}

/** 
 * The constructor for an undecoded trap event. The DefaultTrapContext
 * class will fire undecoded trap events.
 *
 * @param source The source of the event
 * @param v The SNMP version of the trap
 * @param hn The IP address of the host where the trap came from
 * @param mess The trap in bytes
 *
 * @see #getVersion()
 * @see #getHostAddress()
 * @see #getMessage()
 */
public TrapEvent(Object source, int v, String hn, byte [] mess) 
{
    super(source);
    version = v;
    hostAddress = hn;
    message = mess;
    isDecoded = false;
}

/**
 * Returns if this is a decoded trap event. If it is a decoded trap,
 * then a Trap Pdu is available (see getPdu()). If it is an undecoded
 * trap, then the version number, the hostAddress and the byte array is
 * available.
 * 
 * @return true if decoded trap, false if undecoded trap
 */
public boolean isDecoded()
{
    return isDecoded;
}


/**
 * The SNMP version number of the trap. The version is part of an
 * undecoded trap event.
 *
 * @return The version number.
 * @see #TrapEvent(Object, int, String, byte []) 
 * @see #getHostAddress()
 * @see #getMessage()
 * @see SnmpConstants#SNMP_VERSION_1
 * @see SnmpConstants#SNMP_VERSION_2c
 * @see SnmpConstants#SNMP_VERSION_3
 */
public int getVersion()
{
    return version;
}

/**
 * The IP address of the host where the trap came from. The host address is part of an
 * undecoded trap event.
 *
 * @return The IP address of the host or null.
 * @see #TrapEvent(Object, int, String, byte []) 
 * @see #getVersion()
 * @see #getMessage()
 */
public String getHostAddress()
{
    return hostAddress;
}

/**
 * The trap SNMP message in bytes. This is a copy of the original
 * message. The bytes are part of an undecoded trap event.
 *
 * @return The trap in bytes.
 * @see #TrapEvent(Object, int, String, byte []) 
 * @see #getVersion()
 * @see #getHostAddress()
 */
public byte [] getMessage()
{
    return message;
}

/**
 * The trap pdu. The pdu is part of a decoded trap event.
 *
 * @return The decoded Pdu.
 * @see #TrapEvent(Object, Pdu) 
 */
public Pdu getPdu()
{
    return trapPdu;
}

/**
 * Consumes this event so that it will not be send to any other
 * listeners.
 */
public void consume()
{
    consumed = true;
}

/**
 * Returns whether or not this event has been consumed.
 * @see #consume 
 */
public boolean isConsumed()
{
    return consumed;
}

}
