package openplatform.snmp.stack;

import java.io.*;

/**
 * The interface for the different type of sockets.
 *
 */
public interface ContextSocketFace 
{

/**
 * Creates the socket. This socket is used when listening for traps. Use
 * only one (1) of the two create methods.
 *
 * @param port The local port number were we receive (listen for)
 * packets.
 * @see #create(String, int)
 */
public void create(int port) throws IOException;

/**
 * Creates the socket. This socket is used to send out our requests. Use
 * only one (1) of the two create methods.
 *
 * @param host The name of the host that is to receive our packets
 * @param port The port number of the host
 * @see #create(int)
 */
public void create(String host, int port) throws IOException;

/**
 * Returns the IP address of the host of the packet we received. 
 * This will be used when we are listening for traps. 
 *
 * @return The IP address, or null when the hostname cannot be determined
 */
public String getHostAddress();

/** 
 * Receives a packet from this socket.
 * @param maxRecvSize the maximum number of bytes to receive
 * @return the packet as ByteArrayInputStream
 */
public ByteArrayInputStream receive(int maxRecvSize) throws IOException;

/** 
 * Sends a packet from this socket.
 * @param packet the packet
 */
public void send(byte[] packet) throws IOException;

/** 
 * Closes this socket.
 */
public void close();

}
