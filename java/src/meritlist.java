import java.io.*;
import java.util.*;

/**
 * this is the meritlist used for galeShapely algorithm
 */
/**
 * this is the process for creatin a default empty list
 */
public class meritlist {
	
	public HashMap<String,Rank> map;
	public meritlist(){
		this.map=new HashMap<String,Rank>();
	}
	/**
	 * this function inserts the galeShapely candidate in the list
	 */
public void insert(String uniqueID,float rank1,float rank2,float rank3){
	
	Rank k=new Rank();
	k.rank[0]=rank1;k.rank[1]=rank2;k.rank[2]=rank3;
	this.map.put(uniqueID,k);
}
}