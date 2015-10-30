import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
	
	//maximum number of messages
	private final int maximumSize; 
	// index starting from 0000 to 9999
	private int messageIndex;  
	private Queue<String> buffer = new LinkedList<String>();

	
	
	/**
	 * 
	 * @param size ,accepts the maximum number of messages that this buffer can hold
	 */
	public Buffer(int size){
		this.maximumSize = size;
		this.messageIndex = 0;
		
	}
	
	/**
	 * 
	 * @param message, add new message
	 */
	public void put(String message){
		String formatMessage = String.format("%04d", messageIndex);
		formatMessage += ")";
		formatMessage += message;
		
		if(buffer.size() < maximumSize)
			buffer.add(formatMessage);
		else{
			buffer.poll();
			buffer.add(formatMessage);
		}
	

		if(++messageIndex > 9999)
			messageIndex = 0;
		
	}
	
	/**
	 * 
	 * @param numMessages
	 * @return, return numMessages newest messages
	 */
	public String[] getNewest(int numMessages){
		if(numMessages < 0)
			return null;
		else{
			int bufferSize = buffer.size();
			if(numMessages > bufferSize)
				numMessages = bufferSize;
			
			String[] results = new String[numMessages]; // if numMessages = 0, return an empty array
			int startIndex = bufferSize - numMessages;
			int i = 0;
			for(String str:buffer){
				if(i >= startIndex)
					results[i-startIndex] = str;
				i ++;
			}
			return results;
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Buffer cb = new Buffer(5);
		for(int i=0;i<7;i++)
			cb.put(String.valueOf(i));
		for(String str:cb.buffer)
			System.out.print(str);
	}

}
