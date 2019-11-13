package openplatform.snmp.stack;

/**
 * This interface contains the OIDs for the usmStats variables.
 * These variables are returned with the SNMPv3 usm security model when
 * an error occurs.
 *
 * They are part of the 
 * <a href="http://ietf.org/rfc/rfc2574.txt">RFC 2574</a> mib.
 *
 * @see SnmpContextv3
 *
 */
public interface usmStatsConstants  
{

    /**
     * The total number of packets received by the SNMP engine which
     * were dropped because they requested a securityLevel that was
     * unknown to the SNMP engine or otherwise unavailable. 
     */
    public final static String usmStatsUnsupportedSecLevels = "1.3.6.1.6.3.15.1.1.1";

    /**
     * The total number of packets received by the SNMP engine which were
     * dropped because they appeared outside of the authoritative SNMP
     * engine's window.
     */
    public final static String usmStatsNotInTimeWindows = "1.3.6.1.6.3.15.1.1.2"; 

    /**
     * The total number of packets received by the SNMP engine which
     * were dropped because they referenced a user that was not known to
     * the SNMP engine.  
     */
    public final static String usmStatsUnknownUserNames = "1.3.6.1.6.3.15.1.1.3";

    /**
     * The total number of packets received by the SNMP engine which
     * were dropped because they referenced an snmpEngineID that was not
     * known to the SNMP engine. 
     */
    public final static String usmStatsUnknownEngineIDs = "1.3.6.1.6.3.15.1.1.4";

    /**
     * The total number of packets received by the SNMP engine which
     * were dropped because they didn't contain the expected digest
     * value. 
     */
    public final static String usmStatsWrongDigests     = "1.3.6.1.6.3.15.1.1.5";

    /**
     * The total number of packets received by the SNMP engine which
     * were dropped because they could not be decrypted. 
     */
    public final static String usmStatsDecryptionErrors = "1.3.6.1.6.3.15.1.1.6";

    /**
     * The array with all the usmStats dotted OIDs in it.
     */
    public final static String [] usmStatsOids=
    {
        usmStatsUnsupportedSecLevels,
        usmStatsNotInTimeWindows,
        usmStatsUnknownUserNames,
        usmStatsUnknownEngineIDs,
        usmStatsWrongDigests,
        usmStatsDecryptionErrors
    };

    /**
     * The array with all the usmStats verbal OIDs in it.
     */
    public final static String [] usmStatsStrings=
    {
        "usmStatsUnsupportedSecLevels",
        "usmStatsNotInTimeWindows",
        "usmStatsUnknownUserNames",
        "usmStatsUnknownEngineIDs",
        "usmStatsWrongDigests",
        "usmStatsDecryptionErrors"
    };

}

