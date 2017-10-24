package CO2017.exercise3.fnd1;

/**
 * Simple class to represent the "header" of a message.
 *
 * <p>
 * Since this will be used as the key field of a map, it needs to implement
 * {@code equals} and {@code hashCode}.
 *
 * <p>
 * You do <strong>NOT NEED TO IMPLEMENT THIS YOURSELF</strong>. Instead the code
 * is
 * <a href="/teaching/resources/CO2017/exercise3/MessageHeader.java">available
 * here</a>. Don't forget to adjust the {@code package} line.
 *
 * @author Gilbert Laycock
 * @version $Revision: 1016 $
 **/

class MessageHeader {
	private final char _thread;
	private final int _msgID;

	/**
	 * Create a new MessageHeader
	 *
	 * @param t
	 *            the threadID
	 * @param m
	 *            the message id number
	 */
	MessageHeader(char t, int m) {
		_thread = t;
		_msgID = m;
	}

	/**
	 * Implement equals so that instances are only equal if both attributes are
	 * equal.
	 * 
	 * @param o
	 *            the object to compare
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof MessageHeader))
			return false;

		MessageHeader otherMessageHeader = (MessageHeader) o;
		return (_thread == otherMessageHeader._thread) && (_msgID == otherMessageHeader._msgID);
	}

	/**
	 * Implement hashCode so that Map based structures work properly
	 */
	@Override
	public int hashCode() {
		return _msgID * (int) _thread;
	}

	/**
	 * @return a string version of the MessageHeader
	 */
	@Override
	public String toString() {
		return (String.format("%c+%d", _thread, _msgID));
	}
}