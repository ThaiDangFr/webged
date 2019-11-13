LOCK TABLES SnmpFormula WRITE;

INSERT INTO SnmpFormula (formulaid,displayname,description,formula,legend) VALUES
('1','Bandwidth','Bandwidth occupation in percent','(delta(Rfc1213Mib_IFOUTOCTETS)+delta(Rfc1213Mib_IFINOCTETS))*8*100/(deltatime*Rfc1213Mib_IFSPEED)','Rfc1213Mib_IFDESCR'),
('2','Partition','Partition occupation in percent','HostResourcesMib_HRSTORAGEUSED*100/HostResourcesMib_HRSTORAGESIZE','HostResourcesMib_HRSTORAGEDESCR'),
('3','CPU','CPU usage in percent','(delta(UcdSnmpMib_SSCPURAWUSER)+delta(UcdSnmpMib_SSCPURAWNICE)+delta(UcdSnmpMib_SSCPURAWSYSTEM))/deltatime','UcdSnmpMib_SSINDEX'),
('4','Ping received','It represents the number of incoming ping','IpMib_ICMPOUTMSGS','IpMib_ICMPOUTMSGS'),
('5','Ping sent','It represents the number of ping sent','IpMib_ICMPINMSGS','IpMib_ICMPINMSGS'),
('6','TCP connections','Number of established TCP connections','TcpMib_TCPCURRESTAB','TcpMib_TCPCURRESTAB'),
('7','Snmp activity','It measures the load for SNMP input/output messages.','(delta(Snmpv2Mib_SNMPINPKTS)+delta(Snmpv2Mib_SNMPOUTPKTS))/2','Rfc1213Mib_SYSDESCR'),
('8','Bad Snmp request','The number of bad snmp request received','Snmpv2Mib_SNMPINBADVERSIONS+Snmpv2Mib_SNMPINBADCOMMUNITYNAMES+Snmpv2Mib_SNMPINBADCOMMUNITYUSES+Snmpv2Mib_SNMPINBADVALUES','Rfc1213Mib_SYSDESCR'),
('9','Connected users','Number of connected users','HostResourcesMib_HRSYSTEMNUMUSERS','HostResourcesMib_HRSYSTEMNUMUSERS'),
('10','Processes','Number of processes','HostResourcesMib_HRSYSTEMPROCESSES','HostResourcesMib_HRSYSTEMPROCESSES');

UNLOCK TABLES;


