package CO2017.exercise3.fnd1;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MessageBoard {
	
	//map representing a message board
	Map<MessageHeader,String> msgBoard;
	
	MessageBoard(){
		msgBoard = new ConcurrentHashMap<MessageHeader,String>();
	}
	
	//save message to message board
	synchronized void SaveMessage(MessageHeader mh, String msg){
		if(!ListHeaders().contains(mh))
			msgBoard.put(mh, msg);
	}
	
	//retrieve a message via a message header
	String GetMessage(MessageHeader mh){
		return msgBoard.get(mh);
		
	}
	
	//return the set of message headers
	Set<MessageHeader> ListHeaders(){
		return msgBoard.keySet();
	}

}
