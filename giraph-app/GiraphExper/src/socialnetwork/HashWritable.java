package socialnetwork;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.hadoop.io.*;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.mortbay.log.Log;

/**
 * Writable for hashmaps with key:String, value:Object.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable

public class HashWritable implements WritableComparable<HashWritable> {
	
	private Hashtable<String, Object> value;

	//-- Constructors
	
	public HashWritable() {
		value = new Hashtable<String, Object>();
	}
	
	public HashWritable(Hashtable<String, Object> value) {
		value = new Hashtable<String, Object>(value);
	}
	
	public HashWritable(String s) {
		setValueFromString(s);
	}
	
	//-- String Converters
	
	private Object getObjectFromString(String source) {
		
		String s = source.trim();
		
		if ((s.equals("true")) || (s.equals("false"))) {
			return new Boolean(s);
		}
		
		if (s.matches("[0-9]*\\.[0-9]+")) {
			// corresponds to usual reg exp
			// [0-9]*\.[0-9]+
			
			return new Double(s);
		}
		
		if (s.matches("[0-9]+")) {
			return new Integer(s);
		}
		
		return s;
	}
	
	private Hashtable<String, Object> getHashtableFromString(String s) {
		
		Hashtable<String, Object> h = new Hashtable<String, Object>();
		String strimmed = s.trim();
		String scut = strimmed.substring(1, s.length()-1);
		String[] components = scut.split(",");

		for(int i = 0; i < components.length; i++) {
			components[i] = components[i].trim();
			String[] keyValue = components[i].split("=");
			Object value = getObjectFromString(keyValue[1]);
			h.put(keyValue[0], value);
		}

		return h;
	}
	
	public String toString() {
		return value.toString();
	}
	
	public void setValueFromString(String s) {
		value = getHashtableFromString(s);
	}
	
	
	//-- Interface Implementation
	
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(toString());
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		String readState = in.readUTF();
		setValueFromString(readState);
	}

	@Override
	public int compareTo(HashWritable o) {
		return toString().compareTo(o.toString());
	}

}
